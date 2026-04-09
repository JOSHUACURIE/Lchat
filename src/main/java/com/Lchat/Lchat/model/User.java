package com.Lchat.Lchat.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @JsonIgnore  // Prevents password from being serialized in JSON responses
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatStatus chatStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastActive;

    @JsonIgnore  // Prevents infinite recursion in JSON
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> sentMessages = new ArrayList<>();

    @JsonIgnore  // Prevents infinite recursion in JSON
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> receivedMessages = new ArrayList<>();

    // Constructors
    public User() {}

    public User(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.chatStatus = ChatStatus.OFFLINE;
        this.createdAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
 
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public ChatStatus getChatStatus() { 
        return chatStatus; 
    }
    
    public void setChatStatus(ChatStatus chatStatus) { 
        this.chatStatus = chatStatus; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public LocalDateTime getLastActive() { 
        return lastActive; 
    }
    
    public void setLastActive(LocalDateTime lastActive) { 
        this.lastActive = lastActive; 
    }

    public List<Message> getSentMessages() { 
        return sentMessages; 
    }
    
    public void setSentMessages(List<Message> sentMessages) { 
        this.sentMessages = sentMessages; 
    }

    public List<Message> getReceivedMessages() { 
        return receivedMessages; 
    }
    
    public void setReceivedMessages(List<Message> receivedMessages) { 
        this.receivedMessages = receivedMessages; 
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastActive = LocalDateTime.now();
    }

    // Helper methods
    public void updateLastActive() {
        this.lastActive = LocalDateTime.now();
    }
    
    public boolean isOnline() {
        return this.chatStatus == ChatStatus.ONLINE;
    }
    
    public boolean isOffline() {
        return this.chatStatus == ChatStatus.OFFLINE;
    }
    
    public boolean isAway() {
        return this.chatStatus == ChatStatus.AWAY;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", chatStatus=" + chatStatus +
                ", createdAt=" + createdAt +
                ", lastActive=" + lastActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}