package com.websocket.aditya.controller;


import com.websocket.aditya.config.CustomeSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@RestController
@RequestMapping
public class WelcomeController {

    @Autowired
    CustomeSocketHandler webSocketHandler;
    @GetMapping
    public String ping(){
        return "ping pong";
    }

    @GetMapping("sendtouser")
    public void sendToUser(@RequestParam("useremail") String email,@RequestParam("message") String message) throws IOException {
        Map<String, WebSocketSession> map   = webSocketHandler.getUserEmailMap();
        // if user is inactive then store in queue
        if( !map.containsKey(email) ){
             Map<String, Queue<String> > messageMap = webSocketHandler.getUsermessages();
             Queue<String> queue = messageMap.get(email)!=null?messageMap.get(email):new LinkedList<>();
             queue.add(message);
             messageMap.put(email,queue);
        }
        else{
            TextMessage textMessage = new TextMessage(message);
            map.get(email).sendMessage(textMessage);
        }

    }
}
