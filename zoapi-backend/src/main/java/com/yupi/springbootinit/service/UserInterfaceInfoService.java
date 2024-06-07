package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoapi.model.entity.InterfaceInfo;
import com.zoapi.model.entity.UserInterfaceInfo;

/**
* @author Ziio
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-05-31 15:29:08
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 统一参数效验
     * @param userInterfaceInfo
     * @param b
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    boolean invokerCount(long interfaceInfoId , long userId);
}
