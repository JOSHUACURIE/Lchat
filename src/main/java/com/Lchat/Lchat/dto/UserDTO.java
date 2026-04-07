package com.Lchat.Lchat.dto;

import com.Lchat.Lchat.model.ChatStatus;
import com.Lchat.Lchat.model.User;

public class UserDTO {
        private Long id;
    private String username;
    private String name;
    private String email;
    private ChatStatus chatStatus;

    public UserDTO(){}
    public UserDTO(Long id,String username,String email,ChatStatus chatStatus){
        this.id=id;
        this.username=username;
        this.email=email;
        this.chatStatus=chatStatus;
    }

   public Long getId(){ return id;}
   public void setId(Long id){ this.id=id;}
   
   public String getUsername(){ return username;}
   public void setUsername(String username){ this.username=username;}

   public String getEmail(){ return email;}
   public void setEmail(String email){ this.email=email;}

   public String getName(){ return name;}
   public void setName(String name){ this.name=name;}

   public ChatStatus geChatStatus(){ return chatStatus;}
   public void setChatStatus(ChatStatus chatStatus){ this.chatStatus=chatStatus;}
}
