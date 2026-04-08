package com.Lchat.Lchat.controller;

import com.Lchat.Lchat.dto.UserDTO;
import com.Lchat.Lchat.dto.UserLoginDTO;
import com.Lchat.Lchat.dto.UserRegistrationDTO;
import com.Lchat.Lchat.dto.UserResponseDTO;
import com.Lchat.Lchat.dto.UserUpdateDTO;
import com.Lchat.Lchat.model.ChatStatus;
import com.Lchat.Lchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            UserResponseDTO registeredUser = userService.registerUser(registrationDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO loginDTO) {
        try {
            UserResponseDTO loggedInUser = userService.login(loginDTO);
            return ResponseEntity.ok(loggedInUser);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // User logout
    @PostMapping("/{userId}/logout")
    public ResponseEntity<String> logoutUser(@PathVariable Long userId) {
        try {
            userService.logout(userId);
            return ResponseEntity.ok("Logged out successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        try {
            UserResponseDTO user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        try {
            UserResponseDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get all online users
    @GetMapping("/online")
    public ResponseEntity<List<UserDTO>> getAllOnlineUsers() {
        List<UserDTO> onlineUsers = userService.getAllOnlineUsers();
        return ResponseEntity.ok(onlineUsers);
    }

    // Search users (for adding friends)
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam String searchTerm,
            @RequestParam Long currentUserId) {
        List<UserDTO> users = userService.searchUsers(searchTerm, currentUserId);
        return ResponseEntity.ok(users);
    }

    // Update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO updateDTO) {
        try {
            UserResponseDTO updatedUser = userService.updateUser(userId, updateDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update user status
    @PatchMapping("/{userId}/status")
    public ResponseEntity<String> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam ChatStatus status) {
        try {
            userService.updateUserStatus(userId, status);
            return ResponseEntity.ok("Status updated to: " + status);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all users (admin)
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}