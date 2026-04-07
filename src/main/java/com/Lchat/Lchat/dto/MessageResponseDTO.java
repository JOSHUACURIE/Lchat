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
    his.type=type;
    }


}
