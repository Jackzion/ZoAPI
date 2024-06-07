package com.yupi.springbootinit.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口查询请求
 */
@Data
public class InterfaceInfoQueryRequest implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 创建人
     */
    private Long userId;

    /**
     * 第几页
     */
    private Integer current;

    /**
     * 页数
     */
    private Integer pageSize;



    private static final long serialVersionUID = 1L;
}