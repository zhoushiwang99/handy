package com.finals.handy.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.Message;
import com.finals.handy.service.MessageService;
import com.finals.handy.util.JwtUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@ServerEndpoint(value = "/guest/websocket/{AccessToken}/{otherId}")
public class MyWebSocket {


    private static ApplicationContext applicationContext;

    private MessageService messageService;
    public static void setApplicationContext(ApplicationContext applicationContext){
        MyWebSocket.applicationContext = applicationContext;
    }


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private Integer userId;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private Integer getId(String AccessToken) {
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
        String id = claimMap.get("userId").asString();
        Integer userId = Integer.parseInt(id);
        return userId;
    }
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("AccessToken") String AccessToken, @PathParam("otherId") Integer otherId) {

        System.out.println(AccessToken);
        System.out.println(otherId);
       /* Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
        String id = claimMap.get("userId").asString();*/
        Integer userId=null;
        try {
           userId = getId(AccessToken);//Integer.parseInt(AccessToken);
        } catch (Exception e) {
            System.out.println("连接失败");
            return;
        }
        System.out.println(userId);
        this.session = session;
        this.userId = userId;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
//        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        messageService = applicationContext.getBean(MessageService.class);
//连接并且查询所有未读的消息
        List<Message> messages_noRead = messageService.findMessages_NoRead(otherId, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", messages_noRead);
        map.put("code", "0");
        String s = JSON.toJSONString(map);
        try {
            sendMessage(s);
        } catch (IOException e) {
            onError(session, e);
        }
//        设置为已读
        messageService.setHaveRead(otherId, userId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param text 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String text, Session session, @PathParam("otherId") Integer otherId) {
        System.out.println("来自客户端的消息:" + text);
        System.out.println(otherId);
        Integer toId = otherId;
        Message msg=messageService.SendMessage(userId, toId, text);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        String msgJson = JSON.toJSONString(map);
        try {
            this.sendMessage(msgJson);
        } catch (IOException e) {
            onError(session,e);
        }

        for (MyWebSocket item : webSocketSet) {

                if (item != this && item.userId.equals(toId)) {
                    try {
                        item.sendMessage(msgJson);
                    } catch (IOException e) {
                        onError(session,e);
                    }
                    messageService.setHaveRead(userId, item.userId);
                }

        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}