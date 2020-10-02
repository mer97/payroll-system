/*
 Navicat Premium Data Transfer

 Source Server         : local_mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : wage_dw

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 02/10/2020 23:36:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wg_department
-- ----------------------------
DROP TABLE IF EXISTS `wg_department`;
CREATE TABLE `wg_department`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `del_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wg_department
-- ----------------------------
INSERT INTO `wg_department` VALUES ('1042479742f5aa219297daf4c11d061f', '人事部', '0', '2020-10-02 23:24:04', 'admin', NULL, NULL);
INSERT INTO `wg_department` VALUES ('1578382fa3a6f00af12539bd5f12a7b2', '研发部', '0', '2020-10-02 23:23:52', 'admin', NULL, NULL);
INSERT INTO `wg_department` VALUES ('9bd1a5a2bd5c090797a734b943c0cc9c', '财务部', '0', '2020-10-02 23:23:58', 'admin', NULL, NULL);
INSERT INTO `wg_department` VALUES ('b8529f0ed1aef4d1ff95e4dc56453294', '销售部', '0', '2020-10-02 23:24:17', 'admin', NULL, NULL);

-- ----------------------------
-- Table structure for wg_employee
-- ----------------------------
DROP TABLE IF EXISTS `wg_employee`;
CREATE TABLE `wg_employee`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `entry_date` date NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `del_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wg_employee
-- ----------------------------
INSERT INTO `wg_employee` VALUES ('b5bdabc7c7afcc9502d1527b2da2fea6', '', '2017-08-01', '王兴', '18880888888', '1787e1b0d4a4be88fb5094968fd6061d', '6228480402564890019', 'wangxing', '123456', 'ADMIN', '0', '2020-10-02 23:29:44', 'admin', '2020-10-02 23:36:06', NULL, '9bd1a5a2bd5c090797a734b943c0cc9c', 30000);
INSERT INTO `wg_employee` VALUES ('bbd09e5329bec7466be6ca926d4e4cb2', '', '2019-10-01', '李伟', '17770777777', '73e168e11ad9549f812da5bef02c1566', '6228480402564890018', 'liwei', '123456', 'USER', '0', '2020-10-02 23:27:46', 'admin', NULL, NULL, '1578382fa3a6f00af12539bd5f12a7b2', 10000);

-- ----------------------------
-- Table structure for wg_position
-- ----------------------------
DROP TABLE IF EXISTS `wg_position`;
CREATE TABLE `wg_position`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `del_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wg_position
-- ----------------------------
INSERT INTO `wg_position` VALUES ('1787e1b0d4a4be88fb5094968fd6061d', '财务总监', '9bd1a5a2bd5c090797a734b943c0cc9c', '0', '2020-10-02 23:28:55', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('73e168e11ad9549f812da5bef02c1566', 'Java开发工程师（中级）', '1578382fa3a6f00af12539bd5f12a7b2', '0', '2020-10-02 23:25:12', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('9cdcea786c3cb8153ab4c68c9dc1041d', '销售专员', 'b8529f0ed1aef4d1ff95e4dc56453294', '0', '2020-10-02 23:24:36', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('9fddff6341c1ff3114c902e31e13a185', 'Java开发工程师（初级）', '1578382fa3a6f00af12539bd5f12a7b2', '0', '2020-10-02 23:24:57', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('ad39179cdd47413d96493d49c7fe39c0', '产品经理', '1578382fa3a6f00af12539bd5f12a7b2', '0', '2020-10-02 23:25:53', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('b4668e55d85279ac94ee1b9843d3efbe', '项目经理', '1578382fa3a6f00af12539bd5f12a7b2', '0', '2020-10-02 23:25:43', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('b50feafda8d9ee1a59a60d4d5d942a76', '销售经理', 'b8529f0ed1aef4d1ff95e4dc56453294', '0', '2020-10-02 23:24:44', 'admin', NULL, NULL);
INSERT INTO `wg_position` VALUES ('dc22bff00d6df868a354d5130bdb6326', 'Java开发工程师（高级）', '1578382fa3a6f00af12539bd5f12a7b2', '0', '2020-10-02 23:25:23', 'admin', '2020-10-02 23:28:04', 'admin');

-- ----------------------------
-- Table structure for wg_salary
-- ----------------------------
DROP TABLE IF EXISTS `wg_salary`;
CREATE TABLE `wg_salary`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `absenteeism` double NOT NULL,
  `allowance` double NOT NULL,
  `employee_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paid` double NOT NULL,
  `workday` double NOT NULL,
  `del_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `base_salary` double NULL DEFAULT NULL,
  `month` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wg_salary
-- ----------------------------
INSERT INTO `wg_salary` VALUES ('bac020aa6d77848bd922f593eeb89ff2', 0, 1500, 'bbd09e5329bec7466be6ca926d4e4cb2', 11500, 20, '0', '2020-10-02 23:31:18', 'admin', NULL, NULL, '1578382fa3a6f00af12539bd5f12a7b2', '73e168e11ad9549f812da5bef02c1566', 10000, '2019-10', '6228480402564890018');

SET FOREIGN_KEY_CHECKS = 1;
