package com.example.zoapiinterface;

import com.zoapi.zoapiclientsdk.client.ZoApiClient;
import com.zoapi.zoapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ZoapiInterfaceApplicationTests {

    @Resource
    private ZoApiClient zoApiClient;

    @Test
    void contextLoads() {
        String result = zoApiClient.getNameByGet("ziio");
        System.out.println(result);
        User user = new User();
        user.setUsername("ziio");
        String result1 = zoApiClient.getName1ByPost(user);
        System.out.println(result1);
    }

}
