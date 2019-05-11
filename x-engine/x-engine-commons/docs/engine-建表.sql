/*
 Navicat Premium Data Transfer

 Source Server         : 新引擎测试服
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : 192.168.1.116
 Source Database       : engine

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : utf-8

 Date: 10/16/2018 11:23:31 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `accountinfo`
-- ----------------------------
DROP TABLE IF EXISTS `accountinfo`;
CREATE TABLE `accountinfo` (
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `masonry` bigint(20) NOT NULL DEFAULT '0' COMMENT '砖石数量',
  `goldCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户当前携带的金币数量',
  `boxCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户的保险柜金币数量',
  `freezeAmount` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额',
  `winAmount` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计赢金数额，不包含在组局场中的数据',
  `awardCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '领取的奖励数量，用着佣金榜',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户详细信息表';

-- ----------------------------
--  Table structure for `activeplayer`
-- ----------------------------
DROP TABLE IF EXISTS `activeplayer`;
CREATE TABLE `activeplayer` (
  `datestr` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '活跃的时间 （例子：2018-10-10）',
  `userId` bigint(20) DEFAULT NULL COMMENT '玩家的id',
 `gametype` int(20) DEFAULT NULL COMMENT '游戏类型',
   UNIQUE KEY `userId_date_game` (`datestr`,`userId`,`gametype`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当天玩家活跃表';

-- ----------------------------
--  Table structure for `adduserrecord`
-- ----------------------------
DROP TABLE IF EXISTS `adduserrecord`;
CREATE TABLE `adduserrecord` (
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `channelId` varchar(50) DEFAULT NULL COMMENT '平台ID',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '新增人数',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '新增玩家时间',
  `jsonData` text COMMENT '公共josn',
   UNIQUE KEY `userId_time` (`userId`,`time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新增玩家记录';

-- ----------------------------
--  Table structure for `appconfig`
-- ----------------------------
DROP TABLE IF EXISTS `appconfig`;
CREATE TABLE `appconfig` (
  `channelId` varchar(100) NOT NULL COMMENT '渠道ID',
  `channelName` varchar(100) DEFAULT NULL COMMENT '平台名称',
  `url` varchar(100) DEFAULT NULL COMMENT '平台对应URL',
  `jsonData` text COMMENT '公共josn',
  PRIMARY KEY (`channelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台配置表';

-- ----------------------------
--  Table structure for `awardrecord`
-- ----------------------------
DROP TABLE IF EXISTS `awardrecord`;
CREATE TABLE `awardrecord` (
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `channelId` varchar(50) DEFAULT NULL COMMENT '平台ID',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '当期获得的奖励',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '奖励的时间',
  `totalResult` bigint(20) NOT NULL DEFAULT '0' COMMENT '当期总业绩',
  `lowerAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '当期下级代理获得奖励 （直属+无限级）',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1领取，2未领取',
  `takeTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '领取时间',
  `jsonData` text COMMENT '公共josn',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每期奖励记录';

-- ----------------------------
--  Table structure for `battlescore`
-- ----------------------------
DROP TABLE IF EXISTS `battlescore`;
CREATE TABLE `battlescore` (
  `roomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `endTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏结束的时间',
  `imageUrl` varchar(100) DEFAULT NULL COMMENT '玩家的头像',
  `nickName` varchar(100) DEFAULT NULL COMMENT '玩家的名称',
  `borderUrl` varchar(180) DEFAULT NULL COMMENT '头像边框url',
  `playerId` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家的id',
  `roomNumber` int(11) NOT NULL DEFAULT '0' COMMENT '房间号',
  `round` int(11) NOT NULL DEFAULT '0' COMMENT '当前的回合数',
  `score` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家的积分',
  `totalRound` int(11) NOT NULL DEFAULT '0' COMMENT '游戏总的回合数',
  `playerType` int(11) NOT NULL DEFAULT '0' COMMENT '房间类型 0未知   1：癞子牛牛  2： 癞子诈金花   3：三公诈金花',
  `jsonData` text COMMENT '公共josn',
  UNIQUE KEY `roomId_playerId` (`roomId`,`playerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家的一场游戏的总战绩';

-- ----------------------------
--  Table structure for `fakenotice`
-- ----------------------------
DROP TABLE IF EXISTS `fakenotice`;
CREATE TABLE `fakenotice` (
  `noticeId` bigint(20) NOT NULL COMMENT '公告id',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `nickName` varchar(100) DEFAULT NULL COMMENT '玩家的名称',
  `gameName` varchar(100) DEFAULT NULL COMMENT '游戏名称',
  `goldCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '金币数量',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `game`
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `gameId` int(11) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `gameIndex` int(11) NOT NULL DEFAULT '0' COMMENT '游戏在列表中的排序位置',
  `gameIp` varchar(100) DEFAULT NULL COMMENT '游戏网关ip地址',
  `gameName` varchar(100) DEFAULT NULL COMMENT '游戏的名称',
  `gamePort` int(11) NOT NULL DEFAULT '0' COMMENT '游戏网关端口号',
  `logoUrl` varchar(100) DEFAULT NULL COMMENT '游戏logo图片地址',
  `type` bigint(20) NOT NULL DEFAULT '0' COMMENT '类型选择 0未知 1公测 2生产 3维护',
  `jsonData` text COMMENT '公共josn',
  `gameType` int(2) NOT NULL DEFAULT '0' COMMENT '1,好友组局，2,全国匹配对战',
  `expectation` int(10) NOT NULL DEFAULT '0' COMMENT '开房期望人数',
  `oddsRoomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '倍率房列表id',
  `gameMode` int(11) NOT NULL DEFAULT '0' COMMENT '游戏模式 例如1明牌抢庄，2明牌开船等，设置时候和相应游戏前端确定',
  `borderNum` int(2) NOT NULL DEFAULT '0' COMMENT '游戏角标配置，0默认，1HOT，2New',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏列表';

-- ----------------------------
--  Table structure for `gameratio`
-- ----------------------------
DROP TABLE IF EXISTS `gameratio`;
CREATE TABLE `gameratio` (
  `channelId` varchar(100) DEFAULT NULL COMMENT '渠道ID',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '比例等级 1全局比例，2石头剪刀布游戏比例',
  `ratio` int(11) NOT NULL DEFAULT '0' COMMENT '比例 设整数，取百分比 10/100 = 10%',
  `jsonData` text COMMENT '公共josn',
 UNIQUE KEY `channelId_level` (`channelId`,`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏税收比例';

-- ----------------------------
--  Table structure for `gameuser`
-- ----------------------------
DROP TABLE IF EXISTS `gameuser`;
CREATE TABLE `gameuser` (
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键 唯一id 9位',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `channelName` varchar(100) DEFAULT NULL COMMENT '所在平台',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户注册时间',
  `idCardNumber` varchar(100) DEFAULT NULL COMMENT '身份证号码',
  `imageUrl` varchar(100) DEFAULT NULL COMMENT '头像',
  `killedTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '封号日期',
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后一次登陆时间',
  `nickName` varchar(100) DEFAULT NULL COMMENT '昵称',
  `phoneNumber` varchar(100) DEFAULT NULL COMMENT '手机号',
  `realName` varchar(100) DEFAULT NULL COMMENT '实名认证真实姓名',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '账户状态 0正常 1封号',
  `sex` int(4) NOT NULL DEFAULT '0' COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `jsonData` text,
  `remark` varchar(100) DEFAULT NULL COMMENT '个人宣言备注',
  `password` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `borderUrl` varchar(100) DEFAULT NULL COMMENT '头像vip边框',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号类';

-- ----------------------------
--  Table structure for `goldcustomerlog`
-- ----------------------------
DROP TABLE IF EXISTS `goldcustomerlog`;
CREATE TABLE `goldcustomerlog` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键id',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `changeType` int(2) NOT NULL DEFAULT '0' COMMENT '1.兑换金币，2.存入金币 ，3.取出金币 ，4.添加金币后台操作 5.扣除金币，6.领取奖励,8 抽水,9金币输赢(匹配场),10 金币输赢（组局场）,11手机绑定',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `channelId` varchar(20) DEFAULT NULL COMMENT '平台ID',
  `beforeCount` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换之前数量',
  `afterCount` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换之后数量',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换的数量',
  `jsonData` text COMMENT '公共josn',
  `operaterId` int(11) NOT NULL DEFAULT '0' COMMENT '管理员id',
  `depositCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '保险柜金币数量',
  `toUserId` bigint(20) NOT NULL DEFAULT '0' COMMENT '去向用户id',
  `gameId` int(11) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `roomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `RoomType` int(11) NOT NULL DEFAULT '0' COMMENT '房间倍率：1.新手场2.进阶场---6.土豪场',
 `gameType` int(11) NOT NULL DEFAULT '0' COMMENT '游戏类型 1金币牛牛，2白人牛牛，3石头剪刀布...',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='金币消费记录';

-- ----------------------------
--  Table structure for `goldroom`
-- ----------------------------
DROP TABLE IF EXISTS `goldroom`;
CREATE TABLE `goldroom` (
  `oddsRoomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id 主键',
  `RoomType` int(2) NOT NULL DEFAULT '0' COMMENT '房间类型（1新手场，2进阶场，3中级场，4高级场，5职业场，6土豪场）',
  `RoomName` varchar(50) DEFAULT NULL COMMENT '房间名称',
  `RoomIndex` int(2) NOT NULL DEFAULT '0' COMMENT '游戏在列表中的排序位置',
  `odds` int(5) NOT NULL DEFAULT '0' COMMENT '房间倍率',
  `minCome` int(10) NOT NULL DEFAULT '0' COMMENT '最低进入金额',
  `maxINum` int(10) NOT NULL DEFAULT '0' COMMENT '最大下注',
  `minINum` int(10) NOT NULL DEFAULT '0' COMMENT '最小下注',
  `maxNum` int(10) NOT NULL DEFAULT '0' COMMENT '携带的最大数量',
  `minNum` int(10) NOT NULL DEFAULT '0' COMMENT '携带的最小数量',
  `plotting` int(2) NOT NULL DEFAULT '0' COMMENT '服务比例',
  `openStatus` int(2) NOT NULL DEFAULT '0' COMMENT '倍率状态 （1开启，2未开启）',
  `channelId` varchar(30) DEFAULT NULL COMMENT '平台ID',
  `jsonData` text COMMENT '公共josn',
  `gameId` int(11) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `online` int(11) NOT NULL DEFAULT '0' COMMENT '在线人数',
  PRIMARY KEY (`oddsRoomId`,`RoomType`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='金币场房间信息';

-- ----------------------------
--  Table structure for `goldwin`
-- ----------------------------
DROP TABLE IF EXISTS `goldwin`;
CREATE TABLE `goldwin` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '自增id',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `gameType` int(11) NOT NULL DEFAULT '0' COMMENT '游戏类型',
  `rate` int(11) NOT NULL DEFAULT '0' COMMENT '房间倍率',
  `round` int(11) NOT NULL DEFAULT '0' COMMENT '游戏局数',
  `peopleNum` int(11) NOT NULL DEFAULT '0' COMMENT '游戏人数',
  `sysGold` bigint(20) NOT NULL DEFAULT '0' COMMENT '系统盈亏 存在正负区别  正为盈利  负为亏本',
  `choushui` bigint(20) NOT NULL DEFAULT '0' COMMENT '服务费 游戏抽水',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '结算时间 年月日 2018-08-07',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='金币倍率赢收';

-- ----------------------------
--  Table structure for `historybattlescore`
-- ----------------------------
DROP TABLE IF EXISTS `historybattlescore`;
CREATE TABLE `historybattlescore` (
  `id` bigint(11) NOT NULL DEFAULT '0' COMMENT '战id',
  `belowScore` int(11) NOT NULL DEFAULT '0' COMMENT '下注数(玩家进入房间选择下注号数)',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `curRounds` int(11) NOT NULL DEFAULT '0' COMMENT '当前局数 可用来排序  ',
  `gameType` int(11) NOT NULL DEFAULT '0' COMMENT '游戏类型',
  `goalDifference` bigint(20) NOT NULL DEFAULT '0' COMMENT '得失分数',
  `image` varchar(100) DEFAULT NULL COMMENT '头像url',
  `patternsType` int(11) NOT NULL DEFAULT '0' COMMENT '牌型',
  `playerId` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家的id',
  `pokers` varchar(100) DEFAULT NULL COMMENT '牌型',
  `rate` int(11) NOT NULL DEFAULT '0' COMMENT '倍率   炸金花--》输赢的方式： 1 比牌赢  4 弃牌  6 比牌输  9 弃牌  10 比牌输',
  `RoomCode` int(11) NOT NULL DEFAULT '0' COMMENT '房间号',
  `roomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间ID',
  `score` bigint(20) NOT NULL DEFAULT '0' COMMENT '积分',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0 观战者  1参与者',
  `userName` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `isBanker` int(2) NOT NULL DEFAULT '0' COMMENT '是否是庄家 0 否 1是',
  `dot` varchar(100) DEFAULT NULL COMMENT '组合牌',
  `bureau` bigint(2) NOT NULL DEFAULT '0' COMMENT '2轮(1上半局，2下半局 )',
  `jsonData` text,
  `victoryId` bigint(20) NOT NULL DEFAULT '0' COMMENT '胜利者id（猜拳使用）',
  `opening` bigint(20) NOT NULL DEFAULT '0' COMMENT '开局金额',
  `intPlayer` int(11) NOT NULL DEFAULT '0' COMMENT '是否一个比赛的玩家使用类型定义',
  `borderUrl` varchar(180) DEFAULT NULL COMMENT '头像边框url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='战绩明细';

-- ----------------------------
--  Table structure for `logingamestat`
-- ----------------------------
DROP TABLE IF EXISTS `logingamestat`;
CREATE TABLE `logingamestat` (
  `uuid` varchar(100) DEFAULT NULL,
  `channelId` varchar(10) DEFAULT NULL COMMENT '平台ID',
  `gameId` int(10) NOT NULL DEFAULT '0' COMMENT '游戏ID',
  `time` bigint(50) NOT NULL DEFAULT '0' COMMENT '时间',
  `count` int(10) NOT NULL DEFAULT '0' COMMENT '人数',
  `jsonData` text,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏登录数统计';

-- ----------------------------
--  Table structure for `loginstat`
-- ----------------------------
DROP TABLE IF EXISTS `loginstat`;
CREATE TABLE `loginstat` (
  `uuid` varchar(100) DEFAULT NULL,
  `channelId` varchar(4) DEFAULT NULL COMMENT '平台ID',
  `time` bigint(50) NOT NULL DEFAULT '0' COMMENT '时间',
  `count` int(4) NOT NULL DEFAULT '0' COMMENT '人数',
  `jsonData` text,
   PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录总数统计';

-- ----------------------------
--  Table structure for `masonryrecord`
-- ----------------------------
DROP TABLE IF EXISTS `masonryrecord`;
CREATE TABLE `masonryrecord` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键id',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `changeType` int(2) NOT NULL DEFAULT '0' COMMENT '1充值，2兑换金币消耗的砖石，3添加砖石后台操作',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `channelId` varchar(20) DEFAULT NULL COMMENT '平台ID',
  `beforeCount` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换之前数量',
  `afterCount` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换之后数量',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '兑换的数量',
  `jsonData` text COMMENT '公共josn',
`operaterId` bigint(11) NOT NULL DEFAULT '0' COMMENT '管理员id',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='砖石变动记录';

-- ----------------------------
--  Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `endTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束时间',
  `noticeContent` varchar(100) DEFAULT NULL COMMENT '公告内容',
  `noticeType` int(2) NOT NULL DEFAULT '0' COMMENT '通知类型:1.通知，2.公告',
  `publishType` int(2) NOT NULL DEFAULT '0' COMMENT '是否已经发布：0.未发布，1.已发布',
  `startTime` bigint(20) NOT NULL COMMENT '开始时间',
  `status` int(2) NOT NULL COMMENT ' 0：正常；1：删除',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `jsonData` text,
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `isRead` int(2) NOT NULL DEFAULT '0' COMMENT '是否已阅 （0未读，1已阅） 临时业务变量,不是很重要，但是不能删除',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知与公告';

-- ----------------------------
--  Table structure for `onlinestat`
-- ----------------------------
DROP TABLE IF EXISTS `onlinestat`;
CREATE TABLE `onlinestat` (
  `uuid` varchar(100) DEFAULT NULL,
  `channelId` varchar(4) DEFAULT NULL COMMENT '平台ID',
  `gameId` int(11) NOT NULL DEFAULT '0' COMMENT '游戏ID',
  `time` bigint(50) NOT NULL DEFAULT '0' COMMENT '时间',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '人数',
  `jsonData` text,
   PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线统计';

-- ----------------------------
--  Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `orderid` varchar(255) DEFAULT NULL COMMENT '订单id',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goodsid` bigint(20) DEFAULT NULL COMMENT '商品id',
  `channelid` varchar(4) DEFAULT NULL COMMENT '平台id',
  `createtime` datetime DEFAULT NULL COMMENT '订单创建时间',
  `totalfee` bigint(20) DEFAULT NULL COMMENT '支付总价格',
  `status` int(2) DEFAULT NULL COMMENT '支付状态 0未支付 1失败 2成功',
  `goodstype` int(2) DEFAULT NULL COMMENT ' 商品类型（1砖石，2金币，3房卡）',
  `paytype` varchar(100) DEFAULT NULL COMMENT '支付方式 Cash,WechatPay,AliPay,unionpay',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `orderno` varchar(255) DEFAULT NULL COMMENT '系统平台返回的订单',
  `mch` varchar(255) DEFAULT NULL COMMENT '商户注册获得的商务号',
  `paytime` datetime DEFAULT NULL COMMENT '订单支付完成时间',
  `ip` varchar(255) DEFAULT NULL COMMENT '充值玩家ip',
  `goodsnum` int(10) DEFAULT '0' COMMENT '充值商品的数量',
  `goodsname` varchar(100) DEFAULT NULL COMMENT '商品名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
 `orderId` varchar(100) NOT NULL COMMENT '订单号',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `channelId` varchar(10) DEFAULT NULL COMMENT '渠道ID',
  `pid` bigint(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '充值的商品的数量',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '充值状态，0未支付，1充值成功',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单创建时间',
  `totalPrice` bigint(10) NOT NULL DEFAULT '0' COMMENT '充值金额',
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值交易记录';

-- ----------------------------
--  Table structure for `paychannel`
-- ----------------------------
DROP TABLE IF EXISTS `paychannel`;
CREATE TABLE `paychannel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `paytype` varchar(80) DEFAULT NULL COMMENT '支付类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '支付方式描述',
  `selected` int(2) DEFAULT '0' COMMENT '被选择的支付方式 （0未选择，1已选择）',
  `openplatform` int(2) DEFAULT '0' COMMENT '是否直接打开支付平台',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id 主键',
  `productName` varchar(50) DEFAULT NULL COMMENT '商品的名称',
  `count` bigint(10) NOT NULL DEFAULT '0' COMMENT '商品的数量',
  `totalPrice` bigint(5) NOT NULL DEFAULT '0' COMMENT '商品售卖总价 （单位：元）',
  `productIndex` int(2) NOT NULL DEFAULT '0' COMMENT '商品在列表中的排序位置',
  `channelId` varchar(10) DEFAULT NULL COMMENT '渠道ID',
  `borderUrl` varchar(100) DEFAULT NULL COMMENT '头像vip边框',
  `jsonData` text COMMENT '公共josn',
  `productType` int(2) NOT NULL DEFAULT '0' COMMENT '商品种类，1砖石，2金币,3头像框',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息';

-- ----------------------------
--  Table structure for `reseller`
-- ----------------------------
DROP TABLE IF EXISTS `reseller`;
CREATE TABLE `reseller` (
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前用户id',
  `superiorId` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级玩家的id',
  `relationId` bigint(20) NOT NULL DEFAULT '0' COMMENT '下级直属玩家关联id',
  `receiveNum` bigint(20) NOT NULL DEFAULT '0' COMMENT '可以领取的奖励',
  `channelId` varchar(20) DEFAULT NULL COMMENT '平台ID',
  `rewardSum` bigint(20) NOT NULL DEFAULT '0' COMMENT '获得总奖励',
  `promoting` bigint(20) NOT NULL DEFAULT '0' COMMENT '总推广人数',
  `nickName` varchar(100) DEFAULT NULL COMMENT '玩家昵称',
  `jsonData` text,
  `todayFlow` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前玩家游戏流水',
  `todayReport` bigint(20) NOT NULL DEFAULT '0' COMMENT '今日当前玩家游戏输赢业绩',
  `lowerCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '下级玩家所有人数（直属+无限级）',
  `loginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后登陆时间',
  `todayAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '今日佣金',
  `yesterdayAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '昨日佣金',
  `yesterdayReport` bigint(20) NOT NULL DEFAULT '0' COMMENT '昨日业绩',
  `weekReport` bigint(20) NOT NULL DEFAULT '0' COMMENT '本周业绩',
  `lastWeekReport` bigint(20) NOT NULL DEFAULT '0' COMMENT '上周业绩 ',
  `weekAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '本周返利金额',
  `yesterdayFlow` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家输赢昨日流水',
  `weekFlow` bigint(20) NOT NULL DEFAULT '0' COMMENT '本周流水',
  `lastWeekFlow` bigint(20) NOT NULL DEFAULT '0' COMMENT '上周流水',
  `lastWeekAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '上周返利金额',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销用户表';

-- ----------------------------
--  Table structure for `resellerlevel`
-- ----------------------------
DROP TABLE IF EXISTS `resellerlevel`;
CREATE TABLE `resellerlevel` (
  `channelId` varchar(100) NOT NULL COMMENT '渠道ID',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '代理级别： 1玩家，2资深玩家，3会员，4资深会员，5代理，6高级代理，7超级代理，8总代理，9超级总代理，10总监，11超级总监，12股东，13大股东，14超级大股东，15董事长',
  `minLimit` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前代理级别最低业绩数量',
  `maxLimit` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前代理级别最高业绩数量',
  `ratio` int(11) NOT NULL DEFAULT '0' COMMENT '当前奖励业绩比例 设整数，取百分比 10/100 = 10%  （无限级别税收比例*系数*每个游戏税收比例）',
  `basicNumber` int(11) NOT NULL DEFAULT '0' COMMENT '当前级别系数 设整数，取百分比',
  `jsonData` text COMMENT '公共josn',
  PRIMARY KEY (`channelId`,`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理级别';

-- ----------------------------
--  Table structure for `resellerrelation`
-- ----------------------------
DROP TABLE IF EXISTS `resellerrelation`;
CREATE TABLE `resellerrelation` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键id',
  `lowerId` bigint(20) NOT NULL DEFAULT '0' COMMENT '直属玩家id下级用户id',
  PRIMARY KEY (`id`,`lowerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销用户关系表';

-- ----------------------------
--  Table structure for `risklog`
-- ----------------------------
DROP TABLE IF EXISTS `risklog`;
CREATE TABLE `risklog` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台id',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1.未处理 2.已处理3.白名单',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '报警时间',
  `content` varchar(100) DEFAULT NULL COMMENT '警报原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4865 DEFAULT CHARSET=utf8mb4 COMMENT='风险警报记录';

-- ----------------------------
--  Table structure for `risksetting`
-- ----------------------------
DROP TABLE IF EXISTS `risksetting`;
CREATE TABLE `risksetting` (
  `channelId` varchar(20) NOT NULL DEFAULT '' COMMENT '平台ID',
  `oneNum` bigint(20) NOT NULL DEFAULT '0' COMMENT '预警单笔金额',
  `zoneTime` double NOT NULL DEFAULT '0' COMMENT '预警区间时长',
  `zoneBusinessNum` int(11) NOT NULL DEFAULT '0' COMMENT '区间交易笔数',
  `zoneTotalNum` bigint(20) NOT NULL DEFAULT '0' COMMENT '区间预警总金额',
  `noticeTelephone` text COMMENT '通知电话，逗号隔开',
  `noticeEmail` text COMMENT '通知邮箱 ，逗号隔开',
  PRIMARY KEY (`channelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风险警报设置';

-- ----------------------------
--  Table structure for `ristwhitelist`
-- ----------------------------
DROP TABLE IF EXISTS `ristwhitelist`;
CREATE TABLE `ristwhitelist` (
  `channelId` varchar(20) NOT NULL DEFAULT '',
  `userId` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`channelId`,`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风险警报白名单';

-- ----------------------------
--  Table structure for `room`
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `roomId` bigint(20) NOT NULL COMMENT '主键 房间的id',
  `bankerMode` int(11) NOT NULL DEFAULT '0' COMMENT '庄家模式：1明牌抢庄 2明牌开船 3规定庄家 4牛上庄',
  `bet` int(11) NOT NULL DEFAULT '0' COMMENT '底分',
  `bottompourType` int(11) NOT NULL DEFAULT '0' COMMENT ' 下注类型：目前有：(1)x1x2x3x5 (2)x1x3x5x8',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `creatorId` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `creatorName` varchar(100) DEFAULT NULL COMMENT '创建者名称',
  `curRounds` int(11) NOT NULL DEFAULT '0' COMMENT '当前局数',
  `endTime` bigint(20) NOT NULL COMMENT '结束时间',
  `lookNum` int(11) NOT NULL DEFAULT '0' COMMENT '观看人数',
  `playType` varchar(100) DEFAULT NULL COMMENT ' 玩法列表 列：1，2，3',
  `playerNum` int(11) NOT NULL DEFAULT '0' COMMENT '玩家人数',
  `playerType` int(11) NOT NULL DEFAULT '0' COMMENT '房间类型 0未知 1：癞子牛牛 2： 癞子诈金花 3：三公诈金花',
  `regulationType` int(11) NOT NULL DEFAULT '0' COMMENT '选择规制：目前有：(1)牛牛x4九牛x3牛八x3牛七x2 (2)牛牛x3九牛x2牛八x2  牌九规制类型选择：0未知  1:至尊8倍xxxx  2:至尊10倍xxxxx',
  `roomNumber` int(11) NOT NULL DEFAULT '0' COMMENT '房间号',
  `rounds` int(11) NOT NULL DEFAULT '0' COMMENT '总局数',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT ' 房间状态 0：未知 1：未开始 2： 已开启未结束 3：已正常结束 4：已开启已关闭 5：未开启已过期',
  `suitPatterns` varchar(100) DEFAULT NULL COMMENT '牌型 0:无牛 1~10:牛1到牛牛 11:地牛 12:天牛 13：顺子牛 14：五花牛 15：同花牛 16：葫芦牛 17：炸弹牛 18：五小牛 19：金牛',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '房间得分',
  `selectType` int(11) NOT NULL DEFAULT '0' COMMENT '模式1癞子模式 2经典牛牛',
  `openStatus` int(11) NOT NULL DEFAULT '0' COMMENT '开局状态：（0：未知，1：未开局，2：开局已玩过）',
  `seebet` int(4) NOT NULL DEFAULT '0' COMMENT '牌    最低的筹码总数',
  `regulationString` varchar(100) DEFAULT NULL COMMENT '新需求： 规则',
  `bottompourString` varchar(100) DEFAULT NULL COMMENT ' 新需求： 下注倍数的选择',
  `compareBet` int(4) NOT NULL DEFAULT '0' COMMENT '比牌   最低筹码值总数E',
  `oneMaxDet` int(4) NOT NULL DEFAULT '0' COMMENT '游戏中筹码最大值 大于这个值 直接进入结算',
  `baozi235` int(4) NOT NULL DEFAULT '0' COMMENT '是否235吃豹子  0 否  1 是',
  `freeBet` int(4) NOT NULL DEFAULT '0' COMMENT '是否随意比牌  0否 1是',
  `friend` int(4) NOT NULL DEFAULT '0' COMMENT '是否是好友场   0 否 1 是',
  `quota` int(5) NOT NULL DEFAULT '0' COMMENT '限额',
  `method` int(5) NOT NULL DEFAULT '0' COMMENT '玩法  1 大牌九 2小牌九',
  `setting` int(4) NOT NULL DEFAULT '0' COMMENT '丁三牌与二四牌互换加入房间设置高级选中 0 未开启 1开启',
  `jsonData` text,
  `isMatch` int(4) NOT NULL DEFAULT '0' COMMENT '1 组局场  2 匹配场',
  `rateType` int(11) NOT NULL DEFAULT '0' COMMENT '1.新手场 -- 6.土豪场',
  `ostosType` int(2) NOT NULL DEFAULT '0' COMMENT '血拼模式  倍数 默认为0',
  `service` bigint(20) NOT NULL DEFAULT '0' COMMENT '服务费',
  `win` bigint(20) NOT NULL DEFAULT '0' COMMENT '系统赢亏',
  `leastGold` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间准入金',
  PRIMARY KEY (`roomId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- ----------------------------
--  Table structure for `roomplayer`
-- ----------------------------
DROP TABLE IF EXISTS `roomplayer`;
CREATE TABLE `roomplayer` (
  `playId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键 玩家的id',
  `channelId` varchar(100) DEFAULT NULL COMMENT '平台ID',
  `isCreate` int(11) NOT NULL DEFAULT '0' COMMENT '是否为创建者，0参与者，1是创建者',
  `roomId` bigint(20) NOT NULL COMMENT '房间的id',
  `roomNumber` int(11) NOT NULL DEFAULT '0' COMMENT '房间号码',
  `seat` int(11) NOT NULL DEFAULT '0' COMMENT '玩家的座位',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT ' 玩家的状态 0 开始 1 准备',
  `openStatus` int(4) NOT NULL DEFAULT '0' COMMENT '玩家开局状态：（0：未知，1：未开局，2：开局已玩过）',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '玩家的类型  0玩家  1观战',
  `jsonData` text COMMENT '公共josn',
   UNIQUE KEY `roomId_playId` (`roomId`,`playId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间成员表';

-- ----------------------------
--  Table structure for `roomstat`
-- ----------------------------
DROP TABLE IF EXISTS `roomstat`;
CREATE TABLE `roomstat` (
  `uuid` varchar(100) DEFAULT NULL,
  `channelId` varchar(4) DEFAULT NULL COMMENT '平台ID',
  `gameId` int(11) NOT NULL DEFAULT '0' COMMENT '游戏ID',
  `time` bigint(50) NOT NULL DEFAULT '0' COMMENT '时间',
  `count` int(4) NOT NULL DEFAULT '0' COMMENT '房间数',
  `jsonData` text,
PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创建房间数统计';

-- ----------------------------
--  Table structure for `servicestaff`
-- ----------------------------
DROP TABLE IF EXISTS `servicestaff`;
CREATE TABLE `servicestaff` (
  `serviceId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serverType` int(2) NOT NULL DEFAULT '0' COMMENT '客服类型 : 0-客服  1-财务  2-投诉',
  `channelId` varchar(20) DEFAULT NULL COMMENT '平台ID',
  `userName` varchar(100) DEFAULT NULL COMMENT '客服姓名',
  `wechat` varchar(100) DEFAULT NULL COMMENT '微信号',
  PRIMARY KEY (`serviceId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=796 DEFAULT CHARSET=utf8mb4 COMMENT='客服配置';

-- ----------------------------
--  Table structure for `settlementlog`
-- ----------------------------
DROP TABLE IF EXISTS `settlementlog`;
CREATE TABLE `settlementlog` (
  `todayAward` bigint(20) NOT NULL DEFAULT '0' COMMENT '今日佣金奖励',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前用户id',
  `channelId` varchar(50) DEFAULT NULL COMMENT '平台ID',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '结算时间',
  `todayPerformance` bigint(20) NOT NULL DEFAULT '0' COMMENT '当日结算业绩',
 `lowerCount` bigint(20) NOT NULL DEFAULT '0' COMMENT '下级人数',
UNIQUE KEY `userId_time` (`userId`,`time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='结算日志';

-- ----------------------------
--  Table structure for `taxratiolevel`
-- ----------------------------
DROP TABLE IF EXISTS `taxratiolevel`;
CREATE TABLE `taxratiolevel` (
  `channelId` varchar(22) NOT NULL COMMENT '平台ID',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '比例等级 1.无限级奖励比例，2直属代理奖励比例，3系统营收比例',
  `ratio` int(11) NOT NULL DEFAULT '0' COMMENT '比例 设整数，取百分比',
  `jsonData` text COMMENT '公共josn',
UNIQUE KEY `channelId_level` (`channelId`,`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='税收奖励各级别配置比例表';

-- ----------------------------
--  Table structure for `winlose`
-- ----------------------------
DROP TABLE IF EXISTS `winlose`;
CREATE TABLE `winlose` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '自增id',
  `roomId` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `lunNum` bigint(20) NOT NULL DEFAULT '0' COMMENT '轮数',
  `position` int(11) NOT NULL DEFAULT '0' COMMENT '方位',
  `pokers` varchar(100) DEFAULT NULL COMMENT '具体牌',
  `cardType` int(11) NOT NULL DEFAULT '0' COMMENT '牌型',
  `betNum` int(11) NOT NULL DEFAULT '0' COMMENT '下注人数',
  `winGold` bigint(20) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='输赢概况';

SET FOREIGN_KEY_CHECKS = 1;
