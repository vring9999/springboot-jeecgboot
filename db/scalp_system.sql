/*
 Navicat Premium Data Transfer

 Source Server         : ..
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : localhost:3306
 Source Schema         : scalp_system

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 22/05/2020 11:33:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for common_config
-- ----------------------------
DROP TABLE IF EXISTS `common_config`;
CREATE TABLE `common_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `CFG_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称',
  `CFG_KEY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名',
  `CFG_VALUE` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '属性值',
  `CFG_REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `CFG_TYPE` int(4) NULL DEFAULT NULL COMMENT '1:普通配置   2：秘钥配置',
  `cfg_level` int(4) NULL DEFAULT NULL COMMENT '配置级别 ',
  PRIMARY KEY (`id`, `CFG_KEY`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of common_config
-- ----------------------------
INSERT INTO `common_config` VALUES ('14286990-7dc9-481f-a9af-a804fa4de798', 'aliyun.properties', 'sms.temp.login.check', '***', '阿里云短信验证模板', 2, NULL);
INSERT INTO `common_config` VALUES ('1f1c7966-3f79-4779-a965-aad910c27e67', 'qiniu.properties', 'qiniu.path', 'http://qny.hrcx.top/', '', 1, NULL);
INSERT INTO `common_config` VALUES ('32r2s890-s3jh8-x84j839x-3j9399s4j8joiw3', 'scalp.order', 'profit.type', '1', '1:码商利润为基准分销  2：下级代理盘剥制', 1, NULL);
INSERT INTO `common_config` VALUES ('4s8dhf89-sg79-ay8hr3992-f8h3y8d3u8u8', 'scalp.order', 'over.time', '30', '订单过期时间', 1, NULL);
INSERT INTO `common_config` VALUES ('5654be15-2155-4f8e-bd55-1690ef361524', 'qiniu.properties', 'qiniu.secretKey', '***', -8ftDpvVGVWrR-JJm06FFeRu', '', 1, NULL);
INSERT INTO `common_config` VALUES ('73a168003926c43875758464a2fa9496fb1', 'default', 'default.merchant.groupname', '普通用户组', '默认注册商户为普通用户组', 1, NULL);
INSERT INTO `common_config` VALUES ('73a168003926c43875758464a2fa9496fb8', 'default', 'default.user.groupname', '普通用户组', '默认注册码商为普通用户组', 1, NULL);
INSERT INTO `common_config` VALUES ('7d7deaaa-0e75-45e7-a7b3-1d4662c4e704', 'qiniu.properties', 'qiniu.accessKey', '***', '', 1, NULL);
INSERT INTO `common_config` VALUES ('91c94b94-ef16-4b9d-bb20-c21196ecbdcc', 'qiniu.properties', 'qiniu.bucket', '***', '', 1, NULL);
INSERT INTO `common_config` VALUES ('97f2c00a-d9ae-4f7c-9a6d-1eb93036e808', 'aliyun.properties', 'sms.access.secret', '***', '阿里云短信access_secret', 2, NULL);
INSERT INTO `common_config` VALUES ('9ea1f239-cf7c-4a30-b834-b504cd1ac831', 'aliyun.properties', 'sms.access.id', '***', '阿里云短信access_id', 2, NULL);
INSERT INTO `common_config` VALUES ('bcc9ace7-52b2-4e1f-aeb0-3b71a859ed24', 'aliyun.properties', ''***', ', '创享网络', '公司签名', 1, NULL);
INSERT INTO `common_config` VALUES ('f83j209s-sj983-x8j039kj9-2jd82j8dj29joi9s', 'scalp.merchant', 'with.balance', '0.05', '商户提现手续费', 1, NULL);
INSERT INTO `common_config` VALUES ('fuio12jo-j38df3-ja839v93-v983k0ak3v703h', 'scalp.order', 'ranking.small', '100000', '排行统计分水岭', 1, NULL);

-- ----------------------------
-- Table structure for farming_bank
-- ----------------------------
DROP TABLE IF EXISTS `farming_bank`;
CREATE TABLE `farming_bank`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属码商',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '持卡人姓名',
  `bank_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行名',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户行地址',
  `max_amount` int(11) NULL DEFAULT 0 COMMENT '最大收款额',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '启用状态：1 可用（true）0不可用（false）',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_bank
-- ----------------------------
INSERT INTO `farming_bank` VALUES (2, '111', '柳如是', '邮政储蓄银行', '6217995541113055558', '长沙市天心区万家丽北路陕西', 0, NULL, 1, '2020-03-20 14:19:47', '2020-04-02 14:12:55', NULL);
INSERT INTO `farming_bank` VALUES (3, 'admin', '约翰·摩西·勃朗宁', '工商银行', '6217995541113055158', '临夏回族自治州', 0, '系统账号', 1, '2020-04-02 11:54:09', '2020-04-02 11:54:12', NULL);
INSERT INTO `farming_bank` VALUES (4, '111', '司马焦', '邮政储蓄银行', '6217995541113055551', NULL, 0, NULL, 0, '2020-04-02 14:31:30', '2020-04-02 14:31:30', NULL);

-- ----------------------------
-- Table structure for farming_code
-- ----------------------------
DROP TABLE IF EXISTS `farming_code`;
CREATE TABLE `farming_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户id',
  `code` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_code
-- ----------------------------
INSERT INTO `farming_code` VALUES (41, '92725423', 'UVH79Evn7x', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (42, '92725423', 'PJZt3Al6iM', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (43, '92725423', 'zKY7JQOWm6', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (44, '92725423', 'j793DYkNpM', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (45, '92725423', 'HVWeahyj4V', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (46, '92725423', 'vzLOd4y6E4', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (47, '92725423', 'GVGuH2jccD', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (48, '92725423', 'im6TaLRUPv', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (49, '92725423', 'FGb9uA18Qr', '2020-03-11 09:19:28', NULL);
INSERT INTO `farming_code` VALUES (50, '92725423', '9oe1EGxDl4', '2020-03-11 09:19:28', NULL);

-- ----------------------------
-- Table structure for farming_group
-- ----------------------------
DROP TABLE IF EXISTS `farming_group`;
CREATE TABLE `farming_group`  (
  `id` int(11) NOT NULL,
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户组名称',
  `default_per` float(10, 3) NULL DEFAULT NULL COMMENT '默认佣金费率/费率',
  `alipay` float(10, 1) NULL DEFAULT NULL COMMENT '支付宝佣金费率/费率',
  `wechat` float(10, 1) NULL DEFAULT NULL COMMENT '微信佣金费率/费率',
  `type` int(2) NULL DEFAULT NULL COMMENT '1  码商用户组   2商户用户组',
  `sort` int(4) NULL DEFAULT NULL COMMENT '等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_group
-- ----------------------------
INSERT INTO `farming_group` VALUES (1, '普通用户组', 0.010, 1.0, 1.2, 1, 5);
INSERT INTO `farming_group` VALUES (2, '白金用户组', 0.010, 0.0, 1.3, 1, 4);
INSERT INTO `farming_group` VALUES (3, '黄金用户组', 0.010, 1.2, 1.4, 1, 3);
INSERT INTO `farming_group` VALUES (4, '至尊用户组', 0.010, 1.3, 1.5, 1, 1);
INSERT INTO `farming_group` VALUES (5, '普通用户组', 0.010, 3.3, 3.8, 2, 5);
INSERT INTO `farming_group` VALUES (6, '白金用户组', 0.010, 3.3, 3.5, 2, 4);
INSERT INTO `farming_group` VALUES (7, '黄金用户组', 0.010, 3.3, 3.5, 2, 3);
INSERT INTO `farming_group` VALUES (8, '至尊用户组', 0.010, 3.3, 3.3, 2, 1);
INSERT INTO `farming_group` VALUES (9, '铂金用户组', 0.010, 1.2, 1.2, 1, 2);
INSERT INTO `farming_group` VALUES (10, '铂金用户组', 0.010, 1.2, 1.2, 2, 2);

-- ----------------------------
-- Table structure for farming_manghe
-- ----------------------------
DROP TABLE IF EXISTS `farming_manghe`;
CREATE TABLE `farming_manghe`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `big_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大图',
  `shop_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '上架状态   1上架  0下架',
  `profit` int(11) NULL DEFAULT NULL COMMENT '可赚金额',
  `price` int(11) NULL DEFAULT 0 COMMENT '售价',
  `special_price` int(11) NULL DEFAULT 0 COMMENT '特供价',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_manghe
-- ----------------------------
INSERT INTO `farming_manghe` VALUES ('1', '盲盒1', 'http://qny.hrcx.top/', NULL, '盲盒盲盒盲盒', 1, 50, 2000, NULL, NULL, NULL);
INSERT INTO `farming_manghe` VALUES ('2', '盲盒2', 'http://qny.hrcx.top/', NULL, '盲盒盲盒盲盒', 1, 60, 500, 0, NULL, NULL);

-- ----------------------------
-- Table structure for farming_menu
-- ----------------------------
DROP TABLE IF EXISTS `farming_menu`;
CREATE TABLE `farming_menu`  (
  `MENU_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单ID',
  `MENU_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端组件/路由',
  `MENU_URL` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路径 ',
  `PARENT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点ID',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `menu_type` int(4) NULL DEFAULT 1 COMMENT '菜单类型 0:一级菜单; 1:子菜单:2:按钮权限',
  `RESERVE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保留字段',
  `is_route` tinyint(1) NULL DEFAULT 1 COMMENT '是否为路由菜单 1是   0否',
  `hidden` int(2) NULL DEFAULT 0 COMMENT '是否隐藏路由: 0否,1是',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限编码',
  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单跳转地址',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_menu
-- ----------------------------
INSERT INTO `farming_menu` VALUES ('3', '菜单新增', '/menu/add', '/menu/add', '2', 21, 1, NULL, 1, 0, NULL, NULL, NULL);
INSERT INTO `farming_menu` VALUES ('4', '菜单修改', '/menu/update', '/menu/update', '2', 22, 1, NULL, 1, 0, NULL, NULL, NULL);
INSERT INTO `farming_menu` VALUES ('5', '统计报告', '/order/countlist', '/order/countlist', '2', 1, 1, NULL, 0, 0, 'vvv', '1', NULL);

-- ----------------------------
-- Table structure for farming_merchant
-- ----------------------------
DROP TABLE IF EXISTS `farming_merchant`;
CREATE TABLE `farming_merchant`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户名',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `safety_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全密码',
  `balance` int(11) NULL DEFAULT 0 COMMENT '账户余额',
  `frozen` int(11) NULL DEFAULT 0 COMMENT '订单冻结金额',
  `with_frozen` int(11) NULL DEFAULT NULL COMMENT '提现冻结金额',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `register_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册ip',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `last_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次登录ip',
  `last_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次操作系统',
  `last_city` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次登录城市',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq账号',
  `wechat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信账号',
  `match_state` int(3) NULL DEFAULT 0 COMMENT '订单匹配  0关闭  1开启',
  `secret_key` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '我的密钥',
  `group_name` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属用户组',
  `permanent_lock` int(3) NULL DEFAULT 0 COMMENT '永久锁定 0关闭状态 1打开状态',
  `temp_lock` int(3) NULL DEFAULT 0 COMMENT '临时锁定  0关闭状态  1打开状态  ',
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_merchant
-- ----------------------------
INSERT INTO `farming_merchant` VALUES ('27392011', '五六七', '15965314289', 'f8c23466543ba6464b89078dc1db3ad15b6038b80d37ec75', NULL, 850000, 0, NULL, '2020-03-10 11:12:13', '127.0.0.1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `farming_merchant` VALUES ('41939017', '司马焦', '18974529999', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 150000, 275000, NULL, '2020-03-14 15:25:27', '127.0.0.1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '白金用户组', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `farming_merchant` VALUES ('49075115', '卫庄', '15988886565', '837d81f4d52209fe94c62b9a91002ea9fa63c0ec63734218', NULL, 0, 0, NULL, '2020-03-13 15:54:20', '127.0.0.1', '2020-03-13 17:45:58', '127.0.0.1', 'windows 8.1', '福建省厦门市', NULL, NULL, NULL, 0, 'nFEUWHGF34TQPIUH1', '铂金用户组', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `farming_merchant` VALUES ('80002716', '素素', '18974529999', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 0, 0, NULL, '2020-03-14 15:28:40', '127.0.0.1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '普通用户组', 0, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for farming_message
-- ----------------------------
DROP TABLE IF EXISTS `farming_message`;
CREATE TABLE `farming_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `send_type` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收人类型 ：码商user   商户merchant   全平台full',
  `message_type` int(5) NULL DEFAULT 1 COMMENT '消息类型 2订单  1充值  0提现  3系统公告',
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商或者商户id',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `send_status` int(5) NULL DEFAULT 0 COMMENT '推送状态   0待发送   1成功   2失败',
  `failed_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_message
-- ----------------------------
INSERT INTO `farming_message` VALUES (1, '系统公告', '新品上线', 'full', 4, '', '2020-03-30 17:43:36', 1, NULL, '2020-03-30 17:43:57', NULL);
INSERT INTO `farming_message` VALUES (2, '订单通知', '您有一条新的待确认订单', 'user', 1, '111', '2020-03-30 17:45:57', NULL, NULL, '2020-03-30 17:46:00', NULL);

-- ----------------------------
-- Table structure for farming_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `farming_qr_code`;
CREATE TABLE `farming_qr_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款名',
  `qr_url` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码展示地址',
  `type` int(3) NULL DEFAULT 1 COMMENT '二维码类型   1支付宝   2微信',
  `alipay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝账号  类型为支付宝时必填',
  `max_account` int(11) NULL DEFAULT 0 COMMENT '最大收款',
  `today_account` int(11) NULL DEFAULT 0 COMMENT '今日收款',
  `surplus_account` int(11) NULL DEFAULT 0 COMMENT '今日还可收款',
  `user_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户备注',
  `admin_status` tinyint(1) NULL DEFAULT 1 COMMENT '管理员强制下线  1是  0否',
  `admin_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员强制下线说明',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `poll_status` tinyint(1) NULL DEFAULT 1 COMMENT '轮询开关  0关闭(false)   1开启(true)',
  `use_status` int(1) NULL DEFAULT 1 COMMENT '使用状态  0关闭   1开启   2待审核  3驳回',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_qr_code
-- ----------------------------
INSERT INTO `farming_qr_code` VALUES (1, '1111', '黑麒麟', '9', 1, NULL, 200, 0, 200, NULL, 0, NULL, '2020-03-14 09:53:53', '2020-03-25 11:52:00', 0, 1, 1, NULL);
INSERT INTO `farming_qr_code` VALUES (2, '1111', '黑麒麟', '2', 1, NULL, 600, 0, 600, NULL, 0, '收款已达上限', '2020-03-14 10:13:30', '2020-03-25 11:52:00', 0, 1, 2, NULL);
INSERT INTO `farming_qr_code` VALUES (3, '1111', '黑麒麟', '1', 2, NULL, 500, 0, 500, NULL, 0, '收款已达上限', '2020-03-14 10:20:50', '2020-03-25 11:52:00', 0, 1, 3, NULL);
INSERT INTO `farming_qr_code` VALUES (4, '1111', '黑麒麟', '1', 1, NULL, 20000, 0, 20000, NULL, 0, NULL, '2020-03-14 10:49:47', '2020-03-27 15:43:48', 0, 1, 4, NULL);
INSERT INTO `farming_qr_code` VALUES (5, '1111111', '小高', '56', 1, NULL, 50000000, 0, 50000000, NULL, 0, NULL, NULL, '2020-04-02 10:28:41', 1, 1, NULL, NULL);
INSERT INTO `farming_qr_code` VALUES (6, 'admin', NULL, '11111111111111111111', 1, NULL, 5, 0, 5, NULL, 1, NULL, NULL, NULL, 1, 1, NULL, NULL);
INSERT INTO `farming_qr_code` VALUES (7, 'admin', NULL, '222', 1, NULL, 4, 0, 4, NULL, 1, NULL, NULL, NULL, 1, 1, NULL, NULL);
INSERT INTO `farming_qr_code` VALUES (8, '111', NULL, 'http://qny.hrcx.top//20200409164317927', 1, NULL, 3, 0, 3, NULL, 1, NULL, '2020-04-09 16:43:19', '2020-04-09 16:43:19', 1, 2, NULL, NULL);
INSERT INTO `farming_qr_code` VALUES (9, '111', NULL, 'http://qny.hrcx.top//20200409164410916', 1, NULL, 3, 0, 3, NULL, 1, NULL, '2020-04-09 16:44:12', '2020-04-09 16:44:12', 1, 2, NULL, NULL);

-- ----------------------------
-- Table structure for farming_role
-- ----------------------------
DROP TABLE IF EXISTS `farming_role`;
CREATE TABLE `farming_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_pinyin` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_role
-- ----------------------------
INSERT INTO `farming_role` VALUES ('1', '11', '管理员', 'admin', NULL, NULL, NULL);
INSERT INTO `farming_role` VALUES ('2', '22', '超级管理员', 'superadmin', NULL, NULL, NULL);
INSERT INTO `farming_role` VALUES ('3', '33', '码商', 'user', NULL, NULL, NULL);
INSERT INTO `farming_role` VALUES ('4', '44', '商户', 'merchant', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for farming_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `farming_role_menu`;
CREATE TABLE `farming_role_menu`  (
  `PERMISSION_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限ID',
  `ROLE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `MENU_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
  `RESERVE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保留字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_role_menu
-- ----------------------------
INSERT INTO `farming_role_menu` VALUES ('1240176372437626882', '1', '1', NULL, '2020-03-18 15:21:03');
INSERT INTO `farming_role_menu` VALUES ('1240183084783640577', '1', '2', NULL, '2020-03-18 15:47:44');

-- ----------------------------
-- Table structure for farming_sub_commission
-- ----------------------------
DROP TABLE IF EXISTS `farming_sub_commission`;
CREATE TABLE `farming_sub_commission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理收益明细id',
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联订单',
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商id',
  `one_user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级码商id',
  `two_user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级码商id',
  `three_user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '三级码商id',
  `four_user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '四级码商id',
  `one_user_money` int(11) NULL DEFAULT 0 COMMENT '一级码商收益',
  `two_user_money` int(11) NULL DEFAULT 0 COMMENT '二级码商 收益',
  `three_user_money` int(11) NULL DEFAULT 0 COMMENT '三级码商收益',
  `four_user_money` int(11) NULL DEFAULT 0 COMMENT '四级码商收益',
  `proxy_money` int(11) NULL DEFAULT 0 COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_sub_commission
-- ----------------------------
INSERT INTO `farming_sub_commission` VALUES ('b4eee9a7bf133c8f499685be7d643f53', '2020040210164478716506', '1111111', '111', '1111', '11111', '111111', 1, 1, 1, 1, 56, '2020-04-02 10:28:41', '2020-04-02 10:28:41', NULL, NULL);

-- ----------------------------
-- Table structure for farming_system_earn
-- ----------------------------
DROP TABLE IF EXISTS `farming_system_earn`;
CREATE TABLE `farming_system_earn`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `merchant_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户id',
  `audit_sys_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核管理员id',
  `money` int(11) NULL DEFAULT NULL COMMENT '金额',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用1',
  `remark2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for farming_system_log
-- ----------------------------
DROP TABLE IF EXISTS `farming_system_log`;
CREATE TABLE `farming_system_log`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `log_type` int(2) NULL DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
  `log_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志内容',
  `operate_type` int(2) NULL DEFAULT NULL COMMENT '操作类型 1查询，2添加，3修改，4删除,5导入，6导出）',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作用户账号',
  `user_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前登录用户账号类型   admin   user merchant',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录城市',
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_table_userid`(`account`) USING BTREE,
  INDEX `index_logt_ype`(`log_type`) USING BTREE,
  INDEX `index_operate_type`(`operate_type`) USING BTREE,
  INDEX `index_log_type`(`log_type`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for farming_system_user
-- ----------------------------
DROP TABLE IF EXISTS `farming_system_user`;
CREATE TABLE `farming_system_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `safe_code` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全口令',
  `is_super_admin` int(3) NULL DEFAULT 2 COMMENT '是否为超管  1是  2否',
  `lock_status` int(3) NULL DEFAULT 1 COMMENT '锁定状态  0锁定 1正常',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_system_user
-- ----------------------------
INSERT INTO `farming_system_user` VALUES (1, '1', '111', NULL, 2, 1, NULL, NULL, NULL);
INSERT INTO `farming_system_user` VALUES (2, NULL, NULL, NULL, 1, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for farming_user
-- ----------------------------
DROP TABLE IF EXISTS `farming_user`;
CREATE TABLE `farming_user`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '码商id',
  `parent_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级码商id',
  `merchant_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商名',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `id_card` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `safety_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全密码',
  `voucher_money` int(11) NULL DEFAULT 0 COMMENT '抵用券',
  `voucher_frozen` int(11) NULL DEFAULT NULL COMMENT '抵用券冻结',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `register_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册ip',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `last_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次登录城市',
  `last_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次操作系统',
  `last_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次登录ip',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq账号',
  `wechat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信账号',
  `group_name` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属用户组',
  `parent_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册邀请码',
  `register_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '我的邀请码',
  `receive_status` int(3) NULL DEFAULT 1 COMMENT '收款码开关1开启支付宝二维码收款   2开启微信二维码收款',
  `practice_status` int(3) NULL DEFAULT 0 COMMENT '开业状态   0打烊   1开业    2管理员强制打烊',
  `admin_status` int(3) NULL DEFAULT 1 COMMENT '0异常    1正常    （是否已注销）',
  `user_status` int(3) NULL DEFAULT 0 COMMENT '账号状态   0异常   1正常 当码商达到开业条件，管理员给他改成正常之后马上才可以自己开业打烊',
  `receipt_status` int(3) NULL DEFAULT 0 COMMENT '接单状态   0关闭   1开启',
  `chrome_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '谷歌密钥',
  `day_account` int(11) NULL DEFAULT 0 COMMENT '今日收益',
  `proxy_money` int(11) NULL DEFAULT 0 COMMENT '代理收益',
  `proxy_level` int(3) NULL DEFAULT NULL COMMENT '代理等级',
  `remark1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user
-- ----------------------------
INSERT INTO `farming_user` VALUES ('111', NULL, '41939017', '李斯', NULL, 'ppp', NULL, '15978802001', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 75, 0, '2020-03-24 11:55:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '至尊用户组', NULL, NULL, 1, 1, 1, 1, 1, NULL, 0, 75, 1, NULL, NULL, NULL);
INSERT INTO `farming_user` VALUES ('1111', '111', '41939017', '黑麒麟', NULL, 'ppp', NULL, '15588889999', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 107, 0, '2020-03-19 11:56:05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '铂金用户组', NULL, NULL, 1, 1, 1, 1, 1, NULL, 0, 1, 2, NULL, NULL, NULL);
INSERT INTO `farming_user` VALUES ('11111', '1111', '41939017', '白麒麟', NULL, 'ppp', NULL, '15244801315', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 1, 0, '2020-03-25 11:56:09', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '黄金用户组', NULL, NULL, NULL, NULL, 1, NULL, 0, NULL, 0, 1, 3, NULL, NULL, NULL);
INSERT INTO `farming_user` VALUES ('111111', '11111', '41939017', '小白龙', NULL, 'ppp', NULL, '16600001212', NULL, NULL, 59, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '白金用户组', NULL, NULL, NULL, NULL, 1, NULL, 0, NULL, 0, 1, 4, NULL, NULL, NULL);
INSERT INTO `farming_user` VALUES ('1111111', '111111', '41939017', '小高', NULL, 'ppp', NULL, '17680150984', '017d8461ab50093094d7090db80334628839945f4cd5f70d', NULL, 556, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '普通用户组', NULL, NULL, NULL, NULL, 1, NULL, 0, NULL, 0, 0, 5, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for farming_user_collection_log
-- ----------------------------
DROP TABLE IF EXISTS `farming_user_collection_log`;
CREATE TABLE `farming_user_collection_log`  (
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_status` int(3) NULL DEFAULT NULL COMMENT '收款方式更改次数',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更改时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user_collection_log
-- ----------------------------
INSERT INTO `farming_user_collection_log` VALUES ('111', 1, '2020-03-25 00:00:00');
INSERT INTO `farming_user_collection_log` VALUES ('111', 1, '2020-04-09 18:20:01');
INSERT INTO `farming_user_collection_log` VALUES ('111', 1, '2020-04-10 14:11:11');

-- ----------------------------
-- Table structure for farming_user_per
-- ----------------------------
DROP TABLE IF EXISTS `farming_user_per`;
CREATE TABLE `farming_user_per`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `son_per` float(10, 2) NULL DEFAULT 0.00 COMMENT '分配给下级的利润比例',
  `user_per` float(10, 2) NULL DEFAULT 0.00 COMMENT '码商可截留的利润比例',
  `count_per` float(10, 2) NULL DEFAULT 0.00 COMMENT '码商总的返利比例',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码展示路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user_per
-- ----------------------------
INSERT INTO `farming_user_per` VALUES ('1', '111', '黄金', 0.10, 0.05, 0.15, '265987', NULL);
INSERT INTO `farming_user_per` VALUES ('2', '111', '白金', 0.05, 0.10, 0.15, '555555', NULL);
INSERT INTO `farming_user_per` VALUES ('3', '111', '普通', 0.04, 0.11, 0.15, '333333', NULL);

-- ----------------------------
-- Table structure for farming_user_role
-- ----------------------------
DROP TABLE IF EXISTS `farming_user_role`;
CREATE TABLE `farming_user_role`  (
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user_role
-- ----------------------------
INSERT INTO `farming_user_role` VALUES ('1', '111');
INSERT INTO `farming_user_role` VALUES ('1', '41939017');

-- ----------------------------
-- Table structure for farming_user_stock
-- ----------------------------
DROP TABLE IF EXISTS `farming_user_stock`;
CREATE TABLE `farming_user_stock`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商id',
  `m_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `num` int(11) NULL DEFAULT 0 COMMENT '总库存',
  `order_frozen` int(11) NULL DEFAULT 0 COMMENT '订单库存冻结',
  `num_frozen` int(11) NULL DEFAULT 0 COMMENT '充值库存冻结',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用',
  `day_count` int(11) NULL DEFAULT 0 COMMENT '今日已售',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user_stock
-- ----------------------------
INSERT INTO `farming_user_stock` VALUES ('1', '111', '1', 0, 0, 0, NULL, 0);
INSERT INTO `farming_user_stock` VALUES ('2', '1111', '2', 4, 1, 0, NULL, 0);
INSERT INTO `farming_user_stock` VALUES ('3', '1111111', '2', 10, 4, 0, NULL, 0);
INSERT INTO `farming_user_stock` VALUES ('4', '41939017', '2', 12, 1, 0, NULL, 0);

-- ----------------------------
-- Table structure for farming_user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `farming_user_voucher`;
CREATE TABLE `farming_user_voucher`  (
  `id` int(11) NOT NULL COMMENT '抵扣记录id',
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充值订单id',
  `voucher_money` int(11) NULL DEFAULT NULL COMMENT '本次抵扣金额',
  `after_money` int(111) NULL DEFAULT 10 COMMENT '剩余抵扣券',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of farming_user_voucher
-- ----------------------------
INSERT INTO `farming_user_voucher` VALUES (0, NULL, NULL, NULL, 10, '2020-04-27 17:26:32', NULL);

-- ----------------------------
-- Table structure for scalp_account
-- ----------------------------
DROP TABLE IF EXISTS `scalp_account`;
CREATE TABLE `scalp_account`  (
  `id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `account_num` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账目号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '商户号',
  `user_type` int(4) NULL DEFAULT NULL COMMENT '0:商户  1：码商',
  `type` int(4) NULL DEFAULT NULL COMMENT '1：入账充值    0：出账提现',
  `operation_money` int(11) NULL DEFAULT NULL COMMENT '操作金额',
  `actual_money` int(11) NULL DEFAULT NULL COMMENT '实际金额',
  `service_money` int(11) NULL DEFAULT NULL COMMENT '手续费',
  `shop_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `shop_num` int(11) NULL DEFAULT NULL COMMENT '操作商品数量',
  `receipt_bank` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款银行',
  `receipt_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款人',
  `bank_number` bigint(20) NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_num` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行流水号',
  `status` int(4) NULL DEFAULT NULL COMMENT '申请状态 1:待处理  2：驳回   3：通过',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `system_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核管理员id',
  `is_sub` tinyint(1) NULL DEFAULT NULL COMMENT '是否使用佣金   1是 0否',
  `sub_money` int(11) NULL DEFAULT NULL COMMENT '抵消佣金',
  `reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of scalp_account
-- ----------------------------
INSERT INTO `scalp_account` VALUES ('1', '2020040215478899021', 1111, 1, 1, 90000, 89000, 0, '2', 1, '邮政储蓄银行', '柳如是', 6217995540005849497, '0', 1, NULL, NULL, NULL, NULL, 1, 1000, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for scalp_account_log
-- ----------------------------
DROP TABLE IF EXISTS `scalp_account_log`;
CREATE TABLE `scalp_account_log`  (
  `record_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动记录id',
  `account_num` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账目号',
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_type` int(4) NULL DEFAULT NULL COMMENT '用户类型   0：商户   1：码商',
  `is_show` int(4) NULL DEFAULT NULL COMMENT '是否显示  0：不显示  1：显示',
  `old_money` int(11) NULL DEFAULT NULL COMMENT '操作前金额',
  `now_money` int(11) NULL DEFAULT NULL COMMENT '当前金额',
  `change_money` int(11) NULL DEFAULT NULL COMMENT '变动金额',
  `shop_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `shop_old_num` int(11) NULL DEFAULT NULL COMMENT '商品操作前数量',
  `shop_now_num` int(11) NULL DEFAULT NULL COMMENT '商品当前数量',
  `shop_num` int(11) NULL DEFAULT NULL COMMENT '商品操作数量',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出入账  + -',
  `record_type` int(11) NULL DEFAULT NULL COMMENT '订单类型  0提现 1充值 2订单',
  `put_type` int(4) NULL DEFAULT NULL COMMENT '收款方式  1：支付宝   2：微信',
  `rate_ratio` float(11, 4) NULL DEFAULT NULL COMMENT '费率',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留1',
  `reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留2',
  `reserved3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留3',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of scalp_account_log
-- ----------------------------
INSERT INTO `scalp_account_log` VALUES ('4bef052517d4dad0f93b77bee49a391a', '2020052112020945182568', '1111111', 1, 0, NULL, NULL, NULL, '2', 10, 10, 0, '+', 2, NULL, 0.0100, '', '2020-05-21 12:02:14', '2020-05-21 12:02:14', NULL, NULL, NULL);
INSERT INTO `scalp_account_log` VALUES ('55100f80159b01a7be59e4d676e87ef2', '2020052112020945182568', '41939017', 0, 0, 150000, 200000, 50000, NULL, NULL, NULL, NULL, '+', 2, NULL, 0.0000, '客户支付金额：500元', '2020-05-21 12:02:14', '2020-05-21 12:02:14', NULL, NULL, NULL);
INSERT INTO `scalp_account_log` VALUES ('5f8d3c85edebd64ed5f4d8867d1541e5', '2020040210164478716506', '41939017', 0, 1, 100000, 150000, 50000, NULL, NULL, NULL, 1, '+', 2, 1, 0.0000, '客户支付金额：500元', '2020-04-02 10:16:44', '2020-04-02 10:28:40', NULL, NULL, NULL);
INSERT INTO `scalp_account_log` VALUES ('6ed488da5972e69030f377305e25ce12', '2020040210164478716506', '1111111', 1, 1, NULL, NULL, NULL, '2', 12, 11, 1, '-', 2, 1, 0.0000, '扣款可用库存：1个', '2020-04-02 10:16:44', '2020-04-02 10:28:40', NULL, NULL, NULL);
INSERT INTO `scalp_account_log` VALUES ('b4fa496b961cb6a0985f969555689fe9', '2020052112020945182568', '1111111', 1, 0, NULL, NULL, NULL, '2', 11, 10, 1, '-', 2, NULL, 0.0000, '扣款可用库存：1个', '2020-05-21 12:02:14', '2020-05-21 12:02:14', NULL, NULL, NULL);
INSERT INTO `scalp_account_log` VALUES ('d0c71e7fd35075f7018c934b9f7626db', '2020040210164478716506', '1111111', 1, 1, NULL, NULL, NULL, '2', 11, 11, 1, '+', 2, 1, NULL, '订单利润(添加至抵用金额)：0.56元', '2020-04-02 10:16:44', '2020-04-02 10:28:40', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for scalp_order
-- ----------------------------
DROP TABLE IF EXISTS `scalp_order`;
CREATE TABLE `scalp_order`  (
  `order_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `out_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部订单',
  `user_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '码商id',
  `merchant_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户id',
  `earn_money` int(11) NULL DEFAULT 0 COMMENT '收款金额',
  `user_earn` int(11) NULL DEFAULT 0 COMMENT '码商收益',
  `merchant_earn` int(11) NULL DEFAULT 0 COMMENT '商户收益',
  `qr_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码id',
  `qr_type` int(2) NULL DEFAULT NULL COMMENT '二维码类型   1支付宝  2微信',
  `user_pass_type` int(4) NULL DEFAULT 2 COMMENT '码商确认方式   0：手动确认   1：后台确认  2未确认',
  `pay_type` int(4) NULL DEFAULT 0 COMMENT '付款确认  0：未操作  1：确认支付  2取消支付',
  `order_status` int(4) NULL DEFAULT 0 COMMENT '订单状态  0：未支付   1：已支付   2:已超时  3取消',
  `pay_status` int(4) NULL DEFAULT 0 COMMENT '是否正常回调   0：未回调   1：已回调',
  `is_unusual` int(4) NULL DEFAULT 0 COMMENT '是否异常单      0：否    1：是',
  `plat_ratio` float(11, 4) NULL DEFAULT 0.0000 COMMENT '平台利润比例',
  `agency_ratio` float(11, 4) NULL DEFAULT 0.0000 COMMENT '代理分润',
  `shop_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `shop_num` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `back_num` int(4) NULL DEFAULT 0 COMMENT '回调次数',
  `order_time` bigint(20) NULL DEFAULT NULL COMMENT '订单时间',
  `remit_time` bigint(20) NULL DEFAULT NULL COMMENT '收款时间',
  `back_time` bigint(20) NULL DEFAULT NULL COMMENT '回调时间',
  `past_time` bigint(20) NULL DEFAULT NULL COMMENT '过期时间',
  `system_id` varchar(144) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核管理员id',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留1',
  `reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留2',
  `reserved3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留3',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of scalp_order
-- ----------------------------
INSERT INTO `scalp_order` VALUES ('2020040210150111504749', '42927410', '1111111', '41939017', 50000, 0, 0, '5', 1, 2, 0, 2, 0, 0, 0.0000, 0.0000, '2', 1, 0, 1585793701100, NULL, NULL, 1585793761100, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `scalp_order` VALUES ('2020040210164478716506', '42927410', '1111111', '41939017', 50000, 56, 50000, '5', 1, 1, 0, 1, 1, 0, 0.0000, 0.0000, '2', 1, 0, 1585793804263, 1585794241979, NULL, 1585793864263, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `scalp_order` VALUES ('2020052112020945182568', '4292741111', '1111111', '41939017', 50000, 0, 0, '5', 1, 2, 0, 0, 0, 0, 0.0000, 0.0000, '2', 1, 0, 1590033727010, NULL, NULL, 1590033787010, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `scalp_order` VALUES ('2020052114070731912493', '42927411as1', '1111111', '41939017', 50000, 0, 0, '5', 1, 2, 0, 2, 0, 0, 0.0000, 0.0000, '2', 1, 0, 1590041226396, NULL, NULL, 1590041286396, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
