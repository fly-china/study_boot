package com.lpf.spring.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket的服务端,相当于一个ws协议的Controller
 *
 * @author lipengfei
 * @create 2019-04-17 11:14
 **/
@ServerEndpoint("/websocket/{platformType}/{userId}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;

    //接收参数中的用户ID
    public Long userId;

    //接收用户中的平台类型
    public Integer platformType;


    /**
     * 连接建立成功调用的方法
     * 接收url中的参数
     * 每个对象有着各自的 session，其中可以存放个人信息。当收到一个客户端消息时，往所有维护着的对象循环 send 了消息，这就简单实现了聊天室的聊天功能了。
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("platformType") Integer platformType, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
        this.platformType = platformType;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1

        log.info("有新连接加入！当前在线人数为" + getOnlineCount() + "  userId==== " + userId + "  platformType==== " + platformType);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        log.info("来自uid={}客户端的消息:{}", this.userId, message);

        // 针对接受的消息做个性化处理，这里会设计约定的协议，消息的格式
        // 此处定义接受消息格式：{toUid}|{toMsg}, 对toUid定向发送toMsg消息
        String[] msgs = message.split("\\|");
        if (msgs != null && msgs.length > 1) {
            // 私聊发消息
            Long toUid = Long.valueOf(msgs[0]);
            String sendMsg = "用户" + this.userId + "和你说：" + msgs[1];
            log.info("私聊发送消息，{}向{}发送一条消息", this.userId, toUid);
            try {
                sendInfo(Long.valueOf(toUid), sendMsg);
                log.info("消息发送成功");
            } catch (IOException e) {
                log.error("消息发送失败，原因：", e);
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误,原因：", error);
    }


    /**
     * websocket的session 发送文本消息有两个方法：getAsyncRemote()和 getBasicRemote()。
     * getAsyncRemote 是非阻塞式的，getBasicRemote 是阻塞式的。
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 私发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(Long userId, String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                if (item.userId.equals(userId)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfos(String message) throws IOException {
        log.info("群发自定义消息:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.getAndDecrement();
    }
}
