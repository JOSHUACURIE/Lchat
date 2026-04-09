package com.Lchat.Lchat.controller;

import com.Lchat.Lchat.dto.MessageResponseDTO;
import com.Lchat.Lchat.model.User;
import com.Lchat.Lchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private ChatService chatService;

    // Get conversation between two users
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageResponseDTO>> getConversation(
            @RequestParam Long userId1,
            @RequestParam Long userId2) {
        List<MessageResponseDTO> conversation = chatService.getConversation(userId1, userId2);
        return ResponseEntity.ok(conversation);
    }

    // Get unread messages for a user
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<MessageResponseDTO>> getUnreadMessages(@PathVariable Long userId) {
        List<MessageResponseDTO> unreadMessages = chatService.getUnreadMessages(userId);
        return ResponseEntity.ok(unreadMessages);
    }

    // Mark messages as read between two users
    @PutMapping("/read")
    public ResponseEntity<String> markMessagesAsRead(
            @RequestParam Long receiverId,
            @RequestParam Long senderId) {
        chatService.markMessagesAsRead(receiverId, senderId);
        return ResponseEntity.ok("Messages marked as read");
    }

    // Mark single message as read
    @PutMapping("/{messageId}/read")
    public ResponseEntity<String> markSingleMessageAsRead(@PathVariable Long messageId) {
        chatService.markSingleMessageAsRead(messageId);
        return ResponseEntity.ok("Message marked as read");
    }

    // Get last message between two users
    @GetMapping("/last")
    public ResponseEntity<MessageResponseDTO> getLastMessage(
            @RequestParam Long userId1,
            @RequestParam Long userId2) {
        MessageResponseDTO lastMessage = chatService.getLastMessages(userId1, userId2);
        return ResponseEntity.ok(lastMessage);
    }

    // Get all chat partners for a user
    @GetMapping("/partners/{userId}")
    public ResponseEntity<List<User>> getChatPartners(@PathVariable Long userId) {
        List<User> partners = chatService.getChatPartners(userId);
        return ResponseEntity.ok(partners);
    }

    // Delete a message
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Long messageId,
            @RequestParam Long userId) {
        chatService.deleteMessage(messageId, userId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    // Get message by ID
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponseDTO> getMessageById(@PathVariable Long messageId) {
        MessageResponseDTO message = chatService.findMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    // Get recent messages for a user (last 50)
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<MessageResponseDTO>> getRecentMessages(@PathVariable Long userId) {
        List<MessageResponseDTO> recentMessages = chatService.getRecentMessages(userId);
        return ResponseEntity.ok(recentMessages);
    }

    // Check if user is online
    @GetMapping("/online/{userId}")
    public ResponseEntity<Boolean> isUserOnline(@PathVariable Long userId) {
        boolean isOnline = chatService.isUserOnline(userId);
        return ResponseEntity.ok(isOnline);
    }
}