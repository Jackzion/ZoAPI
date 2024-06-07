package com.zoapi.zoapiclientsdk.client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zoapi.zoapiclientsdk.model.User;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.zoapi.zoapiclientsdk.utils.SignUtils.genSign;

public class ZoApiClient {
    private String accessKey ;
    private String secretKey ;

    // 访问网关地址
    private static final String GATEWAY_HOST = "http://localhost:8109";

    public ZoApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        // url 风格
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "北京");

        String result= HttpUtil.get(GATEWAY_HOST + "/api/name/get/", paramMap);
        return result;
    }

    public String getName0ByPost(String  name){
        // url 风格
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "ziio");

        String result= HttpUtil.post(GATEWAY_HOST+"/api/name/post/", paramMap);
        return result;
    }

    public String getName1ByPost(User user){
        String json = JSONUtil.toJsonStr(user);
        Map<String,String> map = getHeaderMap("12345");
        String result2 = HttpRequest.post(GATEWAY_HOST+"/api/name/user/")
                .addHeaders(map)
                .body(json)
                .execute().body();
        return result2;
    }

    private Map<String,String> getHeaderMap(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("body",body);
        hashMap.put("sign",genSign(body,secretKey));
        return hashMap;
    }
}
