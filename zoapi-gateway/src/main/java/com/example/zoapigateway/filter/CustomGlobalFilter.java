package com.example.zoapigateway.filter;

import com.zoapi.model.entity.InterfaceInfo;
import com.zoapi.model.entity.User;
import com.zoapi.service.InnerInterfaceInfoService;
import com.zoapi.service.InnerUserInterfaceInfoService;
import com.zoapi.service.InnerUserService;
import com.zoapi.zoapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    // get public service
    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    // 白名单
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    // todo: 转发地址，写死，理论要从数据库获取，然后拼接 , 转发到真正的地址，不用pom.xml 配置了，不灵活
    private static final String INTERFACE_HOST = "http://localhost:8123";
    /**
     *
     * @param exchange 所有请求，响应的信息体
     * @param chain 过滤链，过滤成功则next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 统一业务逻辑
//        - 统一日志（记录每次的请求响应）
        org.springframework.http.server.reactive.ServerHttpRequest request =  exchange.getRequest();
        String path = INTERFACE_HOST +  request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径："+ path);
        log.info("请求方法："+ method);
        log.info("请求参数："+request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源："+ request.getLocalAddress());
//        - 访问控制（黑白名单）
        // 拿到响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 不在白名单
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            return handleNoAuth(response);
        }
//        - 统一鉴权（accessKey secretKey）
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamps = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        if(!accessKey.equals("ziio")){
            return handleNoAuth(response);
        }
        if(Long.parseLong(nonce)>1000000L){
            return handleNoAuth(response);
        }
        // 超时5分钟
        Long currentTime = System.currentTimeMillis()/1000;
        final Long FIVE_MIN = 60*5L;
        if((currentTime - Long.parseLong(timestamps)) >= FIVE_MIN){
            return  handleNoAuth(response);
        }
        // 数据库查到（RPC）accessKEY效验
        User invokeUser = null;
        try{
            // 调用内部服务，根据访问密码获取用户信息
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            // 信息为空，直接返回not found 响应
            return handleNoAuth(response);
        }

        // 签名不一致(实际情况是从数据库拿到签名进行加密)
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(body,secretKey);
        if(sign==null || !sign.equals(serverSign)){
            return handleNoAuth(response);
        }
//      // 从数据库查询接口是否存在。。。。
        InterfaceInfo interfaceInfo = null;
        try {
            // 从服务查询接口信息
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path,method);
        }catch (Exception e){
            log.error("getInterfaceInfo",e);
        }
        if(interfaceInfo==null){
            // 未获取，not found response
            return handleNoAuth(response);
        }
//        - 统一业务处理（每次请求接口后，接口调用次数+1）
//        - 流量染色（记录请求是否为网关来的）
//
//        - 统一日志（记录每次的请求响应）
        // 返回装饰后的响应
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());
    }

    /**
     * 响应装饰器，处理响应后逻辑
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceId,long userId ) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用数据库接口,invokeCount++
                                try {
                                    // 从服务查询接口信息
                                    innerUserInterfaceInfoService.invokerCount(interfaceId,userId);
                                }catch (Exception e){
                                    log.error("getInterfaceInfo",e);
                                }
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);

                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data

                                // 拼接响应结果
                                sb2.append(data);
                                // 打印响应日志
                                log.info("响应结果：" + data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("网关响应异常：", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关响应异常：.\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}