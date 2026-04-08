package com.Lchat.Lchat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String type;
    

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id",nullable = false)
    private User receiver;


    //constructors
    public Message(){}

    //setters and getters
    public Long getId(){ return id;}
    public void setId(Long id){ this.id=id;}

    public String getContent(){ return content;}
    public void setContent(String content){ this.content=content;}

    public LocalDateTime getTimeStamp(){ return timestamp;}
    public void setTimeStamp(LocalDateTime timestamp){ this.timestamp=timestamp;}

    public boolean getIsRead(){ return isRead;}
    public void setIsRead(boolean isRead){ this.isRead=isRead;}

    public User getSender(){ return sender;}
    public void setSender(User sender){ this.sender=sender;}

    public User getReceiver(){ return receiver;}
    public void setReceiver(User receiver){ this.receiver=receiver;}

    public String getType(){ return type;}
    public void setType(String type){ this.type=type;}
    
}
