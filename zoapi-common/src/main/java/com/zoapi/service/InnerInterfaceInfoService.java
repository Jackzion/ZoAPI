package com.zoapi.service;
import com.zoapi.model.entity.InterfaceInfo;

/**
* @author Ziio
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-05-21 18:09:35
*/
public interface InnerInterfaceInfoService  {

    /**
     * 查看调用接口是否存在
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path,String method);
}
