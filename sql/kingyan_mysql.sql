CREATE DATABASE IF NOT EXISTS kingyan;

USE kingyan;

DROP TABLE IF EXISTS `users`;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`           bigint(20)  NOT NULL COMMENT '用户ID',
    `name`         varchar(30) NOT NULL COMMENT '用户登录名，唯一',
    `password`     varchar(50) NOT NULL COMMENT '登录密码，密文',
    `role_id`      int(20)              COMMENT '用户角色ID',
    `nickname`     varchar(30)          COMMENT '用户昵称',
    `sex`          char(1)              COMMENT '用户性别，0未知，1男，2女',
    `phone`        varchar(11)          COMMENT '用户手机号',
    `email`        varchar(50)          COMMENT '用户邮箱地址',
    `created_date` date                 COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 分段执行，否则执行太快，表还未创建好就插入会报错
INSERT INTO `users`(`id`, `name`, `password`, `role_id`)
VALUES (1, 'admin', '2134', 1),
       (2, 'test', '234sfd');

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role`
(
    `id` int(20) NOT NULL       COMMENT '角色ID',
    `name` varchar(30) NOT NULL COMMENT '角色名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `role` (id, name)
VALUES (1, 'admin'),
       (2, 'test');