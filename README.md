# RobotDemoEngine
这个工程会举例所有demo,这里面已经列举了一个比较可用的发送美女 自动发送美女图片的插件源码.
### 介绍
picplugin引用了目前最新的机器人sdk插件,所以也需要最新版Q++插件1.0.2以及以上支持,需要QQ机器人1.7.0.2以及以上版本支持.
如果手机环境特殊,各位朋友可以直接借助win/mac电脑 安装夜神模拟器+xposed 1.2.4插件 +最新版QQ+Q++插件+qq机器人 完成环境的搭建,而本项目只是机器人的插件.
picplugin模块的功能是让用户发送美女 就能自动发送一个美女图片,原理是从sdcard卡存放的路径中随机取出一个图片进行发送.

### 生成插件和测试步骤

1 .菜单栏 点击build->Build apk(s)
[记得打开build.gradle修改一下包名,避免冲突]

2 .通过 adb push 推送到手机/sdcard/robot_plugin/目录下

如果没有修改项目名默认的命令如下
点击android studio 下面的终端窗口 粘贴如下命令
```adb push app/build/outputs/apk/debug/app-debug.apk /sdcard/qssq666/robot_plugin/see_pic.apk```
3. 打开情迁QQ机器人,进入插件管理中心,点击插件

4. 如果使用Q++插件启用勒插件化加载,则可以从插件中心进入管理插件,不过目前还是非插件式加载最稳定,就算机器人崩溃也不会影响到宿主(QQ).

5. 生效需要刷新列表加载


### 其它问题
可以直接通过lib库build arr然后提取jar文件然后 转换为dex.
然后输入
```adb push picplugin/build/outputs/aar/picplugin-release/classes.jar /sdcard/qssq666/robot_plugin/see_pic.jar```

就可以从机器人软件里面看到的,但是现在的问题是找不到 Class,这个问题目前无法得知原因.所以暂时先用 apk或者apk里面的dex作为加载吧,本来我是想实现dex,jar,apk都兼容的..

### 如何写逻辑
源码中对 ```   api project(':picplugin')```进行了依赖，可以看到设置了固定入口类
```cn.qssq666.robot.plugin.sdk.control.PluginMainImpl```