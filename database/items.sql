/*
Navicat MySQL Data Transfer

Source Server         : MySQL50
Source Server Version : 50067
Source Host           : localhost:3306
Source Database       : shopping

Target Server Type    : MYSQL
Target Server Version : 50067
File Encoding         : 65001

Date: 2014-08-27 12:12:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------items
-- Table structure for items
-- ----------------------------
-- CREATE DATABASE `shopping` DEFAULT CHARACTER set utf8 COLLATE utf8_general_ci;
-- USE shopping;

DROP TABLE IF EXISTS `shopping`.`items`;
CREATE TABLE `shopping`.`items` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `supplierid` varchar(8) DEFAULT NULL,
  `city` varchar(50) default NULL,
  `price` int(11) default NULL,
  `number` int(11) default NULL,
  `picture` varchar(500) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shopping`.`suppliers`;
CREATE TABLE `shopping`.`suppliers` (
  `id` int(11) NOT NULL auto_increment,
  `supplierid` VARCHAR(8) DEFAULT NULL,
  `suppliername` VARCHAR(64) NULL,
  PRIMARY KEY (`id`)
)ENGINE = InnoDB AUTO_INCREMENT=11 DEFAULT CHARACTER SET = utf8;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `shopping`.`items` VALUES ('1', '沃特篮球鞋', '00000001', '佛山', '180', '500', '001.jpg');
INSERT INTO `shopping`.`items` VALUES ('2', '安踏运动鞋', '00000002', '福州', '120', '800', '002.jpg');
INSERT INTO `shopping`.`items` VALUES ('3', '耐克运动鞋', '00000003', '广州', '500', '1000', '003.jpg');
INSERT INTO `shopping`.`items` VALUES ('4', '阿迪达斯T血衫', '00000004', '上海', '388', '600', '004.jpg');
INSERT INTO `shopping`.`items` VALUES ('5', '李宁文化衫', '00000005', '广州', '180', '900', '005.jpg');
INSERT INTO `shopping`.`items` VALUES ('6', '小米3', '00000006', '北京', '1999', '3000', '006.jpg');
INSERT INTO `shopping`.`items` VALUES ('7', 'GioneeS11', '00000007', '深圳', '1999', '1000', '007.jpg');

INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('1', '00000001', 'VOIT.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('2', '00000002', 'ANTA.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('3', '00000003', 'NIKE.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('4', '00000004', 'ADIDAS.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('5', '00000005', 'LINING.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('6', '00000006', 'XIAOMI.Inc');
INSERT INTO `shopping`.`suppliers` (`id`, `supplierid`, `suppliername`) VALUES ('7', '00000007', 'GIONEE.Inc');