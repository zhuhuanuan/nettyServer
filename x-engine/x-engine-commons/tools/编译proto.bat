@echo off & setlocal EnableDelayedExpansion
cd %~dp0
for /f "delims=" %%i in ('"dir /s/B ..\proto\*.proto"') do (
	set file=%%~nxi
	if !file! neq protoSystem.proto (
		echo !file!
		set file=../proto/!file!
		echo !file!
		protoc --java_out=../src --proto_path=../proto/ !file!
		)
)
PAUSE