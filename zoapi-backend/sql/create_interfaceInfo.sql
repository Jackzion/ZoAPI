-- 接口信息表
create table if not exists zoapi_db.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '接口名称',
    `description` varchar(256) not null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestHeader` text not null comment '请求头',
    `responseHeader` text not null comment '响应头',
    `status` int not null comment '接口状态 0 关闭 1 开启',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息表';

insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黎鸿涛', '高越彬', 'www.napoleon-stroman.org', 'qp6', 'sE', 0, 'GET', 6);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('赖展鹏', '尹明轩', 'www.corie-emard.info', 'Vfv0V', 'fftii', 0, 'GET', 5);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('江文轩', '汪立诚', 'www.carmelo-moore.biz', 't6dh', 'uN', 0, 'GET', 5897625196);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('许昊天', '史思淼', 'www.hiram-mohr.net', 'RN', 'wZFN5', 0, 'GET', 4585960);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('董煜祺', '覃炎彬', 'www.corey-hartmann.org', 'A2g', 'GtmTo', 0, 'GET', 4295045);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('郝绍辉', '朱语堂', 'www.nelly-herzog.info', '0hG', '0E5Zf', 0, 'GET', 957);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('贺峻熙', '侯旭尧', 'www.bertha-casper.com', 'BHC', 'y60U', 0, 'GET', 803809856);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('范浩', '冯熠彤', 'www.lewis-hermiston.org', 'Zaknr', 'Kl6ys', 0, 'GET', 3633507528);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('李擎宇', '杨烨磊', 'www.roseanna-marks.info', 'bxNLg', 'bgud', 0, 'GET', 7388757196);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('田昊焱', '谢越泽', 'www.debora-nikolaus.io', 'xAZ', '997R', 0, 'GET', 9146883);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('宋明杰', '薛博文', 'www.adan-legros.co', 'mY3P', 'dZoQ', 0, 'GET', 54895326);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('熊瑾瑜', '龚修杰', 'www.arnulfo-bailey.info', 't6', 'TP1v', 0, 'GET', 646);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('白擎宇', '于胤祥', 'www.retta-stokes.io', '7Zu', 'fT93', 0, 'GET', 358750);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邵伟诚', '吴雨泽', 'www.earle-rice.co', 'Cvq', 'KJw7Q', 0, 'GET', 646156061);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('罗展鹏', '郑烨磊', 'www.coleen-spinka.net', 'Xx59', 'FpG', 0, 'GET', 8);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('洪锦程', '孔乐驹', 'www.tod-simonis.co', 'OzJf', 'RCne2', 0, 'GET', 4);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('贺晓博', '范嘉懿', 'www.alex-mante.com', 'v2', 'ud', 0, 'GET', 1709683);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('阎擎苍', '郝潇然', 'www.charley-mann.co', 'M2', 'bd', 0, 'GET', 7256292);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('丁鹤轩', '黄志泽', 'www.oscar-bode.net', 'YF', 'iEX', 0, 'GET', 583372);
insert into zoapi_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黄雨泽', '段志泽', 'www.leontine-jacobs.io', '5zuR7', 'nQoh', 0, 'GET', 817956);