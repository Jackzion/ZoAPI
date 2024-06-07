package com.zoapi.zoapiclientsdk;

import com.zoapi.zoapiclientsdk.client.ZoApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("zoapi.client")
@Data
@ComponentScan
public class ZoApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public ZoApiClient zoApiClient(){
        // 使用 ak 和 sk 创建一个zoapiClient实例
        return new ZoApiClient(accessKey,secretKey);
    }
}
