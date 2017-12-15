/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : huilv_db

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2017-12-12 10:09:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `huilv`
-- ----------------------------
DROP TABLE IF EXISTS `huilv`;
CREATE TABLE `huilv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `huilv` double(11,4) NOT NULL,
  `currency` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of huilv
-- ----------------------------
INSERT INTO `huilv` VALUES ('1', '0.1511', 'US');
INSERT INTO `huilv` VALUES ('2', '0.1282', 'EU');
INSERT INTO `huilv` VALUES ('3', '17.1400', 'JP');
INSERT INTO `huilv` VALUES ('4', '1.1800', 'HK');
