//package cn.stt.websocket.ex3;
//
//import cn.bblink.wema.platform.base.util.Constants;
//import cn.bblink.wema.platform.user.handler.ChatHistoryHandler;
//import cn.bblink.wema.platform.util.BUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
///**
// * Socket处理器
// *
// * @author Goofy
// * @Date 2015年6月11日 下午1:19:50
// */
//@Component
//public class MyWebSocketHandler implements WebSocketHandler {
//    Logger                                            log = LoggerFactory.getLogger(MyWebSocketHandler.class);
//    public static final Map<String, WebSocketSession> userSocketSessionMap;
//    public static final Map<String, String>           users;
//
//    private String                                    uid;
//
//    static {
//        userSocketSessionMap = new HashMap<String, WebSocketSession>();
//        users = new HashMap<String, String>();
//    }
//
//    /**
//     * 建立连接后
//     */
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String query = session.getUri().getQuery();
//        String sessionId = session.getId();
//        Map<String, Object> map = BUtil.getUrlParams(query);
//        Object objUid = map.get(Constants.SESSION_USER_ID);
//        if (null != objUid) {
//            this.uid = String.valueOf(objUid);
//            userSocketSessionMap.put(this.uid, session);
//        }
//        System.err.println("Connection allowed uid={" + this.uid + " },sessionId={" + sessionId + "},sessionSize={" + userSocketSessionMap.values().size() + "}");
//    }
//
//    /**
//     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
//     */
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        if (message.getPayloadLength() == 0)
//            return;
//        Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
//        msg.setDate(new Date());
//        sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
//        // 聊天记录保存
////        chatHistoryHandler.saveChatHistory(msg);
//    }
//
//    /**
//     * 消息传输错误处理
//     */
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        if (session.isOpen()) {
//            session.close();
//        }
//        // System.err.println("handleTransportError");
//        removeSession(session.getId());
//    }
//
//    /**
//     * 关闭连接后
//     */
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        removeSession(session.getId());
//        System.err.println("afterConnectionClosed = " + session.getId() + " closeStatus= " + closeStatus.getReason() + ",code=" + closeStatus.getCode());
//    }
//
//    private void removeSession(String sessionId) {
//        // String uid = null;
//        try {
//            // Iterator<Entry<String, String>> it = users.entrySet().iterator();
//            // while (it.hasNext()) {
//            // Entry<String, String> entry = it.next();
//            // if (entry.getValue().equals(sessionId)) {
//            // uid = entry.getKey();
//            // users.remove(uid);
//            // break;
//            // }
//            // }
//            WebSocketSession webSocketSession = userSocketSessionMap.remove(this.uid);
//            if (null != webSocketSession && webSocketSession.isOpen()) {
//                webSocketSession.close();
//            }
//            System.err.println("uid=" + this.uid + ",sessionId=" + sessionId + ",sessionSize=" + userSocketSessionMap.values().size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    /**
//     * 给所有在线用户发送消息
//     *
//     * @param message
//     * @throws IOException
//     */
//    public void broadcast(final TextMessage message) throws IOException {
//        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
//
//        // 多线程群发
//        while (it.hasNext()) {
//
//            final Entry<String, WebSocketSession> entry = it.next();
//
//            if (entry.getValue().isOpen()) {
//                // entry.getValue().sendMessage(message);
//                new Thread(new Runnable() {
//
//                    public void run() {
//                        try {
//                            if (entry.getValue().isOpen()) {
//                                entry.getValue().sendMessage(message);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }).start();
//            }
//
//        }
//    }
//
//    /**
//     * 给某个用户发送消息
//     *
//     * @param userName
//     * @param message
//     * @throws IOException
//     */
//    public void sendMessageToUser(String uid, TextMessage message) throws IOException {
//        // String sessionId = users.get(uid);
//        WebSocketSession session = userSocketSessionMap.get(uid);
//        System.err.println("send msg " + userSocketSessionMap.keySet() + "/" + JSONObject.toJSONString(message));
//        if (session != null && session.isOpen()) {
//            session.sendMessage(message);
//        }
//    }
//
//}
