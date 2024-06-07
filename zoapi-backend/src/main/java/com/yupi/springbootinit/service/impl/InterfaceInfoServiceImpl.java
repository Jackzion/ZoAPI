package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.zoapi.model.entity.InterfaceInfo;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Ziio
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2024-05-21 18:09:35
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        // 判断接口是否为空
        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取接口信息对象名称
        String name = interfaceInfo.getName();

        // 添加，则所有必须为空
        if(add){
            if(StringUtils.isAnyBlank(name)){
                throw  new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if(StringUtils.isNotBlank(name)&&name.length()>50){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}




