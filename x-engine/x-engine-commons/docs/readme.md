x-engine 引擎说明文档
1. 环境JDK1.8，gradle 5.6+，protobuf3.5.1

	使用[gradle](http://www.gradle.org/) 管理ide项目文件以及打包
	
	- gradle eclipse # 导出eclipse项目
	- gradle idea # 导出idea项目
	- gradle build # 打包并进行findbugs分析

2. 工具说明
	- 项目导入工具 dependent.bat/dependent.sh 目前只支持eclipse和idea 根据需要修改脚本
	- 打包工具 pack.bat/pack.sh 打包目标文件目录 export/
	- 数据库entity生产工具在`com.xengine.entity.tool.DbEntityTools`
	- 数据库密码加密工具在`com.xengine.dbserver.tool.EncodePassword`
	- protobuf生产脚本在各项目的`tools/编译proto.bat tools/compile_proto.sh`
	
3.引擎结构
	-- frame 		引擎框架
	-- common 		常用、公用     依赖 frame
	-- entity       实体          依赖 frame
	-- dbserver 		数据同步服     依赖 frame
	-- roomserver 	房间服父级     依赖 frame common entity
	-- gate       	网关服        依赖 frame common entity
	-- x          	各子项目      依赖  roomserver

4.日志demo
//日志路径及命名
def LOG_HOME = "/Users/Max_liu/Documents/log/dbserver_log"
appender("stdout", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%d{yyyyMMdd-HH:mm:ss} %-5p %class{5}:%line %m%n"
	}
}

appender("fileLog", RollingFileAppender) {
	append = true
	threshold = INFO
	file = "${LOG_HOME}.log"
	encoding = "UTF-8"
	encoder(PatternLayoutEncoder) {
		pattern = "%d{yyyyMMdd-HH:mm:ss} [t] %-5p %class{5}:%line %m%n"
	}
	rollingPolicy(TimeBasedRollingPolicy) {
		fileNamePattern = "${LOG_HOME}_%d.log.zip"
		maxHistory = 30
	}
	
}

root(DEBUG, ["stdout", "fileLog"])
