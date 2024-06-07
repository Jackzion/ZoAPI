package com.yupi.springbootinit.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口更新请求
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {

    /**
     *  接口id
     */
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;


    /**
     * 请求类型
     */
    private String method;

    private static final long serialVersionUID = 1L;
}