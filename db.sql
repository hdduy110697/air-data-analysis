-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.31-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for warehouse
CREATE DATABASE IF NOT EXISTS `warehouse` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `warehouse`;

-- Dumping structure for table warehouse.bus_customer
CREATE TABLE IF NOT EXISTS `bus_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `connectionpersion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.bus_customer: ~5 rows (approximately)
/*!40000 ALTER TABLE `bus_customer` DISABLE KEYS */;
INSERT IGNORE INTO `bus_customer` (`id`, `customername`, `zip`, `address`, `telephone`, `connectionpersion`, `phone`, `bank`, `account`, `email`, `fax`, `available`) VALUES
	(1, 'Nguyen Van A', '111', 'Da Nang', '0279123131', 'A', '15279230588', 'VCB', '654431331343413', '213123@sina.com', '430000', 0),
	(2, 'Supermarket 1', '222', 'Da Nang', '0755-9123131', 'A', '13064154936', 'VCB', '654431331343413', '213123@sina.com', '430000', 1),
	(3, 'Supermarket 2', '430000', 'Da Nang', '027-11011011', 'B', '13617020687', 'VCB', '6543123341334133', '6666@66.com', '545341', 1),
	(4, 'Nguyen Van A', '332005', 'Da Nang', '079213658745', 'C', '13648524759', 'VCB', '36245684125509', '13648524759@163.com', '152632', 1),
	(8, 'Test 001', '332005', 'Da Nang', '079256214528', 'D', '15236987455', 'VCB', '362458651236509', '15236987455@163.com', '320156', 1);
/*!40000 ALTER TABLE `bus_customer` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_goods
CREATE TABLE IF NOT EXISTS `bus_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `produceplace` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `goodspackage` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productcode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `promitcode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `dangernum` int(11) DEFAULT NULL,
  `goodsimg` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `bus_goods_ibfk_1` (`providerid`) USING BTREE,
  CONSTRAINT `bus_goods_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='InnoDB free: 9216 kB; (`providerid`) REFER `warehouse/bus_provider`(`id`)';

-- Dumping data for table warehouse.bus_goods: ~5 rows (approximately)
/*!40000 ALTER TABLE `bus_goods` DISABLE KEYS */;
INSERT IGNORE INTO `bus_goods` (`id`, `goodsname`, `produceplace`, `size`, `goodspackage`, `productcode`, `promitcode`, `description`, `price`, `number`, `dangernum`, `goodsimg`, `available`, `providerid`) VALUES
	(1, 'Wahaha', 'DNB', '120ML', 'bottle', 'PH12345', 'PZ1234', 'Children love', 2, 488, 10, '2018-12-25/userface1.jpg', 1, 3),
	(2, 'Snow Cake [Small Packet]', 'DNC', 'package', 'bag', 'PH12312312', 'PZ12312', 'Not delicious', 4, 1200, 10, '2018-12-25/userface2.jpg', 1, 1),
	(3, 'Gift Pack', 'DNC', 'box', 'box', '11', '11', '111', 28, 1021, 100, '2018-12-25/userface3.jpg', 1, 1),
	(4, 'Wahaha', 'DNA', '200ML', 'bottle', '11', '111', '12321', 3, 1100, 10, '2018-12-25/userface4.jpg', 1, 3),
	(5, 'Wahaha', 'DNA', '300ML', 'bottle', '1234', '12321', '22221111', 3, 1000, 100, '2018-12-25/userface5.jpg', 1, 3);
/*!40000 ALTER TABLE `bus_goods` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_inport
CREATE TABLE IF NOT EXISTS `bus_inport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `inporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `inportprice` double DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `bus_inport_ibfk_1` (`providerid`) USING BTREE,
  KEY `bus_inport_ibfk_2` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`),
  CONSTRAINT `bus_inport_ibfk_2` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='InnoDB free: 9216 kB; (`providerid`) REFER `warehouse/bus_provider`(`id`); (`goo';

-- Dumping data for table warehouse.bus_inport: ~11 rows (approximately)
/*!40000 ALTER TABLE `bus_inport` DISABLE KEYS */;
INSERT IGNORE INTO `bus_inport` (`id`, `paytype`, `inporttime`, `operateperson`, `number`, `remark`, `inportprice`, `providerid`, `goodsid`) VALUES
	(1, 'WeChat', '2018-05-07 00:00:00', 'Ope3 ', 100, '', 3.5, 1, 1),
	(2, 'Alipay', '2018-05-07 00:00:00', 'Ope3 ', 1000, '', 2.5, 3, 3),
	(3, 'UnionPay', '2018-05-07 00:00:00', 'Ope3 ', 100, '1231', 111, 3, 3),
	(4, 'UnionPay', '2018-05-07 00:00:00', 'Ope3 ', 1000, '', 2, 3, 1),
	(5, 'UnionPay', '2018-05-07 00:00:00', 'Ope3 ', 100, '', 1, 3, 1),
	(6, 'UnionPay', '2018-05-07 00:00:00', 'Ope3 ', 100, '1231', 2.5, 1, 2),
	(8, 'Alipay', '2018-05-07 00:00:00', 'Ope3 ', 100, '', 1, 3, 1),
	(10, 'Alipay', '2018-08-07 17:17:57', 'Super administrator', 100, '', 1.5, 3, 1),
	(11, 'Alipay', '2018-09-17 16:12:25', 'Super administrator', 21, '21', 21, 1, 3),
	(12, 'WeChat', '2018-12-25 16:48:24', 'Super administrator', 100, '123213213', 12321321, 3, 1),
	(13, 'Alipay', '2020-03-07 04:23:05', 'Super administrator', 100, 'Ly do mua la gi', 100000, 1, 2);
/*!40000 ALTER TABLE `bus_inport` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_outport
CREATE TABLE IF NOT EXISTS `bus_outport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outputtime` datetime DEFAULT NULL,
  `operateperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outportprice` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.bus_outport: ~2 rows (approximately)
/*!40000 ALTER TABLE `bus_outport` DISABLE KEYS */;
INSERT IGNORE INTO `bus_outport` (`id`, `providerid`, `paytype`, `outputtime`, `operateperson`, `outportprice`, `number`, `remark`, `goodsid`) VALUES
	(1, 3, 'WeChat', '2019-08-16 08:19:50', 'Super administrator', 12321321, 1, '', 1),
	(2, 3, 'WeChat', '2019-08-16 08:26:54', 'Super administrator', 12321321, 11, '11', 1);
/*!40000 ALTER TABLE `bus_outport` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_provider
CREATE TABLE IF NOT EXISTS `bus_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `connectionperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.bus_provider: ~6 rows (approximately)
/*!40000 ALTER TABLE `bus_provider` DISABLE KEYS */;
INSERT IGNORE INTO `bus_provider` (`id`, `providername`, `zip`, `address`, `telephone`, `connectionperson`, `phone`, `bank`, `account`, `email`, `fax`, `available`) VALUES
	(1, 'Want Want Food', '434000', 'Da Nang', '0728-4124312', 'Tieu Minh', '13413441141', 'Vietcombank', '654124345131', '12312321@sina.com', '5123123', 1),
	(2, 'Garden Food', '430000', 'Ha Noi', '0728-4124312', 'Dai Minh', '13413441141', 'Vietcombank', '654124345131', '12312321@sina.com', '5123123', 1),
	(3, '\r\nWahaha Group', '513131', 'Ho Chi Minh', '21312', 'Tieu X', '12312', 'Vietcombank', '512314141541', '123131', '312312', 1),
	(4, 'Mengniu Group', '332005', 'Hue', '0790-362514856', 'Rita', '13617252689', 'Vietcombank', '36214587962509', '13617252689@163.com', '364145', 1),
	(5, 'Yili Group', '562115', 'Quang Ninh', '0792-36548569', 'Rita', '13698560566', 'Vietcombank', '3621458963596509', '13698560566@163.com', '362514', 1),
	(9, 'df6666', 'df', 'dfffff6666', 'fd666', 'df', '23e4', 'df', 'fd', 'fd', 'df', 1);
/*!40000 ALTER TABLE `bus_provider` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_sales
CREATE TABLE IF NOT EXISTS `bus_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salestime` datetime DEFAULT NULL,
  `operateperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleprice` decimal(10,2) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.bus_sales: ~0 rows (approximately)
/*!40000 ALTER TABLE `bus_sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `bus_sales` ENABLE KEYS */;

-- Dumping structure for table warehouse.bus_salesback
CREATE TABLE IF NOT EXISTS `bus_salesback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salesbacktime` datetime DEFAULT NULL,
  `salebackprice` double(10,2) DEFAULT NULL,
  `operateperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.bus_salesback: ~0 rows (approximately)
/*!40000 ALTER TABLE `bus_salesback` DISABLE KEYS */;
/*!40000 ALTER TABLE `bus_salesback` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_dept
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `open` int(11) DEFAULT NULL COMMENT '是否展开，0不展开，1展开',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '是否可用，0不可用，1可用',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.sys_dept: ~11 rows (approximately)
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT IGNORE INTO `sys_dept` (`id`, `pid`, `name`, `open`, `remark`, `address`, `available`, `ordernum`, `createtime`) VALUES
	(1, 0, 'General manager\'s office', 1, 'Large BOSS', 'DNA', 1, 1, '2019-04-10 14:06:32'),
	(2, 1, 'Sales', 0, 'Programmer reeling', 'DNA', 1, 2, '2019-04-10 14:06:32'),
	(3, 1, 'Operations', 0, 'no', 'DNA', 1, 3, '2019-04-10 14:06:32'),
	(4, 1, 'Production Department', 0, '', 'DNA', 1, 4, '2019-04-10 14:06:32'),
	(5, 2, 'Sell ​​one', 0, '', 'DNA', 1, 5, '2019-04-10 14:06:32'),
	(6, 2, 'Sales Department II', 0, '', 'DNA', 1, 6, '2019-04-10 14:06:32'),
	(7, 3, 'Operation One', 0, '', 'DNA', 1, 7, '2019-04-10 14:06:32'),
	(8, 2, 'Sales three', 0, '', '11', 1, 8, '2019-04-10 14:06:32'),
	(9, 2, 'Sales of four', 0, '', '222', 1, 9, '2019-04-10 14:06:32'),
	(10, 2, 'Sales of five', 0, '', '33', 1, 10, '2019-04-10 14:06:32'),
	(18, 4, 'Production one', 0, '', '44', 1, 11, '2019-04-13 09:49:38');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_loginfo
CREATE TABLE IF NOT EXISTS `sys_loginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loginip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.sys_loginfo: ~0 rows (approximately)
/*!40000 ALTER TABLE `sys_loginfo` DISABLE KEYS */;
INSERT IGNORE INTO `sys_loginfo` (`id`, `loginname`, `loginip`, `logintime`) VALUES
	(1, 'Super administrator-system', '0:0:0:0:0:0:0:1', '2020-03-07 14:48:21'),
	(2, 'Super administrator-system', '0:0:0:0:0:0:0:1', '2020-03-08 05:58:59'),
	(3, 'Super administrator-system', '0:0:0:0:0:0:0:1', '2020-03-08 06:16:08');
/*!40000 ALTER TABLE `sys_loginfo` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_notice
CREATE TABLE IF NOT EXISTS `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `createtime` datetime DEFAULT NULL,
  `opername` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.sys_notice: ~0 rows (approximately)
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_permission
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `percode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限编码[只有type=permission才有 user:view]',
  `icon` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `href` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `target` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '是否可用[0不可用，1可用]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.sys_permission: ~66 rows (approximately)
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT IGNORE INTO `sys_permission` (`id`, `pid`, `type`, `title`, `percode`, `icon`, `href`, `target`, `open`, `ordernum`, `available`) VALUES
	(1, 0, 'menu', 'Warehouse', NULL, '&#xe68e;', '', '', 1, 1, 1),
	(2, 1, 'menu', 'Basic', NULL, '&#xe857;', '', '', 0, 2, 1),
	(3, 1, 'menu', 'Purchase', NULL, '&#xe645;', '', '', 0, 3, 1),
	(4, 1, 'menu', ' Sales', NULL, '&#xe611;', '', '', 0, 4, 1),
	(5, 1, 'menu', 'System', NULL, '&#xe614;', '', '', 1, 5, 1),
	(6, 1, 'menu', ' Others', NULL, '&#xe628;', '', '', 0, 6, 1),
	(7, 2, 'menu', 'Customers', NULL, '&#xe651;', '/bus/toCustomerManager', '', 0, 7, 1),
	(8, 2, 'menu', 'Suppliers', NULL, '&#xe658;', '/bus/toProviderManager', '', 0, 8, 1),
	(9, 2, 'menu', 'Products', NULL, '&#xe657;', '/bus/toGoodsManager', '', 0, 9, 1),
	(10, 3, 'menu', 'Product Purchase', NULL, '&#xe756;', '/bus/toInportManager', '', 0, 10, 1),
	(11, 3, 'menu', 'Product Return Inquiry', NULL, '&#xe65a;', '/bus/toOutportManager', '', 0, 11, 1),
	(12, 4, 'menu', 'Product Sales', NULL, '&#xe65b;', '', '', 0, 12, 1),
	(13, 4, 'menu', 'Sales returns inquiry', NULL, '&#xe770;', '', '', 0, 13, 1),
	(14, 5, 'menu', 'Department management', NULL, '&#xe770;', '/sys/toDeptManager', '', 0, 14, 1),
	(15, 5, 'menu', 'Menu management', NULL, '&#xe857;', '/sys/toMenuManager', '', 0, 15, 1),
	(16, 5, 'menu', 'authority management', '', '&#xe857;', '/sys/toPermissionManager', '', 0, 16, 1),
	(17, 5, 'menu', 'Role management', '', '&#xe650;', '/sys/toRoleManager', '', 0, 17, 1),
	(18, 5, 'menu', 'User Management', '', '&#xe612;', '/sys/toUserManager', '', 0, 18, 1),
	(21, 6, 'menu', 'Login log', NULL, '&#xe675;', '/sys/toLoginfoManager', '', 0, 21, 1),
	(22, 6, 'menu', 'system notification', NULL, '&#xe756;', '/sys/toNoticeManager', NULL, 0, 22, 1),
	(23, 6, 'menu', 'Icon management', NULL, '&#xe670;', '../resources/page/icon.html', NULL, 0, 23, 1),
	(30, 14, 'permission', 'Add department', 'dept:create', '', NULL, NULL, 0, 24, 1),
	(31, 14, 'permission', 'Modify department', 'dept:update', '', NULL, NULL, 0, 26, 1),
	(32, 14, 'permission', 'Delete department', 'dept:delete', '', NULL, NULL, 0, 27, 1),
	(34, 15, 'permission', 'Add menu', 'menu:create', '', '', '', 0, 29, 1),
	(35, 15, 'permission', 'Edit menu', 'menu:update', '', NULL, NULL, 0, 30, 1),
	(36, 15, 'permission', 'Delete menu', 'menu:delete', '', NULL, NULL, 0, 31, 1),
	(38, 16, 'permission', 'Add permissions', 'permission:create', '', NULL, NULL, 0, 33, 1),
	(39, 16, 'permission', 'Modify permissions', 'permission:update', '', NULL, NULL, 0, 34, 1),
	(40, 16, 'permission', 'Delete permission', 'permission:delete', '', NULL, NULL, 0, 35, 1),
	(42, 17, 'permission', 'Add role', 'role:create', '', NULL, NULL, 0, 37, 1),
	(43, 17, 'permission', 'Modify role', 'role:update', '', NULL, NULL, 0, 38, 1),
	(44, 17, 'permission', 'Delete role', 'role:delete', '', NULL, NULL, 0, 39, 1),
	(46, 17, 'permission', 'assign permissions', 'role:selectPermission', '', NULL, NULL, 0, 41, 1),
	(47, 18, 'permission', 'Add user', 'user:create', '', NULL, NULL, 0, 42, 1),
	(48, 18, 'permission', 'Modify user', 'user:update', '', NULL, NULL, 0, 43, 1),
	(49, 18, 'permission', 'delete users', 'user:delete', '', NULL, NULL, 0, 44, 1),
	(51, 18, 'permission', 'User-assigned roles', 'user:selectRole', '', NULL, NULL, 0, 46, 1),
	(52, 18, 'permission', 'reset Password', 'user:resetPwd', NULL, NULL, NULL, 0, 47, 1),
	(53, 14, 'permission', 'Department Inquiry', 'dept:view', NULL, NULL, NULL, 0, 48, 1),
	(54, 15, 'permission', 'Menu query', 'menu:view', NULL, NULL, NULL, 0, 49, 1),
	(55, 16, 'permission', 'Permission query', 'permission:view', NULL, NULL, NULL, 0, 50, 1),
	(56, 17, 'permission', 'Role query', 'role:view', NULL, NULL, NULL, 0, 51, 1),
	(57, 18, 'permission', 'User query', 'user:view', NULL, NULL, NULL, 0, 52, 1),
	(68, 7, 'permission', 'Customer inquiry', 'customer:view', NULL, NULL, NULL, NULL, 60, 1),
	(69, 7, 'permission', 'Customer add', 'customer:create', NULL, NULL, NULL, NULL, 61, 1),
	(70, 7, 'permission', 'Customer modification', 'customer:update', NULL, NULL, NULL, NULL, 62, 1),
	(71, 7, 'permission', 'Customer delete', 'customer:delete', NULL, NULL, NULL, NULL, 63, 1),
	(73, 21, 'permission', 'Log query', 'info:view', NULL, NULL, NULL, NULL, 65, 1),
	(74, 21, 'permission', 'Log deletion', 'info:delete', NULL, NULL, NULL, NULL, 66, 1),
	(75, 21, 'permission', 'Log bulk deletion', 'info:batchdelete', NULL, NULL, NULL, NULL, 67, 1),
	(76, 22, 'permission', 'Announcement query', 'notice:view', NULL, NULL, NULL, NULL, 68, 1),
	(77, 22, 'permission', 'Announcement added', 'notice:create', NULL, NULL, NULL, NULL, 69, 1),
	(78, 22, 'permission', 'Announcement modification', 'notice:update', NULL, NULL, NULL, NULL, 70, 1),
	(79, 22, 'permission', 'Announcement delete', 'notice:delete', NULL, NULL, NULL, NULL, 71, 1),
	(81, 8, 'permission', 'Supplier Inquiry', 'provider:view', NULL, NULL, NULL, NULL, 73, 1),
	(82, 8, 'permission', 'Vendor addition', 'provider:create', NULL, NULL, NULL, NULL, 74, 1),
	(83, 8, 'permission', 'Supplier modification', 'provider:update', NULL, NULL, NULL, NULL, 75, 1),
	(84, 8, 'permission', 'Supplier removal', 'provider:delete', NULL, NULL, NULL, NULL, 76, 1),
	(86, 22, 'permission', 'Announcement View', 'notice:viewnotice', NULL, NULL, NULL, NULL, 78, 1),
	(91, 9, 'permission', 'Product inquiry', 'goods:view', NULL, NULL, NULL, 0, 79, 1),
	(92, 9, 'permission', 'Product addition', 'goods:create', NULL, NULL, NULL, 0, 80, 1),
	(116, 9, 'permission', 'Product deletion', 'goods:delete', NULL, NULL, NULL, 0, 84, 1),
	(117, 9, 'permission', 'Product modification', 'goods:update', NULL, NULL, NULL, 0, 85, 1),
	(118, 9, 'permission', 'Product inquiry', 'goods:view', NULL, NULL, NULL, 0, 86, 1),
	(119, 22, 'permission', 'Announcement bulk delete', 'notice:batchdelete', NULL, NULL, NULL, 0, 87, 1);
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_role
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '0不可用，1可用',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- Dumping data for table warehouse.sys_role: ~5 rows (approximately)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT IGNORE INTO `sys_role` (`id`, `name`, `remark`, `available`, `createtime`) VALUES
	(1, 'Super administrator', 'Has all menu permissions', 1, '2019-04-10 14:06:32'),
	(4, 'Basic Data Administrator', 'Basic Data Administrator', 1, '2019-04-10 14:06:32'),
	(5, 'Warehouse Manager', 'Warehouse Manager', 1, '2019-04-10 14:06:32'),
	(6, 'Sales manager', 'Sales manager', 1, '2019-04-10 14:06:32'),
	(7, 'System administrator', 'System administrator', 1, '2019-04-10 14:06:32');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_role_permission
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`rid`,`pid`) USING BTREE,
  KEY `sys_role_permission_ibfk_1` (`pid`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='InnoDB free: 9216 kB; (`pid`) REFER `warehouse/sys_permission`(`id`) ON DELETE C';

-- Dumping data for table warehouse.sys_role_permission: ~129 rows (approximately)
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT IGNORE INTO `sys_role_permission` (`rid`, `pid`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6),
	(1, 7),
	(1, 8),
	(1, 9),
	(1, 10),
	(1, 11),
	(1, 12),
	(1, 13),
	(1, 14),
	(1, 15),
	(1, 16),
	(1, 17),
	(1, 18),
	(1, 21),
	(1, 22),
	(1, 23),
	(1, 30),
	(1, 31),
	(1, 32),
	(1, 34),
	(1, 35),
	(1, 36),
	(1, 38),
	(1, 39),
	(1, 40),
	(1, 42),
	(1, 43),
	(1, 44),
	(1, 46),
	(1, 47),
	(1, 48),
	(1, 49),
	(1, 51),
	(1, 52),
	(1, 53),
	(1, 54),
	(1, 55),
	(1, 56),
	(1, 57),
	(1, 68),
	(1, 69),
	(1, 70),
	(1, 71),
	(1, 73),
	(1, 74),
	(1, 75),
	(1, 76),
	(1, 77),
	(1, 78),
	(1, 79),
	(1, 81),
	(1, 82),
	(1, 83),
	(1, 84),
	(1, 86),
	(1, 91),
	(1, 92),
	(1, 116),
	(1, 117),
	(1, 118),
	(4, 1),
	(4, 2),
	(4, 7),
	(4, 8),
	(4, 9),
	(4, 68),
	(4, 81),
	(4, 91),
	(4, 92),
	(4, 116),
	(4, 117),
	(4, 118),
	(5, 1),
	(5, 2),
	(5, 3),
	(5, 4),
	(5, 7),
	(5, 8),
	(5, 9),
	(5, 10),
	(5, 11),
	(5, 12),
	(5, 13),
	(5, 68),
	(5, 81),
	(5, 91),
	(5, 92),
	(5, 116),
	(5, 117),
	(5, 118),
	(6, 1),
	(6, 4),
	(6, 12),
	(6, 13),
	(7, 1),
	(7, 5),
	(7, 14),
	(7, 15),
	(7, 16),
	(7, 17),
	(7, 18),
	(7, 30),
	(7, 31),
	(7, 32),
	(7, 34),
	(7, 35),
	(7, 36),
	(7, 38),
	(7, 39),
	(7, 40),
	(7, 42),
	(7, 43),
	(7, 44),
	(7, 46),
	(7, 47),
	(7, 48),
	(7, 49),
	(7, 51),
	(7, 52),
	(7, 53),
	(7, 54),
	(7, 55),
	(7, 56),
	(7, 57);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_user
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loginname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL COMMENT '上级领导id',
  `available` int(11) DEFAULT '1' COMMENT '是否可用，0不可用，1可用',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码',
  `type` int(255) DEFAULT NULL COMMENT '用户类型[0超级管理员，1管理员，2普通用户]',
  `imgpath` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户头像地址',
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '盐',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_sys_dept_sys_user` (`deptid`) USING BTREE,
  CONSTRAINT `FK_sys_dept_sys_user` FOREIGN KEY (`deptid`) REFERENCES `sys_dept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='InnoDB free: 9216 kB; (`deptid`) REFER `warehouse/sys_dept`(`id`) ON UPDATE CASC';

-- Dumping data for table warehouse.sys_user: ~12 rows (approximately)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT IGNORE INTO `sys_user` (`id`, `name`, `loginname`, `pwd`, `address`, `sex`, `remark`, `deptid`, `hiredate`, `mgr`, `available`, `ordernum`, `type`, `imgpath`, `salt`) VALUES
	(1, 'Super administrator', 'system', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 1, '2018-06-25 11:06:34', NULL, 1, 1, 0, '2020-03-06/0C2A3C5D38484CE0AEBB446433B1D4E4.jpg', '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(2, 'Luo Yi-', 'luoyi', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 1, '2019-11-23 20:52:16', NULL, 1, 2, 0, '../resources/images/defaultusertitle.jpg', '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(3, 'Li Yuesu', 'liyuesu', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 1, '2019-12-02 10:49:12', NULL, 1, 3, 1, NULL, '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(4, 'Li Si', 'lisi', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 2, '2019-12-02 18:57:04', 3, 1, 4, 1, NULL, '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(5, 'Vuong Ngu', 'wangwu', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 5, '2019-12-02 18:57:42', 4, 1, 5, 1, NULL, '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(6, 'Lao LUc', 'zhaoliu', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 5, '2019-12-02 18:59:05', 5, 1, 6, 1, NULL, '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(7, 'Lao That', 'chengqi', '532ac00e86893901af5f0be6b704dbc7', '35 Cao Thang', 1, '', 4, '2019-12-03 09:52:18', 3, 1, 7, 1, NULL, '04A93C74C8294AA09A8B974FD1F4ECBB'),
	(10, 'SUbe', 'subeidan', 'b661f48dc70d448773be54874198788c', '35 Cao Thang', 0, '', 3, '2019-12-03 00:00:00', 3, 1, 9, 1, NULL, '950289EBDBA24C7789E392E1D0254635'),
	(11, 'Han xu', 'sijialiyuehanxun', 'f8408d1ccc3f83e4f035de3896569b76', '35 Cao Thang', 0, '', 7, '2019-12-03 14:23:35', 10, 1, 10, 1, NULL, '85DB5F84987146559A75B4B0DCB7DE4F'),
	(12, 'Tony', 'tuoni', '1403e113a2936d4509e9c13b8849f4b5', '35 Cao Thang', 1, '', 7, '2019-12-03 00:00:00', 11, 1, 11, 1, NULL, '571059AF59E64A7D92FECB93FA1B0AEF'),
	(13, 'Weisi', 'jiaweisi', '98f28b861888f4274cb423345dce4bcc', '35 Cao Thang', 1, '', 3, '2019-12-03 00:00:00', 12, 1, 12, 1, NULL, '7258E2D93A3F429085B34BBD8E345CE7'),
	(15, 'Thuong Dan', 'shangdan', '9628dd6423df694d091b0dbb01579cfa', '35 Cao Thang', 1, '', 18, '2019-12-06 08:50:14', 7, 1, 13, 1, NULL, 'C22D85AE05BD43F9A4B4D4FBDA3C7394');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping structure for table warehouse.sys_user_role
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `FK_sys_user_role_1` (`rid`) USING BTREE,
  CONSTRAINT `FK_sys_user_role_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_sys_user_role_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT COMMENT='InnoDB free: 9216 kB; (`rid`) REFER `warehouse/sys_role`(`id`); (`uid`) REFER `w';

-- Dumping data for table warehouse.sys_user_role: ~8 rows (approximately)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT IGNORE INTO `sys_user_role` (`uid`, `rid`) VALUES
	(1, 1),
	(3, 1),
	(4, 4),
	(5, 5),
	(6, 6),
	(7, 6),
	(10, 7),
	(15, 4);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
