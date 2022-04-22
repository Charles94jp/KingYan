CREATE DATABASE IF NOT EXISTS kingyan;

USE kingyan;

DROP TABLE IF EXISTS `users`;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`           bigint(20)  NOT NULL     COMMENT '用户ID',
    `name`         varchar(30) NOT NULL     COMMENT '用户登录名，唯一',
    `password`     varchar(100) NOT NULL    COMMENT '登录密码，密文',
    `role_id`      int(20)                  COMMENT '用户角色ID',
    `nickname`     varchar(30)              COMMENT '用户昵称',
    `sex`          char(1)                  COMMENT '用户性别，0未知，1男，2女',
    `phone`        varchar(11)              COMMENT '用户手机号',
    `email`        varchar(50)              COMMENT '用户邮箱地址',
    `created_date` date                     COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 分段执行，否则执行太快，表还未创建好就插入会报错
INSERT INTO `users`(`id`, `name`, `password`, `role_id`)
        # 密码：123@Admin，和配置文件中的SM2密钥对无关，这个是盐值+SM3 Hash
VALUES (1, 'admin', 'lCNGtzgohvibIsSTd9H+763MneUXDluOpS4Mv4Nq/CaWZfmIqvYv+V+y4cjzMmrpjVXIFmf1NGanqZFeTa9Phg==', 1),
        # 密码：123@Test
       (2, 'test', '2kWIaYsYxO2Kjq+qtDB6B8jOvck6vbyuxHuFwbqSNuzWd3QDKePPkFmY4sXQ/gC94cEvRqvKprwHSZvKpULa7A==', 2),
        # 密码：123@Test
       (3, 'test1', '2kWIaYsYxO2Kjq+qtDB6B8jOvck6vbyuxHuFwbqSNuzWd3QDKePPkFmY4sXQ/gC94cEvRqvKprwHSZvKpULa7A==', 2);

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
