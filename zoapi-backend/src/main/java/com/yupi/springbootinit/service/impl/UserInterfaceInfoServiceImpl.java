package com.yupi.springbootinit.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.zoapi.model.entity.InterfaceInfo;
import com.zoapi.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import com.yupi.springbootinit.mapper.UserInterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Ziio
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-05-31 15:29:08
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        // 判断接口是否为空
        if(userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取接口信息对象名称
        long id = userInterfaceInfo.getId();

        // 添加，则所有必须为空
        if(add){
            if(id<0){
                throw  new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if(id>5000){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public boolean invokerCount(long interfaceInfoId, long userId) {
        // 判断
        if(interfaceInfoId<0||userId<=0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo: 分布式加锁问题
        UpdateWrapper<UserInterfaceInfo> queryWrapper = new UpdateWrapper<>();
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        queryWrapper.eq("userId",userId);
        queryWrapper.gt("leftNum",0);
        queryWrapper.setSql("leftNum = leftNum - 1 , totalNum = totalNum + 1");
        return this.update(queryWrapper);
    }
}




