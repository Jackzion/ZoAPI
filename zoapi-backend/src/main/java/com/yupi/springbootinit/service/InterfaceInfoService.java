package com.yupi.springbootinit.service;

import com.zoapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ziio
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-05-21 18:09:35
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 统一参数效验
     * @param interfaceInfo
     * @param b
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
