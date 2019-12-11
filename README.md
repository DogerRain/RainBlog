# Rain Blog


Rain Blog 是基于SpringBoot2.1.3 + LayUI 搭建的一个博客系统，是从[Tumo](https://github.com/TyCoding/tumo) fork 过来的项目，在原有的基础上增加了部分功能。

移动端：
![](http://forever.baimuxym.cn/20191211094455.jpg)

PC端：
![](http://forever.baimuxym.cn/20191210124914.png)


演示地址: [http://rainblog.baimuxym.cn/](http://rainblog.baimuxym.cn/)

后台管理：[http://rainblog.baimuxym.cn/system](http://rainblog.baimuxym.cn/system)


## 技术选型

### 写在前面

请按照以下流程运行项目：

1. 检查自己本地的开发环境是否与我的一致
2. 创建本地Mysql数据库：`tumo`，并导入项目目录下 `/db/rainblog_test.sql`
3. 保证Maven已经完全加载了项目所需的依赖
4. 运行项目下的：`cn.yudianxx.RainblogApplication.java`
5. 默认用户名和密码：`username: Rainer;   passsword: 123456`

### 后端

* 基础框架：Spring Boot 2.1.3.RELEASE

* 持久层框架：Mybatis-plus 2.1.0

* 安全框架：Shiro

* 模板引擎：Thymeleaf 3.0.11.RELEASE


### 前端

* 基础框架：LayUI

### 开发环境

* 语言： JDK1.8

* IDE： IDEA 2018.3

* 依赖管理： Maven

* 数据库： Mysql 5.7.24

###部署
* 设置了开发环境和生产两套环境，`application-dev.yml`和`application-pro.yml` 
* 若要部署到服务器，需要选择profile节点进行打包，maven 命令：
   ` clean install -Dmaven.test.skip=true -P pro`

## 联系我

- [CSDN:https://blog.csdn.net/yudianxiaoxiao](https://blog.csdn.net/yudianxiaoxiao)

- [GitHub:https://github.com/DogerRain](https://github.com/DogerRain)

- [知乎:https://www.zhihu.com/people/yu-dian-xiao-xiao-42](https://www.zhihu.com/people/yu-dian-xiao-xiao-42)

