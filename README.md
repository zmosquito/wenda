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

正如大多数网站发表言论都是需要经过审查的，而在我们的局域网里面，更是是要经过无数次的审查,然后方案有一下几种:

- 把敏感词删除
- 把敏感词替换为指定的字符串

这里并不是使用`replace()`方法或者使用正则表达式进行逐个查找然后进行替换,而是采用更加优良的算法-**前缀树**

**前缀树：**

- 根节点不包含字符,除根节点外每一个节点都只包含一个字符
- 从根节点到某一节点,路径上经过的字符连接起来,为该节点对应的字符串
- 每个节点的所有子节点包含的字符都不相同 

![tree](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/tree.png)



- 异步

在当今互联网这种上网人群多，访问量大的情况下，异步编程提供了一个非堵塞的，基于事件驱动的编程模型。这种编程方式可以充分利用计算机的多核来同时执行并行任务，提高资源的利用效率。应用场景在我了解的也不多，可以想象应用场景是很广泛的。举个很简单的例子，我们在网站注册新的社交账号的话，严格一点的网站都是会有验证邮箱地址的邮件。这个就是一个异步的事件，你不可能说直接把邮件验证的流程嵌套在业务逻辑里面。应该是把这个事件发送给一个队列里面，然后队列专门处理这种事件。在一个网站中有各种各样的事情需要进行处理。

简单设计流程:

1. 首先定义一个定义一个枚举类型的`EventType`类，这里面列举各种各样将会出现的事件；
2. 再定义一个事件模型`EventModel`类，里面包含的是一个事件所应该具有的一些属性。例如事件类型，操作者的ID，操作的实体类型，操作的实体ID，操作的实体拥有者等等；
3. 再定义一个接口`EventHandler`，里面写上几个抽象的方法，具体的实现由继承的类进行实现；
4. 定义一个事件生产者`EventProducer，`这里就是用来生产各种各样的事件，如异常登录或者是注册邮件等等。本项目中这里就是把事件送到Redis中进行储存；
5. 定义一个事件消费者`EventConsumer，`这里就是需要继承`InitializingBean`跟`ApplicationContextAware`。继承`InitializingBean`是可以定义bean的初始化方式，继承`InitializingBean`是为了可以通过这个上下文对象得到我们想获取的bean。然后在这个类里面使用多线程一直去Redis里面读取出事件，然后进行处理；
6. 再继承`EventHandler`在方法里面写自己的实现；

按照步骤来的代码示例:

- `EventType`类

```java
public enum EventType {

    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
```

- `EventModel`类

```java
public class EventModel {

    private EventType type;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;

    private Map<String, String> exts = new HashMap<>();

    public EventModel() {
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
```

- `EventHandler`接口

```java
public interface EventHandler {

    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
```

- `EventProducer`类

```java
public class EventProducer {

    private final JedisAdapter jedisAdapter;

    @Autowired
    public EventProducer(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;
    }

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

- `EventConsumer`类

```java
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    private final JedisAdapter jedisAdapter;

    @Autowired
    public EventConsumer(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();

                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);

                    for (String message : events) {
                        if (message.equals(key)) {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }

                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }

                    }
                }
            }
        });

        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```

- `*Handler`继承类

```java
public class LikeHandler implements EventHandler {

    private final MessageService messageService;

    private final UserService userService;

    @Autowired
    public LikeHandler(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreateDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        // 这里的会话ID肯定是系统管理员跟通知用户之间的会话
        message.setConversationId(WendaUtil.SYSTEM_USERID + "_" + model.getEntityOwnerId());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
```



- 邮件发送

邮件发送倒也是第一次接触，好像以前看 Java 老师的是有提到过，不过当时也不会写。这里还是引用的是官方的`javax.mail`包，然后是通过`Spring`进行调用，这样可以减少一些邮件发送的复杂度，毕竟很多时候这种固定套路的代码都是复制过来用的。

流程: 

- 定义一个发送邮件的配置文件，里面写好相关的配置，包括服务器信息、邮件协议、账号信息等等。
- 在 bean 中写一个方法`sendWithHTMLTemplate()`用来发送邮件的，因为不可能每一个邮件都是一样的，这里是采用了`Velocity`进行生成邮件文本的。
- 在需要调用发送邮件的地方调用`sendWithHTMLTemplate()`发送邮件。

示例代码：

- Redis事务

以前只知道在关系型数据库里面是有事务的概念的，没想到在 Redis 中也是有事务的概念的.很多时候一些重要的操作如果没有事务进行控制的话,会造成很多不必要的损失。

在Redis事物要用到的两个命令:

- MULTI命令

  首先在事物开始的时候,先发送`MULTI`命令给Redis,表示开始事物,然后再进行一些数据的操作.

- EXEC命令 在操作全部完成后发送`EXEC`命令告诉Redis事物已经结束了,可以执行了

在事务中间执行的命令都是已经保存在Reids中，但是没有被执行，直到收到`EXEC`命令，然后才会以此开始以此执行服务器中保存的命令。当中只要有一次命令执行失败了的话，其他的指定命令也会被撤销。执行原则就是“要么全部被执行成功，要么全部不执行”。

在`Java`中使用`Jedis`操作事务:

```java
try {
    Transaction tx = jedis.multi();
    tx.zadd("qq", 2, "1");
    tx.zadd("qq2", 3, "2");
    List<Object> objs = tx.exec();
    tx.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {}
```



- timeline

顾名思义就是时间轴的意思，在sns网站中一般是要有互动的，然后就会有各种各样的个人时间轴时间或者是新鲜事。在大量用户会产生大量数据的情况下，对数据的储存我们需要合理地分配储存位置以及储存方式，让数据库性能最优化。

数据储存方案:

- 高热点数据放到Redis之类的非关系型数据库,便于高速读取
- 用户言论等数据储存到关系型数据库,便于持久储存,安全性相对更高

![timeline](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/timeline.png)

消息推送方案:

- 这里我们采用推拉的模式,活跃用户直接推送消息,不活跃用户采用拉取的方式,合理利用服务器资源

![PushOrPull](https://blogs-image.oss-cn-beijing.aliyuncs.com/wenda/PushOrPull.png)

- 爬虫

这里数据的读取采用 Python 进行爬取，主要是 Python 做一件事的代码量，前提是抛开别的因素。当我写第一个简单爬虫代码的时候我都惊讶到了，操作数据库短短两三行代码。非常的简洁，又有点自然语言的特点。采用的是`Pyspider`框架，包管理器使用`pip`,`IDE`用`IDEA`的兄弟`Pycharm`。

- Solr全文搜索



- 压力测试

这里介绍的是一个阿帕奇下的一个开源工具`apache2-utils`,因为我是在`Ubuntu`系统下,直接从终端安装就好了

```
sudo apt-get install apache2-utils
```

然后直接在终端里面就可以打开，输入命令`ab `后面带参数`ab [options] [http[s]://]hostname[:port]/path` 就可以开始进行测试了，你可以随便输入下，然后就会有帮助出来，跟着帮助做做例子就知道怎么用了，不过这里需要注意的是网址的填写，填写简短的网址的话肯定运行不了的，网址最后面那个要带到来。