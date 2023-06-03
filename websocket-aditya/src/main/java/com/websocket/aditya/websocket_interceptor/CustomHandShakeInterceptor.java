package com.websocket.aditya.websocket_interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.OriginHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CustomHandShakeInterceptor extends OriginHandshakeInterceptor {




    // if we want to perform some task before a two way connection then do this
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(true) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
          //  return false;
        }
        wsHandler.afterConnectionClosed(null, CloseStatus.NO_STATUS_CODE);

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String userName  = httpServletRequest.getParameter("userName");
//        String token = httpServletRequest.getParameter("token");
//        String sessionId = httpServletRequest.getHeader("Sec-WebSocket-Key");
//        System.out.println(" before intercept ");

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
