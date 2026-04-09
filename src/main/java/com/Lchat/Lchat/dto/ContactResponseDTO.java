package com.Lchat.Lchat.dto;

import com.Lchat.Lchat.model.ChatStatus;

public class ContactResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String avatar;
    private ChatStatus status;
    private String relationshipStatus; // PENDING, ACCEPTED, etc.
    
    // Constructors
    public ContactResponseDTO() {}
    
    public ContactResponseDTO(Long id, String name, String username, String email, ChatStatus status, String relationshipStatus) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.status = status;
        this.relationshipStatus = relationshipStatus;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public ChatStatus getStatus() { return status; }
    public void setStatus(ChatStatus status) { this.status = status; }
    
    public String getRelationshipStatus() { return relationshipStatus; }
    public void setRelationshipStatus(String relationshipStatus) { this.relationshipStatus = relationshipStatus; }
}