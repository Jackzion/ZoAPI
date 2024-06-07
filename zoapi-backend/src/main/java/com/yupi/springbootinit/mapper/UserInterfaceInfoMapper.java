package com.yupi.springbootinit.mapper;

import com.zoapi.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Ziio
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-05-31 15:29:08
* @Entity com.yupi.springbootinit.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    // 查询接口调用次数，并按top排序返回
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




