// src/main/java/com/Lchat/Lchat/config/UserHandshakeHandler.java
package com.Lchat.Lchat.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class UserHandshakeHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
            message, StompHeaderAccessor.class
        );

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getLogin(); // gets the 'login' header we set in JS
            if (userId != null && !userId.isEmpty()) {
                accessor.setUser(new StompPrincipal(userId));
                System.out.println("STOMP user set: " + userId);
            }
        }

        return message;
    }
}