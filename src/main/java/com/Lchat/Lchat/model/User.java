package com.Lchat.Lchat.model;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

     private String username;
    @Column( nullable = false,unique = true)
    private String name;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatStatus chatStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime lastActive;

    @OneToMany(mappedBy = "sender")
    public List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    public List<Message> receivedMessages;

    //constructors
    public User(){}

    public User(String name,String password,String email){
        this.name=name;
        this.password=password;
        this.email=email;
        this.chatStatus=ChatStatus.OFFLINE;
    }

    public Long getId(){ return id;}
    public void setId(Long id){ this.id=id;}

    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}
 
    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}

    @PrePersist
  protected void onCreate(){
 this.createdAt=LocalDateTime.now();
  }

    public String getEmail(){ return email;}
    public void setEmail(String email){ this.email=email;}

    public LocalDateTime getCreatedAt(){ return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt=createdAt;}

    public LocalDateTime getLastActive(){ return lastActive;}
    public void setLastActive(LocalDateTime lastActive){ this.lastActive=lastActive;}

    public String getUsername(){ return username;}
    public void setUsername(String username){ this.username=username;}

    public ChatStatus getChatStatus(){ return chatStatus;}
    public void setChatStatus(ChatStatus chatStatus){ this.chatStatus=chatStatus;}
    

    

}
