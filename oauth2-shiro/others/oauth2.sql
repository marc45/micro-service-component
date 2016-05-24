/*
Navicat MySQL Data Transfer

Source Server         : thing
Source Server Version : 50629
Source Host           : databasejump.mysql.rds.aliyuncs.com:3306
Source Database       : xiaoxiao

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-05-24 16:39:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `oauth_access_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` varchar(255) DEFAULT NULL,
  `token_expired_seconds` int(11) DEFAULT '-1',
  `authentication_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `refresh_token_expired_seconds` int(11) DEFAULT '-1',
  `refresh_token` varchar(255) DEFAULT NULL,
  UNIQUE KEY `token_id` (`token_id`),
  UNIQUE KEY `refresh_token` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------
INSERT INTO oauth_access_token VALUES ('2016-05-19 14:13:41', 'd500150a48a383c1005439c891b71182', '43200', '8ec304a56dc8b1d8cee6e20aabfa21fb', 'test', 'web', 'Bearer', '2592000', null);
INSERT INTO oauth_access_token VALUES ('2016-05-19 14:14:19', '937011c9006b258f8bce0c714dfeaa1b', '43200', 'adcc280eb037ca8c2e897df011fa32eb', 'test', 'web', 'Bearer', '2592000', 'ee595b47b26a949a79ba39ac94bd5ad2');
INSERT INTO oauth_access_token VALUES ('2016-05-20 16:34:28', '6f44df71d1b3ec3770105b94c6cefd4e', '43200', '7b06f035679930bece6a2ffe252fb27b', 'test', 'mobile', 'Bearer', '2592000', '5d6b656ace8b7b5c5864a863622af036');

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `client_uri` varchar(255) DEFAULT NULL,
  `client_icon_uri` varchar(255) DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `grant_types` varchar(255) DEFAULT NULL,
  `redirect_uri` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT '-1',
  `refresh_token_validity` int(11) DEFAULT '-1',
  `description` varchar(4096) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `archived` tinyint(1) DEFAULT '0',
  `trusted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO oauth_client_details VALUES ('mobile', 'mobile', 'Mobile Client', 'http://andaily.com', 'http://andaily.com/favicon.ico', 'api-resource', 'read write', 'password,refresh_token', 'http://localhost:7777/authorization_code_callback', '22', '-1', '-1', null, '2016-05-17 17:15:56', '0', '0');
INSERT INTO oauth_client_details VALUES ('web', 'web', 'Web Client', 'http://andaily.com', 'http://andaily.com/favicon.ico', 'web-resource', 'read write', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:7777/authorization_code_callback', '22', '-1', '-1', null, '2016-05-17 17:15:56', '0', '0');

-- ----------------------------
-- Table structure for `oauth_code`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `archived` tinyint(1) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `guid` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO roles VALUES ('21', 'f5ede825-1c0f-11e6-848c-d89d67299c10', '2016-05-17 17:15:55', '0', '0', 'Admin');
INSERT INTO roles VALUES ('22', 'f5edec6d-1c0f-11e6-848c-d89d67299c10', '2016-05-17 17:15:55', '0', '0', 'User');

-- ----------------------------
-- Table structure for `roles_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
  `roles_id` int(11) NOT NULL,
  `permission` varchar(255) NOT NULL,
  KEY `roles_id_index` (`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO roles_permissions VALUES ('21', 'user:create');
INSERT INTO roles_permissions VALUES ('21', 'user:edit');
INSERT INTO roles_permissions VALUES ('21', 'user:list');
INSERT INTO roles_permissions VALUES ('21', 'user:delete');
INSERT INTO roles_permissions VALUES ('22', 'user:list');
INSERT INTO roles_permissions VALUES ('22', 'user:edit');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guid` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `archived` tinyint(1) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `default_user` tinyint(1) DEFAULT '0',
  `last_login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `guid` (`guid`),
  UNIQUE KEY `username` (`username`),
  KEY `username_index` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO users VALUES ('21', 'f5dddffa-1c0f-11e6-848c-d89d67299c10', '2016-05-17 17:15:55', '0', '0', '21232f297a57a5a743894a0e4a801fc3', 'admin', '1', null);
INSERT INTO users VALUES ('22', 'f5dde688-1c0f-11e6-848c-d89d67299c10', '2016-05-17 17:15:55', '0', '0', '098f6bcd4621d373cade4e832627b4f6', 'test', '0', null);

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `users_id_index` (`users_id`),
  KEY `roles_id_index` (`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO user_roles VALUES ('21', '21');
INSERT INTO user_roles VALUES ('22', '22');
