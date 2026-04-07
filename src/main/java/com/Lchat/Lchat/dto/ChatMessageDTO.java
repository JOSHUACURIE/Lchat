package com.Lchat.Lchat.dto;

public class ChatMessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
    private String type;

    public ChatMessageDTO(){}
    public ChatMessageDTO(Long senderId,Long receiverId,String content,String type){
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.content=content;
        this.type=type;
    }

    public Long getSenderId(){ return senderId; }
    public void setSenderId(Long senderId){ this.senderId=senderId; }

    public Long getReceiverId(){ return receiverId;}
    public void setReceiverId(Long receiverId){ this.receiverId=receiverId;}

    public String getContent(){ return content;}
    public void setContent(String content){ this.content=content;}

    public String getType(){ return type;}
    public void setType(String type){ this.type=type;}

}
