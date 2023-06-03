package com.websocket.aditya.config;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class CustomeSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

    public final List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
    // this map stored userEmail as key and websocketSession as value
    public final HashMap<String,WebSocketSession> userEmailMap = new HashMap<>();



    /*this store messages of users in Queue if user is not active and then send it after user Connected
       where user email is key and message is value
    */
    public final HashMap<String,Queue<String>> usermessages = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        if(validatingWebSocketConnection(session)==false){
            return;
        }
        System.out.println(" handler user connected");
        Map<String,String> userDetails = new HashMap<>();
        String email = session.getHandshakeHeaders().get("useremail").get(0);
        userEmailMap.put(email,session);
        webSocketSessions.add(session);
        TextMessage textMessage = new TextMessage("user Connected");
        session.sendMessage(textMessage);
        sendPreviousMessageOfUser(email);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(" handler user closed");
        String email = session.getHandshakeHeaders().get("useremail").get(0);
        userEmailMap.remove(email);
        webSocketSessions.remove(session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("message Handle");
        super.handleMessage(session, message);
        for (WebSocketSession webSocketSession : webSocketSessions) {
            System.out.println("send to Server");
            webSocketSession.sendMessage(  message);
        }
    }
    @Override
    public List<String> getSubProtocols() {
        return null;
    }

    private boolean validatingWebSocketConnection(WebSocketSession session) throws IOException {
       // for token
       String token = null;
       if( session.getHandshakeHeaders().get("token")==null ){
           session.close(new CloseStatus(1003,"token Not Found in Header"));
           return false;
       }
        if( session.getHandshakeHeaders().get("useremail")==null || session.getHandshakeHeaders().get("useremail").size()==0 ){
            session.close(new CloseStatus(1004,"useremail Not Found in Header"));
            return false;
        }
      return true;
    }
    private void sendPreviousMessageOfUser(String email){
        WebSocketSession session  = userEmailMap.get(email);
        if( usermessages.get(email)!=null){
            Queue<String> queue =  usermessages.get(email);
            while(!queue.isEmpty()){
                try {
                    session.sendMessage(new TextMessage(queue.poll()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public List<WebSocketSession> getWebSocketSessions() {
        return webSocketSessions;
    }

    public HashMap<String, WebSocketSession> getUserEmailMap() {
        return userEmailMap;
    }

    public HashMap<String, Queue<String>> getUsermessages() {
        return usermessages;
    }
}
