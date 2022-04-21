CREATE DATABASE IF NOT EXISTS kingyan;

USE kingyan;

DROP TABLE IF EXISTS `users`;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`          int(20)     NOT NULL,
    `name`        varchar(20) NOT NULL,
    `nickname`    varchar(20),
    `phone`       varchar(11),
    `password`    varchar(30) NOT NULL,
    `created_date` date,
    PRIMARY KEY (`id`),
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 分段执行，否则执行太快，表还未创建好就插入会报错
INSERT INTO `users`(`id`, `name`, `password`)
VALUES (1, 'admin', '2134'),
       (2, 'test', '234sfd')