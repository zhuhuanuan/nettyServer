#!/usr/bin/env python
# !-*- coding:utf8 -*-
# 注：把脚本放在游戏项目根目录下运行 ,运行此脚本时，请确保run.py 存在且放在游戏根目录
# author:Liujinxin
# date :2016/05/28
import sys
import os
import commands
import readline
import rlcompleter
import time

readline.parse_and_bind('tab:complete')
histfile = os.path.join(os.environ['HOME'], '.pythonhistory')

help = "Example:\npython + %s + start/stop/restart + servername " % (sys.argv[0])


def checkdirs(path):
    '''
    遍历指定目录下的当层所有的目录（不包括文件,不递归）
    '''
    filesAndDirs = os.listdir(path)
    dirs = [x for x in filesAndDirs if os.path.isdir(path + os.sep + x)]
    return dirs


def offstatuscheck(sname,subname):
    status, output = commands.getstatusoutput('pwd')
    rootdir = output
    while True:
        status1, output1 = commands.getstatusoutput('cat %s/status.txt' % (rootdir + '/' + sname + '/' + subname))
        if 'off' in output1:
            print "\033[1;32m %s %s stop sucess\033[0m\033[5;32m...\033[0m" % (sname,subname)
            time.sleep(1)
            os.system('screen -S %s -p bash -X title %s' % (sname + subname.capitalize(), sname + subname.capitalize()))
            time.sleep(1)
            os.system('screen -S %s -p 0 -X stuff $"exit\r"' % (sname + subname.capitalize()))
            time.sleep(1)
            break
        else:
            print "\033[1;32m %s %s is closing,please wait\033[0m\033[5;32m...\033[0m" % (sname,subname)
            time.sleep(5)


def onstatuscheck(sname,subname):
    status, output = commands.getstatusoutput('pwd')
    rootdir = output
    while True:
        status1, output1 = commands.getstatusoutput('cat %s/status.txt' % (rootdir + '/' + sname + '/' + subname))
        if 'on' in output1:
            print "\033[1;32m %s %s start sucess\033[0m\033[5;32m...\033[0m" % (sname,subname)
            time.sleep(1)
            break
        else:
            print "\033[1;32m %s %s is starting,please wait\033[0m\033[5;32m...\033[0m" % (sname,subname)
            time.sleep(5)


def startserver(sname):
    status, output = commands.getstatusoutput('screen -ls| grep %s' % (sname))
    if status == 0:
        print "\033[1;32m The screen %s is exits,is running\033[0m\033[5;32m...\033[0m" % (sname)
    else:
        status, output = commands.getstatusoutput('pwd')
        rootdir = output
        print "\033[1;32m ProgramPath is %s\033[0m\033[5;32m...\033[0m" % (rootdir)
        dirlist = checkdirs(rootdir)
        if sname not in dirlist:
            print "\033[1;32m Error!!!%s Path %s not found.\033[0m" % (sname, rootdir + '/' + sname)
        for dir in dirlist:
            if dir == sname:
                print "\033[1;32m %s Path %s\033[0m\033[5;32m...\033[0m" % (sname, rootdir + '/' + dir)
                os.system("screen -dmS %sRoom" % (sname))
                time.sleep(1)
                os.system('screen -S %sRoom -p bash -X title %sRoom' % (sname, sname))
                time.sleep(1)
                os.system('screen -S %sRoom -p 0 -X stuff $"cd %s/room/\r"' % (sname, dir))
                os.system('screen -S %sRoom -p 0 -X stuff $"./start.sh\r"' % (sname))
                onstatuscheck(sname,'room')
                time.sleep(1)

                os.system("screen -dmS %sGate" % (sname))
                time.sleep(1)
                os.system('screen -S %sGate -p bash -X title %sGate' % (sname, sname))
                time.sleep(1)
                os.system('screen -S %sGate -p 0 -X stuff $"cd %s/gate/\r"' % (sname, dir))
                os.system('screen -S %sGate -p 0 -X stuff $"./start.sh\r"' % (sname))
                onstatuscheck(sname,'gate')
                time.sleep(1)

def stopserver(sname):
    status, output = commands.getstatusoutput('screen -ls| grep %s' % (sname+'Gate'))
    status1, output1 = commands.getstatusoutput('screen -ls| grep %s' % (sname+'Room'))
    if status == 0 and status1 == 0:
        status, output = commands.getstatusoutput('pwd')
        rootdir = output
        print "\033[1;32m ProgarmPath is %s\033[0m\033[5;32m...\033[0m" % (rootdir)
        dirlist = checkdirs(rootdir)
        if sname not in dirlist:
            print "\033[1;32m Error!!!%s Path %s not found.\033[0m" % (sname, rootdir + '/' + sname)
        else:
            print "\033[1;32m %s path is %s\033[0m\033[5;32m...\033[0m" % (sname, rootdir + '/' + sname)
            
            os.system('screen -S %sRoom -p bash -X title %sRoom' % (sname, sname))
            os.system('screen -S %sRoom -p 0 -X stuff $"stop\r"' % (sname))
            time.sleep(1)
            offstatuscheck(sname,'room')
            time.sleep(1)
            
            os.system('screen -S %sGate -p bash -X title %sGate' % (sname, sname))
            os.system('screen -S %sGate -p 0 -X stuff $"stop\r"' % (sname))
            time.sleep(1)
            offstatuscheck(sname,'gate')
            time.sleep(1)
    else:
        print "\033[1;32m Ths screen %s or %s is not exits, no running\033[0m\033[5;32m...\033[0m" % (sname + 'Gate',sname + 'Room')


if __name__ == "__main__":
    if len(sys.argv) == 2:
        status4, output4 = commands.getstatusoutput('pwd')
        rootdir4 = output4
        print "\033[1;32m ProgramPath is %s\033[0m\033[5;32m...\033[0m" % (rootdir4)
        dirlist4 = checkdirs(rootdir4)
        if sys.argv[1] == 'allstart':
            for dir in dirlist4:
                print '\033[1;32m开始维护更新 ,启动服务 %s\033[0m\033[5;32m...\033[0m' % (dir)
                startserver(dir)
                print '\033[1;32m启动%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (dir)
            sys.exit()   
        elif sys.argv[1] == 'allstop':
            for dir in dirlist4:
                print '\033[1;32m停止 %s\033[0m\033[5;32m...\033[0m' % (dir)
                stopserver(dir)
                print '\033[1;32m停止%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (dir)
            sys.exit()
        elif sys.argv[1] == 'allrestart':
            for dir in dirlist4:
                print '\033[1;32m停止 %s\033[0m\033[5;32m...\033[0m' % (dir)
                stopserver(dir)
                print '\033[1;32m停止%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (dir)
            time.sleep(3)   
            for dir in dirlist4:
                print '\033[1;32m开始维护更新 ,启动服务 %s\033[0m\033[5;32m...\033[0m' % (dir)
                startserver(dir)
                print '\033[1;32m启动%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (dir)
            time.sleep(1)
            sys.exit()
        else:
            print help
        
    if len(sys.argv) < 3:
        print help
        sys.exit()
    if len(sys.argv) == 3:
        if sys.argv[1] == 'start':
            print '\033[1;32m启动 %s\033[0m\033[5;32m...\033[0m' % (sys.argv[2])
            startserver(sys.argv[2])
            print '\033[1;32m启动%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (sys.argv[2])
        elif sys.argv[1] == 'stop':
            print '\033[1;32m停止 %s\033[0m\033[5;32m...\033[0m' % (sys.argv[2])
            stopserver(sys.argv[2])
            print '\033[1;32m停止%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (sys.argv[2])
        elif sys.argv[1] == 'restart':
            print '\033[1;32m开始维护更新 ,停止服务 %s\033[0m\033[5;32m...\033[0m' % (sys.argv[2])
            stopserver(sys.argv[2])
            time.sleep(3)
            print '\033[1;32m开始维护更新 ,启动服务 %s\033[0m\033[5;32m...\033[0m' % (sys.argv[2])
            startserver(sys.argv[2])
            print '\033[1;32m维护更新%s服务 ,完成\033[0m\033[5;32m！！！........\033[0m' % (sys.argv[2])
            time.sleep(3)
        else:
            print help
    else:
        print help

    # time.sleep(1)
    # sys.exit()
