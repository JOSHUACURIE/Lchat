package com.Lchat.Lchat.dto;

import com.Lchat.Lchat.model.ChatStatus;
import java.time.LocalDateTime;


public class UserResponseDTO {

    private Long id;
    private String username;
    private String name;
    private String email;
    private ChatStatus chatStatus;
    private LocalDateTime lastActive;
    private LocalDateTime createdAt;

    public UserResponseDTO(){}

    public UserResponseDTO(Long id,String username,String name,String email,ChatStatus chatStatus,LocalDateTime lastActive,LocalDateTime createdAt){
        this.id=id;
        this.username=username;
        this.name=name;
        this.email=email;
        this.chatStatus=chatStatus;
        this.lastActive=lastActive;
        this.createdAt=createdAt;
    }

    //setters and getters

    public Long getId(){ return id;}
    public void setId(Long id){ this.id=id;}

    public String getUsername(){ return username;}
    public void setUsername(String username){ this.username=username;}

    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}

    public String getEmail(){ return email;}
    public void setEmail(String email){ this.email=email;}

    public ChatStatus getChatStatus(){ return chatStatus;}
    public void setChatStatus(ChatStatus chatStatus){ this.chatStatus=chatStatus;}

    public LocalDateTime getLastActive(){ return lastActive;}
    public void setLastActive(LocalDateTime lastActive){ this.lastActive=lastActive;}

    public LocalDateTime getCreatedAt(){ return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt=createdAt;}






    
}
