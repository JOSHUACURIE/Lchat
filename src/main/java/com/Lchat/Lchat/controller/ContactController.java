package com.Lchat.Lchat.controller;

import com.Lchat.Lchat.dto.ContactRequestDTO;
import com.Lchat.Lchat.dto.ContactResponseDTO;
import com.Lchat.Lchat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    // Get all accepted contacts
    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> getContacts(@RequestParam Long userId) {
        List<ContactResponseDTO> contacts = contactService.getAcceptedContacts(userId);
        return ResponseEntity.ok(contacts);
    }
    
    // Get pending friend requests
    @GetMapping("/pending")
    public ResponseEntity<List<ContactResponseDTO>> getPendingRequests(@RequestParam Long userId) {
        List<ContactResponseDTO> pendingRequests = contactService.getPendingRequests(userId);
        return ResponseEntity.ok(pendingRequests);
    }
    
    // Get sent friend requests
    @GetMapping("/sent")
    public ResponseEntity<List<ContactResponseDTO>> getSentRequests(@RequestParam Long userId) {
        List<ContactResponseDTO> sentRequests = contactService.getSentRequests(userId);
        return ResponseEntity.ok(sentRequests);
    }
    
    // Send friend request
    @PostMapping("/add")
    public ResponseEntity<?> addContact(
            @RequestParam Long userId,
            @RequestBody ContactRequestDTO request) {
        try {
            ContactResponseDTO contact = contactService.sendFriendRequest(userId, request.getContactId());
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Accept friend request
    @PostMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(
            @RequestParam Long userId,
            @PathVariable Long requestId) {
        try {
            ContactResponseDTO contact = contactService.acceptFriendRequest(userId, requestId);
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Reject friend request
    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(
            @RequestParam Long userId,
            @PathVariable Long requestId) {
        try {
            contactService.rejectFriendRequest(userId, requestId);
            return ResponseEntity.ok("Friend request rejected");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Remove contact
    @DeleteMapping("/remove/{contactId}")
    public ResponseEntity<?> removeContact(
            @RequestParam Long userId,
            @PathVariable Long contactId) {
        try {
            contactService.removeContact(userId, contactId);
            return ResponseEntity.ok("Contact removed");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Search users
    @GetMapping("/search")
    public ResponseEntity<List<ContactResponseDTO>> searchUsers(
            @RequestParam Long userId,
            @RequestParam String searchTerm) {
        List<ContactResponseDTO> results = contactService.searchUsers(userId, searchTerm);
        return ResponseEntity.ok(results);
    }
}