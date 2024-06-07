package com.zoapi.service;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface InnerUserService {
    /**
     * 查看调用用户是否已分配秘钥
     * @param accessKey
     * @return
     */
    com.zoapi.model.entity.User getInvokeUser(String accessKey );

}
