-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: kaka
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_bespeak`
--

DROP TABLE IF EXISTS `t_bespeak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bespeak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `franchiserId` int(11) NOT NULL,
  `consultantId` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `bespeakTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bespeak`
--

LOCK TABLES `t_bespeak` WRITE;
/*!40000 ALTER TABLE `t_bespeak` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_bespeak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_card`
--

DROP TABLE IF EXISTS `t_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `rule` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_card`
--

LOCK TABLES `t_card` WRITE;
/*!40000 ALTER TABLE `t_card` DISABLE KEYS */;
INSERT INTO `t_card` VALUES (1,'重庆公共交通乘车卡','<p>打开支付宝--选择顶部【付款】--点击底部【乘车码】--将乘车码对准公交车上扫码机具--听到【扫码成功】语音提示即可；</p>');
/*!40000 ALTER TABLE `t_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_card_level`
--

DROP TABLE IF EXISTS `t_card_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_card_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cardId` int(11) NOT NULL,
  `level` varchar(16) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `imageUrl` varchar(128) NOT NULL,
  `points` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_card_level`
--

LOCK TABLES `t_card_level` WRITE;
/*!40000 ALTER TABLE `t_card_level` DISABLE KEYS */;
INSERT INTO `t_card_level` VALUES (1,1,'silver','白银卡','http://127.0.0.1:8082/files/image/3/c/0e66c9799f134aa69df5dc0cecc857fe_1.jpg',0),(2,1,'gold','金卡','http://127.0.0.1:8082/files/image/e/b/e21ca91f35cc4380b61bf410b64bde63_14.jpg',400),(5,1,'damond','钻石卡','http://127.0.0.1:8082/files/image/c/f/1ce1cb6e4e134c9bb67a9a05e4813a97_2.jpg',1000);
/*!40000 ALTER TABLE `t_card_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_carousel`
--

DROP TABLE IF EXISTS `t_carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `imageUrl` varchar(128) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_carousel`
--

LOCK TABLES `t_carousel` WRITE;
/*!40000 ALTER TABLE `t_carousel` DISABLE KEYS */;
INSERT INTO `t_carousel` VALUES (1,'衣裤','http://127.0.0.1:8082/files/image/2/b/e825fbf2f3d340c3b55c76388f26fc2d_2.jpg','on','2019-04-12 13:12:32');
/*!40000 ALTER TABLE `t_carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_carousel_plot`
--

DROP TABLE IF EXISTS `t_carousel_plot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_carousel_plot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carouselId` int(11) DEFAULT NULL,
  `plot` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_carousel_plot`
--

LOCK TABLES `t_carousel_plot` WRITE;
/*!40000 ALTER TABLE `t_carousel_plot` DISABLE KEYS */;
INSERT INTO `t_carousel_plot` VALUES (1,1,'<p><img src=\"http://127.0.0.1:8082/files/9/8/883d1113c74848e797525b47b83e61a6_1.jpg\" style=\"width:100%;\"><img src=\"http://127.0.0.1:8082/files/8/4/f214863074db415daa4dd223550d6400_2.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/d/9/d717e429efe34325be2c5c761ac900d3_3.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/2/c/575e03616b824ff78bce73a7519e06c8_4.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/e/b/e262b20042c54275b04d844bac6e4e05_11.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/3/4/b9fc565210084eb598fb669dcb8778b0_12.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/f/0/cb4f8f8e79444a19b23ac41c4f35402c_13.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/d/4/09d8e81476884a759dfbee6bfff9c74e_14.jpg\" style=\"width: 100%;\"><img src=\"http://127.0.0.1:8082/files/e/e/00ed9b5ca066452bb84b7d91efb904f3_15.jpg\" style=\"width: 100%;\"><br></p>');
/*!40000 ALTER TABLE `t_carousel_plot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_consultant`
--

DROP TABLE IF EXISTS `t_consultant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_consultant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `imageUrl` varchar(128) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_consultant`
--

LOCK TABLES `t_consultant` WRITE;
/*!40000 ALTER TABLE `t_consultant` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_consultant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_customer`
--

DROP TABLE IF EXISTS `t_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `openid` varchar(32) NOT NULL,
  `avatarUrl` varchar(128) DEFAULT NULL,
  `status` varchar(16) NOT NULL,
  `createTime` datetime NOT NULL,
  `cardNo` varchar(16) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `points` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_customer`
--

LOCK TABLES `t_customer` WRITE;
/*!40000 ALTER TABLE `t_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_franchiser`
--

DROP TABLE IF EXISTS `t_franchiser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_franchiser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locationId` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `address` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_franchiser`
--

LOCK TABLES `t_franchiser` WRITE;
/*!40000 ALTER TABLE `t_franchiser` DISABLE KEYS */;
INSERT INTO `t_franchiser` VALUES (1,8,'长征长宝宝马4s店','东三环二段龙潭工业园航天路2号');
/*!40000 ALTER TABLE `t_franchiser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_shop`
--

DROP TABLE IF EXISTS `t_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locationId` int(11) NOT NULL,
  `code` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `address` varchar(256) NOT NULL,
  `longitude` varchar(16) NOT NULL,
  `latitude` varchar(16) NOT NULL,
  `radius` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_shop`
--

LOCK TABLES `t_shop` WRITE;
/*!40000 ALTER TABLE `t_shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_auth`
--

DROP TABLE IF EXISTS `t_system_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authCode` varchar(64) DEFAULT NULL,
  `authName` varchar(64) DEFAULT NULL,
  `authType` varchar(16) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_auth`
--

LOCK TABLES `t_system_auth` WRITE;
/*!40000 ALTER TABLE `t_system_auth` DISABLE KEYS */;
INSERT INTO `t_system_auth` VALUES (1,'system.user','人员管理','menu',NULL,0,'2018-03-06 09:09:08'),(2,'system.user.add','新增人员','oper',1,1,'2018-03-06 09:09:32'),(3,'system.user.edit','编辑人员','oper',1,0,'2018-03-06 09:09:49'),(4,'system.user.delete','删除人员','oper',1,1,'2018-03-06 09:10:01'),(5,'system.role','角色管理','menu',NULL,0,'2018-03-06 09:10:54'),(6,'system.role.add','新增角色','oper',5,1,'2018-03-06 09:12:07'),(7,'system.role.edit','编辑角色','oper',5,0,'2018-03-06 09:12:17'),(8,'system.role.delete','删除角色','oper',5,1,'2018-03-06 09:12:27'),(9,'system.auth','权限管理','menu',NULL,0,'2018-03-06 09:13:40'),(10,'system.auth.add','新增权限','oper',9,1,'2018-03-06 09:14:12'),(11,'system.auth.update','修改权限','oper',9,1,'2018-03-06 09:14:23'),(12,'system.auth.delete','删除权限','oper',9,1,'2018-03-06 09:14:37'),(13,'system.auth.detail','查看权限','oper',9,1,'2018-08-23 01:15:00'),(14,'system.user.authorize','人员授权','oper',1,1,'2018-08-23 01:16:49'),(15,'system.role.authorize','角色授权','oper',5,1,'2018-08-23 01:17:34'),(16,'system.setting','个人设置','oper',1,1,'2019-04-12 12:08:56'),(17,'system.config','系统参数','menu',NULL,0,'2019-04-12 12:09:51'),(18,'system.config.add','新增参数','oper',17,1,'2019-04-12 12:10:22'),(19,'system.config.edit','修改参数','oper',17,1,'2019-04-12 12:10:43'),(20,'system.config.delete','删除参数','oper',17,1,'2019-04-12 12:11:06'),(21,'system.log','系统日志','menu',NULL,0,'2019-04-12 12:13:04'),(22,'system.log.detail','查看日志详情','oper',21,1,'2019-04-12 12:14:03'),(23,'carousel','轮播管理','menu',NULL,0,'2019-04-12 12:43:00'),(24,'carousel.add','新增轮播图','oper',23,1,'2019-04-12 12:43:26'),(25,'carousel.update','修改轮播图','oper',23,1,'2019-04-12 12:44:13'),(26,'carousel.up','上架轮播图','oper',23,1,'2019-04-12 12:44:42'),(27,'carousel.delete','删除轮播图','oper',23,1,'2019-04-12 12:45:03'),(28,'carousel.down','下架轮播图','oper',23,1,'2019-04-12 12:45:21'),(29,'customer','会员管理','menu',NULL,0,'2019-04-12 12:47:07'),(30,'customer','会员信息','menu',29,1,'2019-04-12 12:49:54'),(31,'customer.detail','查看会员详情','oper',30,2,'2019-04-12 12:50:44'),(32,'card','会员卡','menu',29,1,'2019-04-12 18:10:21'),(33,'system.location','地区管理','menu',NULL,0,'2019-04-13 08:20:17'),(34,'system.location.detail','查看地区详情','oper',33,1,'2019-04-13 08:20:59'),(35,'system.location.add','新增地区','oper',33,1,'2019-04-13 08:21:20'),(36,'system.location.update','修改地区','oper',33,1,'2019-04-13 08:21:41'),(37,'system.location.delete','删除地区','oper',33,1,'2019-04-13 08:22:01'),(38,'shop','门店管理','menu',NULL,0,'2019-04-13 14:27:26'),(39,'shop.add','新增门店','oper',38,1,'2019-04-13 14:30:20'),(40,'shop.detail','查看门店详情','oper',38,1,'2019-04-13 14:30:46'),(41,'shop.acode','生成积分码','oper',38,1,'2019-04-13 14:31:06'),(42,'shop.update','修改门店','oper',38,1,'2019-04-13 14:31:27'),(43,'shop.delete','删除门店','oper',38,1,'2019-04-13 14:31:47'),(44,'franchiser','经销商','menu',NULL,0,'2019-04-14 07:57:50'),(45,'franchiser.add','新增经销商','oper',44,1,'2019-04-14 07:58:29'),(46,'franchiser.detail','查看经销商详情','oper',44,1,'2019-04-14 07:59:03'),(47,'franchiser.update','修改经销商','oper',44,1,'2019-04-14 07:59:26'),(48,'franchiser.delete','删除经销商','oper',44,1,'2019-04-14 07:59:42'),(49,'consultant','汽车顾问','menu',NULL,0,'2019-04-14 09:31:11'),(50,'consultant.add','新增汽车顾问','oper',49,1,'2019-04-14 09:33:18'),(51,'consultant.update','修改汽车顾问','oper',49,1,'2019-04-14 09:33:47'),(52,'consultant.delete','删除汽车顾问','oper',49,1,'2019-04-14 09:34:05'),(53,'bespeak','预约看车','menu',29,1,'2019-04-14 11:26:03'),(54,'bespeak.detail','查看预约详情','oper',53,2,'2019-04-14 11:26:33');
/*!40000 ALTER TABLE `t_system_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_config`
--

DROP TABLE IF EXISTS `t_system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `propertyName` varchar(64) DEFAULT NULL,
  `propertyValue` varchar(512) DEFAULT NULL,
  `propertyDesc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_config`
--

LOCK TABLES `t_system_config` WRITE;
/*!40000 ALTER TABLE `t_system_config` DISABLE KEYS */;
INSERT INTO `t_system_config` VALUES (1,'wx.appid','wx25fec6dbca9b236b','小程序appid'),(2,'wx.secret','wx.secret','小程序APPID对应的secret'),(3,'wx.acode.page','pages/index/index','小程序积分码跳转页面');
/*!40000 ALTER TABLE `t_system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_location`
--

DROP TABLE IF EXISTS `t_system_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locationCode` varchar(32) NOT NULL,
  `locationName` varchar(512) NOT NULL,
  `locationCodePath` varchar(512) NOT NULL,
  `locationNamePath` varchar(512) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `level` smallint(6) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_location`
--

LOCK TABLES `t_system_location` WRITE;
/*!40000 ALTER TABLE `t_system_location` DISABLE KEYS */;
INSERT INTO `t_system_location` VALUES (1,'重庆市','重庆市','重庆市','重庆市',NULL,0,'2019-04-13 08:25:29'),(2,'四川省','四川省','四川省','四川省',NULL,0,'2019-04-13 08:25:49'),(3,'成都市','成都市','四川省/成都市','四川省/成都市',2,1,'2019-04-13 08:26:05'),(4,'浙江省','浙江省','浙江省','浙江省',NULL,0,'2019-04-13 08:26:20'),(5,'北京市','北京市','北京市','北京市',NULL,0,'2019-04-13 08:26:39'),(6,'海淀区','海淀区','北京市/海淀区','北京市/海淀区',5,1,'2019-04-13 08:26:51'),(7,'云阳县','云阳县','重庆市/云阳县','重庆市/云阳县',1,1,'2019-04-13 08:27:07'),(8,'成华区','成华区','四川省/成都市/成华区','四川省/成都市/成华区',3,2,'2019-04-14 08:39:49');
/*!40000 ALTER TABLE `t_system_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_log`
--

DROP TABLE IF EXISTS `t_system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientIp` varchar(16) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userName` varchar(64) DEFAULT NULL,
  `params` varchar(1024) DEFAULT NULL,
  `module` varchar(32) DEFAULT NULL,
  `operType` varchar(32) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `result` varchar(256) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `operateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_log`
--

LOCK TABLES `t_system_log` WRITE;
/*!40000 ALTER TABLE `t_system_log` DISABLE KEYS */;
INSERT INTO `t_system_log` VALUES (1,'192.168.0.103',1,'admin','{\"userName\":\"CCu5bdaoPRs=\"}','shrio','login',NULL,'成功',309,'登录','2019-04-12 12:05:01'),(2,'192.168.0.103',1,'admin','{\"authName\":\"个人设置\",\"authCode\":\"system.setting\",\"authType\":\"oper\",\"parentId\":\"1\"}','auth','add',NULL,'成功',50,'新增权限','2019-04-12 12:08:56'),(3,'192.168.0.103',1,'admin','{\"authName\":\"参数管理\",\"authCode\":\"system.config\",\"authType\":\"menu\"}','auth','add',NULL,'成功',42,'新增权限','2019-04-12 12:09:51'),(4,'192.168.0.103',1,'admin','{\"authName\":\"新增参数\",\"authCode\":\"system.config.add\",\"authType\":\"oper\",\"parentId\":\"17\"}','auth','add',NULL,'成功',37,'新增权限','2019-04-12 12:10:22'),(5,'192.168.0.103',1,'admin','{\"authName\":\"修改参数\",\"authCode\":\"system.config.edit\",\"authType\":\"oper\",\"parentId\":\"17\"}','auth','add',NULL,'成功',39,'新增权限','2019-04-12 12:10:42'),(6,'192.168.0.103',1,'admin','{\"authName\":\"删除参数\",\"authCode\":\"system.config.delete\",\"authType\":\"oper\",\"parentId\":\"17\"}','auth','add',NULL,'成功',48,'新增权限','2019-04-12 12:11:06'),(7,'192.168.0.103',1,'admin','{\"authName\":\"系统日志\",\"authCode\":\"system.log\",\"authType\":\"menu\"}','auth','add',NULL,'成功',51,'新增权限','2019-04-12 12:13:04'),(8,'192.168.0.103',1,'admin','{\"authCode\":\"system.config\",\"level\":\"0\",\"createTime\":\"2019-04-12 20:09:51\",\"authName\":\"系统参数\",\"id\":\"17\",\"authType\":\"menu\",\"authTypeName\":\"菜单权限\"}','auth','update',NULL,'成功',30,'修改权限','2019-04-12 12:13:16'),(9,'192.168.0.103',1,'admin','{\"authName\":\"查看日志详情\",\"authCode\":\"system.log.detail\",\"authType\":\"oper\",\"parentId\":\"21\"}','auth','add',NULL,'成功',47,'新增权限','2019-04-12 12:14:03'),(10,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',2,'退出','2019-04-12 12:14:08'),(11,'192.168.0.103',1,'admin','{\"userName\":\"63hFGEPDIMQ=\"}','shrio','login',NULL,'成功',10,'登录','2019-04-12 12:14:17'),(12,'192.168.0.103',1,'admin','{\"userName\":\"/9CJoWKxU6Q=\"}','shrio','login',NULL,'成功',365,'登录','2019-04-12 12:18:22'),(13,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',1,'退出','2019-04-12 12:20:13'),(14,'192.168.0.103',1,'admin','{\"userName\":\"eauYXKqvyqs=\"}','shrio','login',NULL,'成功',15,'登录','2019-04-12 12:20:21'),(15,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',0,'退出','2019-04-12 12:32:38'),(16,'192.168.0.103',1,'admin','{\"userName\":\"Noaxmli2ydM=\"}','shrio','login',NULL,'成功',12,'登录','2019-04-12 12:32:54'),(17,'192.168.0.103',1,'admin','{\"roleName\":\"系统管理员\"}','role','add',NULL,'成功',66,'新增角色','2019-04-12 12:34:07'),(18,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15],\"roleId\":1}','role','authorize','新增权限[1,2,3,4,14,16,5,6,7,8,15]','成功',375,'权限分配','2019-04-12 12:35:52'),(19,'192.168.0.103',1,'admin','{\"authName\":\"轮播管理\",\"authCode\":\"carousel\",\"authType\":\"menu\"}','auth','add',NULL,'成功',85,'新增权限','2019-04-12 12:43:00'),(20,'192.168.0.103',1,'admin','{\"authName\":\"新增轮播图\",\"authCode\":\"carousel.add\",\"authType\":\"oper\",\"parentId\":\"23\"}','auth','add',NULL,'成功',118,'新增权限','2019-04-12 12:43:26'),(21,'192.168.0.103',1,'admin','{\"authName\":\"修改轮播图\",\"authCode\":\"carousel.update\",\"authType\":\"oper\",\"parentId\":\"23\"}','auth','add',NULL,'成功',45,'新增权限','2019-04-12 12:44:13'),(22,'192.168.0.103',1,'admin','{\"authName\":\"上架轮播图\",\"authCode\":\"carousel.up\",\"authType\":\"oper\",\"parentId\":\"23\"}','auth','add',NULL,'成功',45,'新增权限','2019-04-12 12:44:42'),(23,'192.168.0.103',1,'admin','{\"authName\":\"删除轮播图\",\"authCode\":\"carousel.delete\",\"authType\":\"oper\",\"parentId\":\"23\"}','auth','add',NULL,'成功',45,'新增权限','2019-04-12 12:45:03'),(24,'192.168.0.103',1,'admin','{\"authName\":\"下架轮播图\",\"authCode\":\"carousel.down\",\"authType\":\"oper\",\"parentId\":\"23\"}','auth','add',NULL,'成功',44,'新增权限','2019-04-12 12:45:21'),(25,'192.168.0.103',1,'admin','{\"authName\":\"会员管理\",\"authCode\":\"customer\",\"authType\":\"menu\"}','auth','add',NULL,'成功',47,'新增权限','2019-04-12 12:47:07'),(26,'192.168.0.103',1,'admin','{\"authName\":\"会员信息\",\"authCode\":\"customer\",\"authType\":\"menu\",\"parentId\":\"29\"}','auth','add',NULL,'成功',49,'新增权限','2019-04-12 12:49:54'),(27,'192.168.0.103',1,'admin','{\"authName\":\"查看会员详情\",\"authCode\":\"customer.detail\",\"authType\":\"oper\",\"parentId\":\"30\"}','auth','add',NULL,'成功',45,'新增权限','2019-04-12 12:50:44'),(28,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',0,'退出','2019-04-12 12:51:21'),(29,'192.168.0.103',1,'admin','{\"userName\":\"qiDSGGk/4b0=\"}','shrio','login',NULL,'成功',9,'登录','2019-04-12 12:51:29'),(30,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15,23,24,25,26,27,28,29,30,31],\"roleId\":1}','role','authorize','删除权限[],新增权限[23,24,25,26,27,28,29,30,31]','成功',315,'权限分配','2019-04-12 12:52:35'),(31,'192.168.0.103',1,'admin','{\"realName\":\"刘小凯\",\"userName\":\"刘小凯\",\"phone\":\"15023184811\",\"nickName\":\"刘小凯\"}','user','add',NULL,'成功',59,'新增用户','2019-04-12 12:57:36'),(32,'192.168.0.103',1,'admin','{\"roleList\":[1],\"userId\":2}','user','authorize','新增角色[1]','成功',56,'角色分配','2019-04-12 12:57:43'),(33,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',1,'退出','2019-04-12 12:57:55'),(34,'192.168.0.103',2,'刘小凯','{\"userName\":\"zxTy26DQ4X3mdMKh+QhfVw==\"}','shrio','login',NULL,'成功',9,'登录','2019-04-12 12:58:12'),(35,'192.168.0.103',2,'刘小凯','{\"imageUrl\":\"http://127.0.0.1:8082/files/image/7/a/07d6123f4fb74ac7a3f4ae389aec4e11_20190412205545.jpg\",\"id\":\"2\"}','user','update','上传用户头像:【http://127.0.0.1:8082/files/image/7/a/07d6123f4fb74ac7a3f4ae389aec4e11_20190412205545.jpg】','成功',43,'个人设置','2019-04-12 13:02:59'),(36,'192.168.0.103',1,'admin','{\"userName\":\"2Q/s0tZnwmU=\"}','shrio','login',NULL,'成功',321,'登录','2019-04-12 13:10:39'),(37,'192.168.0.103',1,'admin','{\"id\":1}','carousel','up','上架轮播图[衣裤]','成功',41,'上架轮播图','2019-04-12 13:13:49'),(38,'192.168.0.103',1,'admin','{\"propertyValue\":\"wx.appid\",\"propertyName\":\"wx.appid\",\"propertyDesc\":\"小程序appid\"}','config','add',NULL,'成功',42,'新增系统参数','2019-04-12 13:16:43'),(39,'192.168.0.103',1,'admin','{\"propertyValue\":\"wx.secret\",\"propertyName\":\"wx.secret\",\"propertyDesc\":\"小程序APPID对应的secret\"}','config','add',NULL,'成功',61,'新增系统参数','2019-04-12 13:17:22'),(40,'192.168.0.103',1,'admin','{\"userName\":\"RpJFggsAKjE=\"}','shrio','login',NULL,'成功',25,'登录','2019-04-12 15:26:33'),(41,'192.168.0.103',1,'admin','{\"userName\":\"3kGNZIKki70=\"}','shrio','login',NULL,'成功',329,'登录','2019-04-12 17:27:04'),(42,'192.168.0.103',1,'admin','{\"userName\":\"XlkF5PdCIcU=\"}','shrio','login',NULL,'成功',326,'登录','2019-04-12 17:28:29'),(43,'192.168.0.103',1,'admin','{\"userName\":\"3CIxW5qZIXM=\"}','shrio','login',NULL,'成功',316,'登录','2019-04-12 17:45:15'),(44,'192.168.0.103',1,'admin','{\"userName\":\"UHhyCUUyIgE=\"}','shrio','login',NULL,'成功',327,'登录','2019-04-12 17:49:24'),(45,'192.168.0.103',1,'admin','{\"userName\":\"UwlqAySfEYg=\"}','shrio','login',NULL,'成功',323,'登录','2019-04-12 17:51:30'),(46,'192.168.0.103',1,'admin','{\"userName\":\"q/nu7iU4BxU=\"}','shrio','login',NULL,'成功',334,'登录','2019-04-12 17:53:22'),(47,'192.168.0.103',1,'admin','{\"authName\":\"会员卡\",\"authCode\":\"card\",\"authType\":\"menu\",\"parentId\":\"29\"}','auth','add',NULL,'成功',59,'新增权限','2019-04-12 18:10:21'),(48,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',2,'退出','2019-04-12 18:10:28'),(49,'192.168.0.103',1,'admin','{\"userName\":\"lITNeTMhclo=\"}','shrio','login',NULL,'成功',10,'登录','2019-04-12 18:10:37'),(50,'192.168.0.103',1,'admin','{\"userName\":\"ieulV13nOM4=\"}','shrio','login',NULL,'成功',343,'登录','2019-04-12 18:11:15'),(51,'192.168.0.103',1,'admin','{\"userName\":\"U6MfNByiUT8=\"}','shrio','login',NULL,'成功',318,'登录','2019-04-12 19:10:18'),(52,'192.168.0.103',1,'admin','{\"name\":\"重庆公共交通乘车卡\"}','card','add',NULL,'成功',56,'新增会员卡','2019-04-12 19:10:45'),(53,'192.168.0.103',1,'admin','{\"name\":\"重庆公共交通乘车卡\",\"id\":1}','card','update',NULL,'成功',56,'修改会员卡','2019-04-12 19:14:44'),(54,'192.168.0.103',1,'admin','{\"userName\":\"XlPaIx8oesA=\"}','shrio','login',NULL,'成功',338,'登录','2019-04-12 20:01:50'),(55,'192.168.0.103',1,'admin','{\"name\":\"白银卡\",\"level\":\"silver\",\"points\":\"0\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/9/e/bea635bdfb91471d9257e221397cf8d7_1.jpg\"}','level','add',NULL,'失败:会员卡级别最低积分只能输入数字，请重新输入',0,'新增会员卡级别','2019-04-12 20:03:46'),(56,'192.168.0.103',1,'admin','{\"name\":\"白银卡\",\"level\":\"silver\",\"points\":\"0\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/9/e/bea635bdfb91471d9257e221397cf8d7_1.jpg\"}','level','add',NULL,'失败:会员卡级别最低积分只能输入数字，请重新输入',0,'新增会员卡级别','2019-04-12 20:03:54'),(57,'192.168.0.103',1,'admin','{\"userName\":\"JtDlXc0kKhM=\"}','shrio','login',NULL,'成功',343,'登录','2019-04-12 20:05:01'),(58,'192.168.0.103',1,'admin','{\"name\":\"白银卡\",\"level\":\"silver\",\"points\":\"0\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/7/7/cf8cbdb60080420499f24523308a7458_1.jpg\"}','level','add',NULL,'失败:请选择会员卡',0,'新增会员卡级别','2019-04-12 20:05:30'),(59,'192.168.0.103',1,'admin','{\"name\":\"白银卡\",\"level\":\"silver\",\"points\":\"0\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/3/c/0e66c9799f134aa69df5dc0cecc857fe_1.jpg\",\"cardId\":1}','level','add',NULL,'成功',73,'新增会员卡级别','2019-04-12 20:06:36'),(60,'192.168.0.103',1,'admin','{\"name\":\"金卡\",\"level\":\"gold\",\"points\":\"400\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/8/a/2cbabc34c1cf48698b36bcddc965ca76_22.jpg\",\"cardId\":1}','level','add',NULL,'成功',92,'新增会员卡级别','2019-04-12 20:07:39'),(61,'192.168.0.103',1,'admin','{\"name\":\"钻石卡\",\"level\":\"diamond\",\"points\":\"1000\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/5/4/d0ef2e10aca749b8b65628c03a08b0d5_35.jpg\",\"cardId\":1}','level','add',NULL,'成功',47,'新增会员卡级别','2019-04-12 20:08:28'),(62,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15,23,24,25,26,27,28,29,30,31,32],\"roleId\":1}','role','authorize','删除权限[],新增权限[32]','成功',52,'权限分配','2019-04-12 20:12:00'),(63,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',3,'退出','2019-04-12 20:12:05'),(64,'192.168.0.103',2,'刘小凯','{\"userName\":\"MPsSaV2QpX7VZ6LiwUjvQw==\"}','shrio','login',NULL,'成功',10,'登录','2019-04-12 20:12:17'),(65,'192.168.0.103',2,'刘小凯',NULL,'shrio','logout',NULL,'成功',1,'退出','2019-04-12 20:12:43'),(66,'192.168.0.103',1,'admin','{\"userName\":\"qJPKEiT58PU=\"}','shrio','login',NULL,'成功',1999,'登录','2019-04-13 04:20:23'),(67,'192.168.0.103',1,'admin','{\"propertyValue\":\"wx25fec6dbca9b236b\",\"id\":\"1\",\"propertyName\":\"wx.appid\",\"propertyDesc\":\"小程序appid\"}','config','update',NULL,'成功',652,'修改系统参数','2019-04-13 04:20:50'),(68,'192.168.0.103',1,'admin','{\"level\":\"gold\",\"cardId\":1,\"imageUrl\":\"http://127.0.0.1:8082/files/image/e/b/e21ca91f35cc4380b61bf410b64bde63_14.jpg\",\"name\":\"金卡\",\"id\":2,\"points\":400}','level','update',NULL,'成功',72,'修改会员卡级别','2019-04-13 06:07:39'),(69,'192.168.0.103',1,'admin','{\"level\":\"diamond\",\"cardId\":1,\"imageUrl\":\"http://127.0.0.1:8082/files/image/8/c/e1b1cbe25440477082cdc9fb4c083663_2.jpg\",\"name\":\"钻石卡\",\"id\":3,\"points\":1000}','level','update',NULL,'成功',53,'修改会员卡级别','2019-04-13 06:07:53'),(70,'192.168.0.103',1,'admin','{\"userName\":\"VpR1uR6/nZI=\"}','shrio','login',NULL,'成功',16,'登录','2019-04-13 07:07:38'),(71,'192.168.0.103',1,'admin','{\"userName\":\"8c0QNSWblTo=\"}','shrio','login',NULL,'成功',354,'登录','2019-04-13 07:39:20'),(72,'192.168.0.103',1,'admin','{\"param0\":3}','level','delete',NULL,'成功',64,'删除会员卡级别','2019-04-13 08:04:33'),(73,'192.168.0.103',1,'admin','{\"authName\":\"地区管理\",\"authCode\":\"system.location\",\"authType\":\"menu\"}','auth','add',NULL,'成功',50,'新增权限','2019-04-13 08:20:17'),(74,'192.168.0.103',1,'admin','{\"authName\":\"查看地区详情\",\"authCode\":\"system.location.detail\",\"authType\":\"oper\",\"parentId\":\"33\"}','auth','add',NULL,'成功',35,'新增权限','2019-04-13 08:20:59'),(75,'192.168.0.103',1,'admin','{\"authName\":\"新增地区\",\"authCode\":\"system.location.add\",\"authType\":\"oper\",\"parentId\":\"33\"}','auth','add',NULL,'成功',60,'新增权限','2019-04-13 08:21:20'),(76,'192.168.0.103',1,'admin','{\"authName\":\"修改地区\",\"authCode\":\"system.location.update\",\"authType\":\"oper\",\"parentId\":\"33\"}','auth','add',NULL,'成功',46,'新增权限','2019-04-13 08:21:41'),(77,'192.168.0.103',1,'admin','{\"authName\":\"删除地区\",\"authCode\":\"system.location.delete\",\"authType\":\"oper\",\"parentId\":\"33\"}','auth','add',NULL,'成功',41,'新增权限','2019-04-13 08:22:01'),(78,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',3,'退出','2019-04-13 08:22:05'),(79,'192.168.0.103',1,'admin','{\"userName\":\"CFO+ZozDB8Q=\"}','shrio','login',NULL,'成功',364,'登录','2019-04-13 08:23:09'),(80,'192.168.0.103',1,'admin','{\"locationCode\":\"重庆市\",\"locationName\":\"重庆市\"}','location','add',NULL,'成功',56,'新增地区','2019-04-13 08:25:29'),(81,'192.168.0.103',1,'admin','{\"locationCode\":\"四川省\",\"locationName\":\"四川省\"}','location','add',NULL,'成功',194,'新增地区','2019-04-13 08:25:49'),(82,'192.168.0.103',1,'admin','{\"locationName\":\"成都市\",\"locationCode\":\"成都市\",\"parentId\":\"2\"}','location','add',NULL,'成功',52,'新增地区','2019-04-13 08:26:05'),(83,'192.168.0.103',1,'admin','{\"locationCode\":\"浙江省\",\"locationName\":\"浙江省\"}','location','add',NULL,'成功',67,'新增地区','2019-04-13 08:26:20'),(84,'192.168.0.103',1,'admin','{\"locationCode\":\"北京市\",\"locationName\":\"北京市\"}','location','add',NULL,'成功',43,'新增地区','2019-04-13 08:26:39'),(85,'192.168.0.103',1,'admin','{\"locationName\":\"海淀区\",\"locationCode\":\"海淀区\",\"parentId\":\"5\"}','location','add',NULL,'成功',81,'新增地区','2019-04-13 08:26:51'),(86,'192.168.0.103',1,'admin','{\"locationName\":\"云阳县\",\"locationCode\":\"云阳县\",\"parentId\":\"1\"}','location','add',NULL,'成功',51,'新增地区','2019-04-13 08:27:07'),(87,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37],\"roleId\":1}','role','authorize','删除权限[],新增权限[33,34,35,36,37]','成功',186,'权限分配','2019-04-13 08:27:57'),(88,'192.168.0.103',1,'admin','{\"userName\":\"j8OP0ZeAuCU=\"}','shrio','login',NULL,'成功',343,'登录','2019-04-13 10:38:15'),(89,'192.168.0.103',1,'admin','{\"name\":\"钻石卡\",\"level\":\"damond\",\"points\":\"1000\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/c/c/294de5e161cd4f8f9ff6e559649cd0d4_4.jpg\",\"cardId\":1}','level','add',NULL,'成功',90,'新增会员卡级别','2019-04-13 10:39:07'),(90,'192.168.0.103',1,'admin','{\"param0\":4}','level','delete',NULL,'成功',60,'删除会员卡级别','2019-04-13 10:41:42'),(91,'192.168.0.103',1,'admin','{\"name\":\"钻石卡\",\"level\":\"damond\",\"points\":\"1000\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/c/f/1ce1cb6e4e134c9bb67a9a05e4813a97_2.jpg\",\"cardId\":1}','level','add',NULL,'成功',81,'新增会员卡级别','2019-04-13 10:42:20'),(92,'192.168.0.103',1,'admin','{\"userName\":\"6w/LgGL9axE=\"}','shrio','login',NULL,'成功',320,'登录','2019-04-13 11:04:42'),(93,'192.168.0.103',1,'admin','{\"userName\":\"jARCUbbXRnE=\"}','shrio','login',NULL,'成功',27,'登录','2019-04-13 13:08:12'),(94,'192.168.0.103',1,'admin','{\"userName\":\"hl07p05xU4U=\"}','shrio','login',NULL,'成功',337,'登录','2019-04-13 14:27:03'),(95,'192.168.0.103',1,'admin','{\"authName\":\"门店管理\",\"authCode\":\"shop\",\"authType\":\"menu\"}','auth','add',NULL,'成功',46,'新增权限','2019-04-13 14:27:26'),(96,'192.168.0.103',1,'admin','{\"authName\":\"新增门店\",\"authCode\":\"shop.add\",\"authType\":\"oper\",\"parentId\":\"38\"}','auth','add',NULL,'成功',49,'新增权限','2019-04-13 14:30:20'),(97,'192.168.0.103',1,'admin','{\"authName\":\"查看门店详情\",\"authCode\":\"shop.detail\",\"authType\":\"oper\",\"parentId\":\"38\"}','auth','add',NULL,'成功',58,'新增权限','2019-04-13 14:30:46'),(98,'192.168.0.103',1,'admin','{\"authName\":\"生成积分码\",\"authCode\":\"shop.acode\",\"authType\":\"oper\",\"parentId\":\"38\"}','auth','add',NULL,'成功',40,'新增权限','2019-04-13 14:31:06'),(99,'192.168.0.103',1,'admin','{\"authName\":\"修改门店\",\"authCode\":\"shop.update\",\"authType\":\"oper\",\"parentId\":\"38\"}','auth','add',NULL,'成功',42,'新增权限','2019-04-13 14:31:27'),(100,'192.168.0.103',1,'admin','{\"authName\":\"删除门店\",\"authCode\":\"shop.delete\",\"authType\":\"oper\",\"parentId\":\"38\"}','auth','add',NULL,'成功',52,'新增权限','2019-04-13 14:31:47'),(101,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43],\"roleId\":1}','role','authorize','删除权限[],新增权限[38,39,40,41,42,43]','成功',253,'权限分配','2019-04-13 14:32:03'),(102,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',3,'退出','2019-04-13 14:32:08'),(103,'192.168.0.103',1,'admin','{\"userName\":\"/2QsLQHkX4k=\"}','shrio','login',NULL,'成功',12,'登录','2019-04-13 14:32:16'),(104,'192.168.0.103',1,'admin','{\"userName\":\"72lgr/pxbzc=\"}','shrio','login',NULL,'成功',313,'登录','2019-04-13 15:34:33'),(105,'192.168.0.103',1,'admin','{\"ll\":\"29.590836,106.510446\",\"code\":\"lhbkjy\",\"address\":\"龙华大道48号\",\"locationId\":1,\"latitude\":\"29.590836\",\"name\":\"龙湖别克君越店\",\"radius\":\"10\",\"longitude\":\"106.510446\"}','shop','add',NULL,'成功',99,'新增门店','2019-04-13 16:02:29'),(106,'192.168.0.103',1,'admin','{\"userName\":\"6GLiXFolcY0=\"}','shrio','login',NULL,'成功',334,'登录','2019-04-13 16:20:07'),(107,'192.168.0.103',1,'admin','{\"userName\":\"Z9C4ifJyjLE=\"}','shrio','login',NULL,'成功',352,'登录','2019-04-13 16:22:20'),(108,'192.168.0.103',1,'admin','{\"ll\":\"29.590836,106.510446\",\"address\":\"重庆市龙华大道48号\",\"code\":\"lhbkjy\",\"locationId\":1,\"latitude\":\"29.590836\",\"name\":\"龙湖别克君越店\",\"id\":1,\"radius\":10,\"longitude\":\"106.510446\"}','shop','update',NULL,'成功',86,'修改门店','2019-04-13 16:22:39'),(109,'192.168.0.103',1,'admin','{\"ll\":\"29.590836,106.510446\",\"address\":\"重庆市龙华大道48号\",\"code\":\"lhbkjy\",\"locationId\":1,\"latitude\":\"29.590836\",\"name\":\"龙湖别克君越店\",\"id\":1,\"radius\":10,\"longitude\":\"106.510446\"}','shop','update',NULL,'成功',141,'修改门店','2019-04-13 16:24:51'),(110,'192.168.0.103',1,'admin','{\"userName\":\"2NArddGEHnw=\"}','shrio','login',NULL,'成功',333,'登录','2019-04-13 16:29:35'),(111,'192.168.0.103',1,'admin','{\"ll\":\"29.590836,106.510446\",\"address\":\"重庆市龙华大道48号\",\"code\":\"lhbkjy\",\"locationId\":1,\"latitude\":\"29.590836\",\"name\":\"龙湖别克君越店\",\"id\":1,\"radius\":10,\"longitude\":\"106.510446\"}','shop','update',NULL,'成功',84,'修改门店','2019-04-13 16:29:54'),(112,'192.168.0.103',1,'admin','{\"userName\":\"eCHUopy9czw=\"}','shrio','login',NULL,'成功',317,'登录','2019-04-13 16:38:22'),(113,'192.168.0.103',1,'admin','{\"userName\":\"Om/LaxK9wJA=\"}','shrio','login',NULL,'成功',317,'登录','2019-04-13 16:57:19'),(114,'192.168.0.103',1,'admin','{\"propertyValue\":\"pages/index/index\",\"propertyName\":\"wx.acode.page\",\"propertyDesc\":\"小程序积分码跳转页面\"}','config','add',NULL,'成功',52,'新增系统参数','2019-04-13 16:58:44'),(115,'192.168.0.103',1,'admin','{\"userName\":\"Sl3wC09oOGY=\"}','shrio','login',NULL,'成功',70,'登录','2019-04-13 17:48:53'),(116,'192.168.0.103',1,'admin','{\"param0\":1}','shop','delete',NULL,'成功',66,'删除门店','2019-04-13 18:19:32'),(117,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',3,'退出','2019-04-13 18:25:40'),(118,'192.168.0.103',1,'admin','{\"userName\":\"73OD+dHvHsc=\"}','shrio','login',NULL,'成功',351,'登录','2019-04-14 07:57:25'),(119,'192.168.0.103',1,'admin','{\"authName\":\"经销商\",\"authCode\":\"franchiser\",\"authType\":\"menu\"}','auth','add',NULL,'成功',184,'新增权限','2019-04-14 07:57:50'),(120,'192.168.0.103',1,'admin','{\"authName\":\"新增经销商\",\"authCode\":\"franchiser.add\",\"authType\":\"oper\",\"parentId\":\"44\"}','auth','add',NULL,'成功',51,'新增权限','2019-04-14 07:58:29'),(121,'192.168.0.103',1,'admin','{\"authName\":\"查看经销商详情\",\"authCode\":\"franchiser.detail\",\"authType\":\"oper\",\"parentId\":\"44\"}','auth','add',NULL,'成功',52,'新增权限','2019-04-14 07:59:03'),(122,'192.168.0.103',1,'admin','{\"authName\":\"修改经销商\",\"authCode\":\"franchiser.update\",\"authType\":\"oper\",\"parentId\":\"44\"}','auth','add',NULL,'成功',47,'新增权限','2019-04-14 07:59:26'),(123,'192.168.0.103',1,'admin','{\"authName\":\"删除经销商\",\"authCode\":\"franchiser.delete\",\"authType\":\"oper\",\"parentId\":\"44\"}','auth','add',NULL,'成功',59,'新增权限','2019-04-14 07:59:42'),(124,'192.168.0.103',1,'admin','{\"userName\":\"EMUXPYjrRi4=\"}','shrio','login',NULL,'成功',311,'登录','2019-04-14 08:30:00'),(125,'192.168.0.103',1,'admin','{\"userName\":\"rq47QRQ8zIM=\"}','shrio','login',NULL,'成功',309,'登录','2019-04-14 08:31:23'),(126,'192.168.0.103',1,'admin','{\"name\":\"长征长宝宝马4s店\",\"address\":\"成华区东三环二段龙潭工业园航天路2号\",\"locationId\":3}','franchiser','add',NULL,'成功',93,'新增经销商','2019-04-14 08:38:31'),(127,'192.168.0.103',1,'admin','{\"locationName\":\"成华区\",\"locationCode\":\"成华区\",\"parentId\":\"3\"}','location','add',NULL,'成功',53,'新增地区','2019-04-14 08:39:49'),(128,'192.168.0.103',1,'admin','{\"locationName\":\"四川省成都市\",\"address\":\"东三环二段龙潭工业园航天路2号\",\"locationId\":8,\"locationArray\":[2,3],\"name\":\"长征长宝宝马4s店\",\"id\":1}','franchiser','update',NULL,'成功',54,'修改经销商','2019-04-14 08:41:06'),(129,'192.168.0.103',1,'admin','{\"userName\":\"lLsHbxxt0JM=\"}','shrio','login',NULL,'成功',19,'登录','2019-04-14 09:30:33'),(130,'192.168.0.103',1,'admin','{\"authName\":\"汽车顾问\",\"authCode\":\"consultant\",\"authType\":\"menu\"}','auth','add',NULL,'成功',42,'新增权限','2019-04-14 09:31:11'),(131,'192.168.0.103',1,'admin','{\"authName\":\"新增汽车顾问\",\"authCode\":\"consultant.add\",\"authType\":\"oper\",\"parentId\":\"49\"}','auth','add',NULL,'成功',50,'新增权限','2019-04-14 09:33:18'),(132,'192.168.0.103',1,'admin','{\"authName\":\"修改汽车顾问\",\"authCode\":\"consultant.update\",\"authType\":\"oper\",\"parentId\":\"49\"}','auth','add',NULL,'成功',43,'新增权限','2019-04-14 09:33:47'),(133,'192.168.0.103',1,'admin','{\"authName\":\"删除汽车顾问\",\"authCode\":\"consultant.delete\",\"authType\":\"oper\",\"parentId\":\"49\"}','auth','add',NULL,'成功',58,'新增权限','2019-04-14 09:34:05'),(134,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',1,'退出','2019-04-14 09:34:31'),(135,'192.168.0.103',1,'admin','{\"userName\":\"mv+nPSVs6Yo=\"}','shrio','login',NULL,'成功',11,'登录','2019-04-14 09:34:40'),(136,'192.168.0.103',1,'admin','{\"userName\":\"z0lthSwQ5e8=\"}','shrio','login',NULL,'成功',352,'登录','2019-04-14 09:37:54'),(137,'192.168.0.103',1,'admin','{\"name\":\"袁文树\",\"phone\":\"沙发\"}','consultant','add',NULL,'失败:请输入11位有效手机号码',19,'新增汽车顾问','2019-04-14 09:38:28'),(138,'192.168.0.103',1,'admin','{\"name\":\"袁文树\",\"phone\":\"150869201531\"}','consultant','add',NULL,'失败:请输入11位有效手机号码',0,'新增汽车顾问','2019-04-14 09:38:38'),(139,'192.168.0.103',1,'admin','{\"name\":\"袁文树\",\"phone\":\"15086920153\"}','consultant','add',NULL,'失败:请上传汽车顾问头像',0,'新增汽车顾问','2019-04-14 09:38:43'),(140,'192.168.0.103',1,'admin','{\"name\":\"袁文树\",\"phone\":\"15086920153\"}','consultant','add',NULL,'失败:请上传汽车顾问头像',1,'新增汽车顾问','2019-04-14 09:41:18'),(141,'192.168.0.103',1,'admin','{\"name\":\"姓名\",\"phone\":\"15086920153\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/9/8/e8867abe865c4054b5f643413ca00572_2.jpg\"}','consultant','add',NULL,'成功',62,'新增汽车顾问','2019-04-14 09:49:29'),(142,'192.168.0.103',1,'admin','{\"userName\":\"du+EbCkAkHw=\"}','shrio','login',NULL,'成功',317,'登录','2019-04-14 09:52:18'),(143,'192.168.0.103',1,'admin','{\"name\":\"姓名\",\"id\":1,\"phone\":\"15086920153\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/3/8/ef109ef6c0bc4070897bb27532803a4b_22.jpeg\"}','consultant','update',NULL,'成功',99,'修改汽车顾问','2019-04-14 09:52:33'),(144,'192.168.0.103',1,'admin','{\"name\":\"袁文树\",\"id\":1,\"phone\":\"15086920153\",\"imageUrl\":\"http://127.0.0.1:8082/files/image/3/8/ef109ef6c0bc4070897bb27532803a4b_22.jpeg\"}','consultant','update',NULL,'成功',52,'修改汽车顾问','2019-04-14 09:53:15'),(145,'192.168.0.103',1,'admin','{\"id\":1}','consultant','delete',NULL,'成功',77,'删除汽车顾问','2019-04-14 09:54:21'),(146,'192.168.0.103',1,'admin','{\"userName\":\"JOI6H3myid0=\"}','shrio','login',NULL,'成功',307,'登录','2019-04-14 11:25:02'),(147,'192.168.0.103',1,'admin','{\"authName\":\"预约看车\",\"authCode\":\"bespeak\",\"authType\":\"menu\",\"parentId\":\"29\"}','auth','add',NULL,'成功',52,'新增权限','2019-04-14 11:26:03'),(148,'192.168.0.103',1,'admin','{\"authName\":\"查看预约详情\",\"authCode\":\"bespeak.detail\",\"authType\":\"oper\",\"parentId\":\"53\"}','auth','add',NULL,'成功',41,'新增权限','2019-04-14 11:26:33'),(149,'192.168.0.103',1,'admin','{\"authList\":[1,2,3,4,14,16,5,6,7,8,15,23,24,25,26,27,28,29,30,31,32,53,54,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52],\"roleId\":1}','role','authorize','删除权限[],新增权限[53,54,44,45,46,47,48,49,50,51,52]','成功',448,'权限分配','2019-04-14 11:27:02'),(150,'192.168.0.103',1,'admin',NULL,'shrio','logout',NULL,'成功',2,'退出','2019-04-14 11:27:09'),(151,'192.168.0.103',1,'admin','{\"userName\":\"ihRwaujTkJ4=\"}','shrio','login',NULL,'成功',13,'登录','2019-04-14 11:27:17');
/*!40000 ALTER TABLE `t_system_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_role`
--

DROP TABLE IF EXISTS `t_system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_role`
--

LOCK TABLES `t_system_role` WRITE;
/*!40000 ALTER TABLE `t_system_role` DISABLE KEYS */;
INSERT INTO `t_system_role` VALUES (1,'系统管理员','2019-04-12 12:34:07');
/*!40000 ALTER TABLE `t_system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_role_auth`
--

DROP TABLE IF EXISTS `t_system_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `authId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_role_auth`
--

LOCK TABLES `t_system_role_auth` WRITE;
/*!40000 ALTER TABLE `t_system_role_auth` DISABLE KEYS */;
INSERT INTO `t_system_role_auth` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,14),(6,1,16),(7,1,5),(8,1,6),(9,1,7),(10,1,8),(11,1,15),(12,1,23),(13,1,24),(14,1,25),(15,1,26),(16,1,27),(17,1,28),(18,1,29),(19,1,30),(20,1,31),(21,1,32),(22,1,33),(23,1,34),(24,1,35),(25,1,36),(26,1,37),(27,1,38),(28,1,39),(29,1,40),(30,1,41),(31,1,42),(32,1,43),(33,1,53),(34,1,54),(35,1,44),(36,1,45),(37,1,46),(38,1,47),(39,1,48),(40,1,49),(41,1,50),(42,1,51),(43,1,52);
/*!40000 ALTER TABLE `t_system_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_user`
--

DROP TABLE IF EXISTS `t_system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `nickName` varchar(64) DEFAULT NULL,
  `realName` varchar(32) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `openId` varchar(128) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `orgId` int(11) DEFAULT NULL,
  `imageUrl` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_user`
--

LOCK TABLES `t_system_user` WRITE;
/*!40000 ALTER TABLE `t_system_user` DISABLE KEYS */;
INSERT INTO `t_system_user` VALUES (1,'admin','3d1463e279bd9c5277df283bcc2aa545','超级管理员','袁文树','tJbpS',NULL,'15086920153','2018-04-13 13:44:01',NULL,NULL),(2,'刘小凯','0415d2dcb45d71a22590f2a8cbf60ee6','刘小凯','刘小凯','ABKM4',NULL,'15023184811','2019-04-12 12:57:36',NULL,'http://127.0.0.1:8082/files/image/7/a/07d6123f4fb74ac7a3f4ae389aec4e11_20190412205545.jpg');
/*!40000 ALTER TABLE `t_system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_user_role`
--

DROP TABLE IF EXISTS `t_system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_user_role`
--

LOCK TABLES `t_system_user_role` WRITE;
/*!40000 ALTER TABLE `t_system_user_role` DISABLE KEYS */;
INSERT INTO `t_system_user_role` VALUES (1,1,1),(2,1,2);
/*!40000 ALTER TABLE `t_system_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-14 19:39:28
