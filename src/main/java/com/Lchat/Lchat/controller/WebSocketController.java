package com.Lchat.Lchat.controller;

import com.Lchat.Lchat.dto.UserResponseDTO;
import com.Lchat.Lchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    // Handle user connection
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // Get user ID from session attributes (you'll set this when client connects)
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null && sessionAttributes.containsKey("userId")) {
            Long userId = (Long) sessionAttributes.get("userId");
            
            // Update user status to ONLINE
            userService.updateUserStatus(userId, com.Lchat.Lchat.model.ChatStatus.ONLINE);
            
            // Broadcast to all users that this user came online
            UserResponseDTO user = userService.getUserById(userId);
            messagingTemplate.convertAndSend("/topic/users", user);
        }
    }

    // Handle user disconnection
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // Get user ID from session attributes
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null && sessionAttributes.containsKey("userId")) {
            Long userId = (Long) sessionAttributes.get("userId");
            
            // Update user status to OFFLINE
            userService.updateUserStatus(userId, com.Lchat.Lchat.model.ChatStatus.OFFLINE);
            
            // Broadcast to all users that this user went offline
            UserResponseDTO user = userService.getUserById(userId);
            messagingTemplate.convertAndSend("/topic/users", user);
        }
    }
}