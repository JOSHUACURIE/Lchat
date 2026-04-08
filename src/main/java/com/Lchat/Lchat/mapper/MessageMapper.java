package com.Lchat.Lchat.mapper;

import com.Lchat.Lchat.model.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.Lchat.Lchat.dto.*;


@Component
public class MessageMapper {
    //convert message dto to entity
    public Message toEntity(ChatMessageDTO dto,User sender,User receiver){
        if(dto==null){ return null;}

        Message message=new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(dto.getContent());
        message.setType(dto.getType());
        message.setTimeStamp(LocalDateTime.now());
        message.setIsRead(false);



        return message;
    }
    
}
