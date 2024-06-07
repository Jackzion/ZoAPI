package com.yupi.springbootinit.service.impl.inner;

import com.yupi.springbootinit.service.impl.UserInterfaceInfoServiceImpl;
import com.zoapi.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoServiceImpl userInterfaceInfoService;

    @Override
    public boolean invokerCount(long interfaceInfoId, long userId) {
        // 直接调用本地 userInterfaceInfoService 即可
        return userInterfaceInfoService.invokerCount(interfaceInfoId,userId);
    }
}
