#jfinal-rest
jfinal-rest是jfinal的轻量级RESTful扩展，使用非常方便，看了DEMO就可以用了。

Config示例代码：

```java
public class Config extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        //设置默认渲染json
        me.setMainRenderFactory(new IMainRenderFactory() {
            @Override
            public Render getRender(String view) {
                return new JsonRender();
            }

            @Override
            public String getViewExtension() {
                return null;
            }
        });

    }

    @Override
    public void configRoute(Routes me) {
        //配置路由，三个参数：访问路径（API版本），jfinal路由对象，要扫描的包(包下加了API注解的controller会被扫描)
        RestKit.buildRoutes("/v1", me, "peak.v1");
    }

    @Override
    public void configPlugin(Plugins me) {
        //TODO 配置数据库等插件

    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        //配置handler
        RestKit.buildHandler(me);
    }
}
```

Controller示例代码：

```java

@API("/tickets/:ticketId/messages")
public class MessageController extends Controller {
    /**
     * 获取单个数据或者列表
     */
    public void get() {
        //路径里定义的参数变量，需要通过getAttr()方法获取
        String ticketId = getAttr("ticketId");
        String messageId = getPara();
        //GET /v1/tickets/xxxx/messages
        if (StrKit.isBlank(messageId)) {
            Page<Message> page = null;
            //TODO 分页查询message
            setAttr("error", 0);
            setAttr("data", page);
            return;
        }
        //GET /v1/tickets/xxxx/messages/xxxx
        Message message = null;
        //TODO 查询单个message数据
        setAttr("error", 0);
        setAttr("data", message);
    }

    /**
     * 添加新数据
     */
    public void post() {
        //POST /v1/tickets/xxxx/messages
        String ticketId = getAttr("ticketId");
        Message message = getModel(Message.class);
        message.set("ticketId", ticketId);
        message.save();
        setAttr("error", 0);
        setAttr("id", message.getInt("id"));
    }

    /**
     * 部分更新数据
     */
    public void patch() {
        //PATCH /v1/tickets/xxxx/messages/xxxxx
        String messageId = getPara();
        Message message = Message.dao.findById(messageId);
        //TODO 为message设置各个要更新的属性
        message.update();
        setAttr("error", 0);
    }

    /**
     * 数据整体更新
     */
    public void put() {
        //PUT /v1/tickets/xxxx/messages/xxxxx
        int messageId = getParaToInt();
        Message message = getModel(Message.class);
        message.set("id", messageId);
        message.update();
        setAttr("error", 0);
    }

    /**
     * 删除数据
     */
    public void delete() {
        //DELETE /v1/tickets/xxxx/messages/xxxxx
        String messageId = getPara();
        Message message = Message.dao.findById(messageId);
        message.delete();
        setAttr("error", 0);
    }
}

```