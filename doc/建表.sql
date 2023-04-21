/*
Navicat MySQL Data Transfer

Source Server         : aliyun_mqp
Source Server Version : 80027
Source Host           : 139.196.8.79:3306
Source Database       : mqp_iot

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2021-12-25 10:47:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acc
-- ----------------------------
DROP TABLE IF EXISTS `acc`;
CREATE TABLE `acc` (
`id`  int NOT NULL AUTO_INCREMENT ,
`x`  int NULL DEFAULT NULL ,
`y`  int NULL DEFAULT NULL ,
`z`  int NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
AUTO_INCREMENT=807

;


-- ----------------------------
-- Table structure for hum
-- ----------------------------
DROP TABLE IF EXISTS `hum`;
CREATE TABLE `hum` (
`id`  int NOT NULL AUTO_INCREMENT ,
`temp`  double NULL DEFAULT NULL ,
`hum`  double NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
AUTO_INCREMENT=260

;

-- ----------------------------
-- Auto increment value for acc
-- ----------------------------
ALTER TABLE `acc` AUTO_INCREMENT=807;

-- ----------------------------
-- Auto increment value for hum
-- ----------------------------
ALTER TABLE `hum` AUTO_INCREMENT=260;
