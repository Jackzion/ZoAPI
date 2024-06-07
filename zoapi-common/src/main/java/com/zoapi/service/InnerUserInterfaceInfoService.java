package com.zoapi.service;


/**
* @author Ziio
* @description public service 只提供接口，不提供实现
* @createDate 2024-05-31 15:29:08
*/
public interface InnerUserInterfaceInfoService  {



    /**
     * 接口调用次数++
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokerCount(long interfaceInfoId , long userId);
}
