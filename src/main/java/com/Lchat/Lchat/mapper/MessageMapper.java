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
    
    //convert message response to messageResponseDTO
    public MessageResponseDTO toResponseDTO(Message message){
        if(message==null){ return null; }


        MessageResponseDTO dto=new MessageResponseDTO();
        dto.setId(message.getId());
        
        //setting sender info
        if(message.getSender()!=null){
            dto.setSenderId(message.getSender().getId());
            dto.setSenderName(message.getSender().getUsername());

        }

        //setting receiver info
        if(message.getReceiver()!=null){
            dto.setReceiverId(message.getReceiver().getId());
            dto.setReceiverName(message.getReceiver().getUsername());
        }
      
        dto.setContent(message.getContent());
        dto.setTimeStamp(message.getTimeStamp());
        dto.setIsRead(message.getIsRead());
        dto.setType(message.getType());

        return dto;
    }
    //converting list of messages to list of reponses dtos
    public List<MessageResponseDTO> toResponseDTOList(List<Message> messages){

  if(messages==null){ return null;}


  return messages.stream()
                 .map(this::toResponseDTO)
                 .collect(Collectors.toList());
    }

}
