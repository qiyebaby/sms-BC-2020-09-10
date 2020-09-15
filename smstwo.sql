/*
Navicat MySQL Data Transfer

Source Server         : mysql-01
Source Server Version : 50560
Source Host           : 127.0.0.1:3306
Source Database       : smstwo

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2020-09-10 09:56:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_advice`
-- ----------------------------
DROP TABLE IF EXISTS `t_advice`;
CREATE TABLE `t_advice` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `person` varchar(50) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`aid`),
  KEY `uid` (`uid`),
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `t_userlogin` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_advice
-- ----------------------------
INSERT INTO `t_advice` VALUES ('1', '2', '2', '2020-09-09 20:08:30', '管理员', '今天上午第二节课不在上课，大家上自习', '10');
INSERT INTO `t_advice` VALUES ('2', '1', '1', '2020-09-09 20:08:30', '系统管理员', '大叔大婶多', '113');

-- ----------------------------
-- Table structure for `t_authority`
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority` (
  `uid` bigint(20) NOT NULL DEFAULT '0',
  `level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES ('1', '4');
INSERT INTO `t_authority` VALUES ('8', '3');
INSERT INTO `t_authority` VALUES ('9', '3');
INSERT INTO `t_authority` VALUES ('10', '3');
INSERT INTO `t_authority` VALUES ('11', '3');
INSERT INTO `t_authority` VALUES ('12', '3');
INSERT INTO `t_authority` VALUES ('13', '3');
INSERT INTO `t_authority` VALUES ('76', '1');
INSERT INTO `t_authority` VALUES ('98', '3');
INSERT INTO `t_authority` VALUES ('107', '3');
INSERT INTO `t_authority` VALUES ('108', '1');
INSERT INTO `t_authority` VALUES ('109', '1');
INSERT INTO `t_authority` VALUES ('110', '1');
INSERT INTO `t_authority` VALUES ('111', '1');
INSERT INTO `t_authority` VALUES ('112', '1');
INSERT INTO `t_authority` VALUES ('113', '1');

-- ----------------------------
-- Table structure for `t_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `cno` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(50) DEFAULT NULL,
  `address` varchar(10) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  `headmaster` int(11) DEFAULT NULL,
  PRIMARY KEY (`cno`),
  KEY `deptno` (`deptno`),
  KEY `headmaster` (`headmaster`),
  CONSTRAINT `t_class_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `t_dept` (`deptno`),
  CONSTRAINT `t_class_ibfk_2` FOREIGN KEY (`headmaster`) REFERENCES `t_tearcher` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('1', '软件工程', '1#-103', '1', '1');
INSERT INTO `t_class` VALUES ('2', '大数据', '1#-205', '1', '2');
INSERT INTO `t_class` VALUES ('3', '社会英语', '2#-106', '2', '3');
INSERT INTO `t_class` VALUES ('4', '翻译一班', '2#-107', '2', '4');
INSERT INTO `t_class` VALUES ('5', '翻译二班', '2#-109', '2', '5');
INSERT INTO `t_class` VALUES ('7', '流行音乐', '3#-402', '3', '7');
INSERT INTO `t_class` VALUES ('10', '软件工', '1#-102', '1', '10');
INSERT INTO `t_class` VALUES ('11', '计算机科学与技术', '1#-303', '1', '6');
INSERT INTO `t_class` VALUES ('12', '阿萨德', '2#-201', '1', '15');

-- ----------------------------
-- Table structure for `t_course`
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `kid` int(11) NOT NULL AUTO_INCREMENT,
  `kname` varchar(50) DEFAULT NULL,
  `kpress` varchar(50) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`kid`),
  KEY `tid` (`tid`),
  CONSTRAINT `t_course_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `t_tearcher` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1', 'c++', '清华大学出版社', '1');
INSERT INTO `t_course` VALUES ('2', 'java', '人民出版社', '1');
INSERT INTO `t_course` VALUES ('3', '大学数学', '清华大学出版社', '2');
INSERT INTO `t_course` VALUES ('4', '大学英语', '人民出版社', '3');
INSERT INTO `t_course` VALUES ('5', '大学物理', '中原出版社', '4');
INSERT INTO `t_course` VALUES ('6', '计算机网络', '中原出版社', '4');
INSERT INTO `t_course` VALUES ('7', '古典音乐鉴赏', '清华大学出版社', '5');
INSERT INTO `t_course` VALUES ('8', '外国英语文化', '清华大学出版社', '6');

-- ----------------------------
-- Table structure for `t_dept`
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `deptname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('1', '计算机学院');
INSERT INTO `t_dept` VALUES ('2', '外国语学院');
INSERT INTO `t_dept` VALUES ('3', '音乐学院');
INSERT INTO `t_dept` VALUES ('4', '文学院');
INSERT INTO `t_dept` VALUES ('5', '地理学院');
INSERT INTO `t_dept` VALUES ('6', '化学学院');

-- ----------------------------
-- Table structure for `t_globalinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_globalinfo`;
CREATE TABLE `t_globalinfo` (
  `info` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_globalinfo
-- ----------------------------
INSERT INTO `t_globalinfo` VALUES ('global_infoStatistics', '499');

-- ----------------------------
-- Table structure for `t_leave`
-- ----------------------------
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave` (
  `sno` varchar(50) NOT NULL DEFAULT '',
  `sname` varchar(50) DEFAULT NULL,
  `cname` varchar(50) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leave
-- ----------------------------
INSERT INTO `t_leave` VALUES ('20171007', '任我行', '软件工程', 'asdas', '2020-08-10', 'asdas');

-- ----------------------------
-- Table structure for `t_room`
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(11) DEFAULT NULL,
  `dorm` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `people` int(11) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `dorm` (`dorm`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_room
-- ----------------------------
INSERT INTO `t_room` VALUES ('18', '12', '1#-101', '批评 : 13', '1');
INSERT INTO `t_room` VALUES ('19', null, '2#-204', null, '1');
INSERT INTO `t_room` VALUES ('20', null, '5#-305', null, '1');
INSERT INTO `t_room` VALUES ('21', null, '2#-302', null, '1');
INSERT INTO `t_room` VALUES ('22', null, '2#-104', null, '1');
INSERT INTO `t_room` VALUES ('23', null, '5#-204', null, '1');
INSERT INTO `t_room` VALUES ('24', null, '4#-202', null, '1');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sno` varchar(11) DEFAULT NULL,
  `sname` varchar(50) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `age` char(10) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `dorm` varchar(11) DEFAULT NULL,
  `cno` int(11) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sno` (`sno`),
  KEY `cno` (`cno`),
  KEY `uid` (`uid`),
  KEY `dorm` (`dorm`),
  CONSTRAINT `t_student_ibfk_1` FOREIGN KEY (`cno`) REFERENCES `t_class` (`cno`),
  CONSTRAINT `t_student_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `t_userlogin` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('19', '20175101001', '陈依依', '0', '19', '654321', '1#-101', '1', '76');
INSERT INTO `t_student` VALUES ('20', '20175101002', '刘表', '1', '20', '123456', '2#-204', '3', '108');
INSERT INTO `t_student` VALUES ('21', '20175101003', '李世明', '1', '20', '123456', '5#-305', '5', '109');
INSERT INTO `t_student` VALUES ('22', '20175101004', '玛丽', '0', '20', '123456', '2#-302', '2', '110');
INSERT INTO `t_student` VALUES ('23', '20175101005', '陈丽', '0', '20', '123456', '2#-104', '4', '111');
INSERT INTO `t_student` VALUES ('24', '20175101006', '马伊', '0', '20', '123456', '5#-204', '2', '112');
INSERT INTO `t_student` VALUES ('25', '20175101007', '玛丽111', '0', '20', '123456', '4#-202', '2', '113');

-- ----------------------------
-- Table structure for `t_tearcher`
-- ----------------------------
DROP TABLE IF EXISTS `t_tearcher`;
CREATE TABLE `t_tearcher` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `realname` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `entrydate` varchar(10) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tearcher
-- ----------------------------
INSERT INTO `t_tearcher` VALUES ('1', '张三N', '1008611as', '2004-08-20', '8');
INSERT INTO `t_tearcher` VALUES ('2', '李四', '1008612', '2004-08-20', '9');
INSERT INTO `t_tearcher` VALUES ('3', '王五', '1008613', '2004-08-20', '10');
INSERT INTO `t_tearcher` VALUES ('4', '赵六', '1008614', '2004-08-20', '11');
INSERT INTO `t_tearcher` VALUES ('5', '陈七', '1008615', '2004-08-20', '12');
INSERT INTO `t_tearcher` VALUES ('6', '刘八', '1008616', '2004-08-20', '13');
INSERT INTO `t_tearcher` VALUES ('7', '周九', '1008617', '2004-08-20', '14');
INSERT INTO `t_tearcher` VALUES ('10', '任君', '123353254', '2020-07-26', '29');
INSERT INTO `t_tearcher` VALUES ('15', '李四222', '123456', '2020-09-06', '98');

-- ----------------------------
-- Table structure for `t_userlogin`
-- ----------------------------
DROP TABLE IF EXISTS `t_userlogin`;
CREATE TABLE `t_userlogin` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(11) NOT NULL,
  `userpwd` varchar(20) NOT NULL,
  `date` varchar(19) DEFAULT NULL,
  `usertype` varchar(1) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userlogin
-- ----------------------------
INSERT INTO `t_userlogin` VALUES ('1', 'admin', '123', '2020-07-19 12:34:30', '0', '1');
INSERT INTO `t_userlogin` VALUES ('8', '1008', '123', '2020-07-19 12:34:37', '2', '1');
INSERT INTO `t_userlogin` VALUES ('9', '1009', '123', '2020-07-19 12:34:38', '2', '1');
INSERT INTO `t_userlogin` VALUES ('10', '1010', '123', '2020-07-19 12:34:39', '2', '1');
INSERT INTO `t_userlogin` VALUES ('11', '1011', '123', '2020-07-19 12:34:40', '2', '1');
INSERT INTO `t_userlogin` VALUES ('12', '1012', '1231232', '2020-07-19 12:34:41', '2', '1');
INSERT INTO `t_userlogin` VALUES ('13', '1013', '123', '2020-07-19 12:34:42', '2', '1');
INSERT INTO `t_userlogin` VALUES ('76', '20175101001', '45678', '2020-09-05 20:51:43', '1', '1');
INSERT INTO `t_userlogin` VALUES ('98', '1020', '123456', '2020-09-06 10:32:01', '2', '1');
INSERT INTO `t_userlogin` VALUES ('107', '啊十大杀手', '123456', '2020-09-06 12:01:44', '0', '1');
INSERT INTO `t_userlogin` VALUES ('108', '20175101002', '123456', '2020-09-07 11:07:44', '1', '1');
INSERT INTO `t_userlogin` VALUES ('109', '20175101003', '123456', '2020-09-07 11:08:00', '1', '1');
INSERT INTO `t_userlogin` VALUES ('110', '20175101004', '123456', '2020-09-07 11:08:27', '1', '1');
INSERT INTO `t_userlogin` VALUES ('111', '20175101005', '123456', '2020-09-07 11:08:48', '1', '1');
INSERT INTO `t_userlogin` VALUES ('112', '20175101006', '123456', '2020-09-07 11:58:35', '1', '1');
INSERT INTO `t_userlogin` VALUES ('113', '20175101007', '123456', '2020-09-07 12:00:52', '1', '1');
