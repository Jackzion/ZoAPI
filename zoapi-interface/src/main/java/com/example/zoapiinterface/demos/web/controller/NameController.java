package com.example.zoapiinterface.demos.web.controller;

import com.zoapi.zoapiclientsdk.model.User;
import com.zoapi.zoapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name){
        return name ;
    }
    @PostMapping("/post")
    public String getName0ByPost(@RequestParam String name){
        return "get 你的名字是 " + name ;
    }

    @PostMapping("/user")
    public String getName1ByPost(@RequestBody User user, HttpServletRequest request){
        // 效验交给网关了
        return "用户的名字是：" + user.getUsername();
    }

}
