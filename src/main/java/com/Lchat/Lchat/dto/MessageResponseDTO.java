package com.Lchat.Lchat.dto;

import java.time.LocalDateTime;

public class MessageResponseDTO {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String type;
    

    public MessageResponseDTO(){}
  
    public MessageResponseDTO(Long id,Long senderId,String senderName,Long receiverId,String receiverName,
        String content,LocalDateTime timestamp,boolean isRead,String type
    ){
   this.id=id;
   this.senderId=senderId;
   this.senderName=senderName;
   this.receiverId=receiverId;
   this.receiverName=receiverName;
   this.content=content;
   this.timestamp=timestamp;
   this.isRead=isRead;
    this.type=type;
    }

    public Long getId(){ return id;}
    public void setId( Long id){ this.id=id;}

    public Long getSenderId(){ return senderId;}
    public void setSenderId(Long senderId){ this.senderId=senderId;}

    public String getSenderName(){ return senderName;}
    public void setSenderName(String senderName){ this.senderName=senderName;}

    public Long getReceiverId(){ return receiverId;}
    public void setReceiverId(Long receiverId){ this.receiverId=receiverId;}

    public String getReceiverName(){ return receiverName;}
    public void setReceiverName(String receiverName){ this.receiverName=receiverName;}

    public String getContent(){ return content;}
    public void setContent(String content){ this.content=content;}

    public LocalDateTime getTimeStamp(){ return timestamp;}
    public void setTimeStamp(LocalDateTime timestamp){ this.timestamp=timestamp;}

    public boolean getIsRead(){ return isRead;}
    public void setIsRead(boolean isRead){ this.isRead=isRead;}

    public String getType(){ return type;}
    public void setType(String type){ this.type=type;}




}
