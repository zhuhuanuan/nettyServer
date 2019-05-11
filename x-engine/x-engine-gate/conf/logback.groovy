appender("stdout", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%d{yyyyMMdd-HH:mm:ss} %-5p %class{5}:%line %m%n"
	}
}

appender("fileLog", RollingFileAppender) {
	append = true
	threshold = INFO
	file = "log/info.log"
	encoding = "UTF-8"
	
	encoder(PatternLayoutEncoder) {
		pattern = "%d{yyyyMMdd-HH:mm:ss} [t] %-5p %class{5}:%line %m%n"
	}
	rollingPolicy(FixedWindowRollingPolicy) {
		fileNamePattern = "log/info.%i.log.zip"
		minIndex = 1
		maxIndex = 10
	}
	triggeringPolicy(SizeBasedTriggeringPolicy) {
		maxFileSize = "100MB"
	}
}

root(DEBUG, ["stdout", "fileLog"])
