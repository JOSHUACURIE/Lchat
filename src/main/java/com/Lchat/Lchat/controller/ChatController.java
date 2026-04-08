package com.Lchat.Lchat.controller;

import com.Lchat.Lchat.dto.ChatMessageDTO;
import com.Lchat.Lchat.dto.MessageResponseDTO;
import com.Lchat.Lchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Send private message to specific user
    @MessageMapping("/chat.send")
    public void sendPrivateMessage(@Payload ChatMessageDTO chatMessage) {
        // Save message to database
        MessageResponseDTO savedMessage = chatService.sendMessage(chatMessage);
        
        // Send to receiver's personal queue
        messagingTemplate.convertAndSendToUser(
            chatMessage.getReceiverId().toString(),
            "/queue/messages",
            savedMessage
        );
        
        // Also send confirmation back to sender
        messagingTemplate.convertAndSendToUser(
            chatMessage.getSenderId().toString(),
            "/queue/messages",
            savedMessage
        );
    }

    // Send message to public topic (broadcast to all users)
    @MessageMapping("/chat.broadcast")
    @SendTo("/topic/public")
    public MessageResponseDTO broadcastMessage(@Payload ChatMessageDTO chatMessage) {
        // Save message to database
        MessageResponseDTO savedMessage = chatService.sendMessage(chatMessage);
        return savedMessage;
    }

    // Handle typing indicator
    @MessageMapping("/chat.typing")
    public void typingIndicator(@Payload ChatMessageDTO typingMessage) {
        // Send typing status to receiver
        messagingTemplate.convertAndSendToUser(
            typingMessage.getReceiverId().toString(),
            "/queue/typing",
            typingMessage.getSenderId() + " is typing..."
        );
    }

    // Mark messages as read
    @MessageMapping("/chat.read")
    public void markAsRead(@Payload ChatMessageDTO readReceipt) {
        chatService.markMessagesAsRead(
            readReceipt.getReceiverId(), 
            readReceipt.getSenderId()
        );
    }
}