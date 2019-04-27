# wenda



## 一.项目环境

- IDE：IDEA　&&　Pycharm
- JDK Version：JDK1.8
- Python Version：Python 3.0
- Web容器：SpringBoot集成的tomcat
- 数据库：Mysql8.0  &&  redis
- 依赖管理：Maven
- 版本控制：git

## 二.项目运行

- 下载

`Download Zip`或者 `git clone`

`git clone https://github.com/zmosquito/wenda.git                                                                           `

- 导入IDE

使用`IDEA`创建的项目,配置好`maven`的相关配置,以及项目`JDK`版本,直接在主界面选择`Open`,然后找到项目所在路径,点击导入就可以了

- 邮件发送配置

在[MailSender](https://github.com/zmosquito/wenda/blob/master/src/main/java/com/mosquito/util/MailSender.java)下，更改mail配置

![Mail](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/wenda-mail.png)

- 数据库建立

数据库文件全部位于[resources/sql](<https://github.com/zmosquito/wenda/blob/master/src/test/resources/init-schema.sql>)目录下面,导入数据库后可以自行填充数据

- 数据库连接

因为我是用的MySQL8.0，所以数据库链接的时候要将[resources/application.properties](https://github.com/zmosquito/wenda/blob/master/src/main/resources/application.properties)下的数据库连接配置，自行修改

```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/wenda?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT
spring.datasource.username=root
spring.datasource.password=
```

## 三.项目经验总结

- Velocity模板引擎

这个模板引擎还是第一次接触，我的理解就是大多数模板引擎的出现是为了使用户界面与业务数据进行分离，因为jsp在用户访问量大的时候容易出现解析时间久，用户体验性不好等等原因，使用起来其实还是挺方便的，至于性能的话我也没有去测试。

- Spring Boot

貌似最近`Spring Boot`挺火的，还跟微服务、SpringCould等高大上名词的扯上了关系。总结起来就是Spring的发展导致便携性非常高,然后各种配置文件很繁琐，于是又回到了注解的开发，大幅度减少无关于业务的代码量。这个倒确实有点体会，各种Spring的配置文件手动编写起来很是麻烦。总结出特点就是：

1. 创建独立的Spring应用程序
2. 嵌入的Tomcat，无需部署WAR文件
3. 简化Maven配置
4. 自动配置Spring
5. 提供生产就绪型功能，如指标，健康检查和外部配置
6. 绝对没有代码生成和对XML没有要求配置

在IDEA中生成一个SpringBoot工程还是挺方便的,填入跟选择相对应的值直接一步步就可以了 

![springbootProject](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/springbootProject.png)

![projectProperties](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/projectProperties.png)

![chooseDependencies](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/chooseDependencies.png)

也可以选择官网的快速生成，然后导入IDEA就OK了

[传送门](https://start.spring.io/)

![createrWebSpringboot](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/createrWebSpringboot.png)

- 登录与注册

引入了`ticket`的概念，在用户登陆之后，后台会自动产生一个ticket，然后放入数据库中，`ticket`是具有时效性的，通过保存的过期时间与当前服务器时间进行对比，如果过期了，就需要重新登陆，如果没有过期就会直接是登录状态。这里的`ticket`在服务器发送给客户端后保存在`Cookie`中

在进行登陆注册的时候保存当前用户访问的网址，在登陆或注册完成后直接跳回操作的网址，提升用户的体验。

- 敏感词过滤
- 异步
- 邮件发送
- Redis事务
- 排序算法
- timeline
- 爬虫
- Solr全文搜索
- 压力测试