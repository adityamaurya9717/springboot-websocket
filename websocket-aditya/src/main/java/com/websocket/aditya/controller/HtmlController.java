package com.websocket.aditya.controller;

import com.websocket.aditya.Model.Greeting;
import com.websocket.aditya.Model.HelloMessage;
import com.websocket.aditya.config.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class HtmlController {

    @Autowired
    WebSocketConfig webSocketConfig;


    @GetMapping("/welcome")
    public String greeting() {
        return "socket.html";
    }
    @GetMapping("/socket")
    public String socket() {
        return "socket.html";
    }

    @GetMapping("/another")
    public String another() {
        return "another.html";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {
        //System.out.println(webSocketMessageBrokerStats);
        Thread.sleep(1000);
        return new Greeting("Hello, " + "From server "+ message.getName());
    }



}
