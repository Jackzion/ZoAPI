package com.yupi.springbootinit.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口添加请求
 * @TableName interface_info
 */
@Data
public class IdRequest implements Serializable {

    /**
     * 接口id
     */
    private long id;

    private static final long serialVersionUID = 1L;
}