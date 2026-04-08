package com.Lchat.Lchat.service;

import com.Lchat.Lchat.dto.ChatMessageDTO;
import com.Lchat.Lchat.dto.MessageResponseDTO;
import com.Lchat.Lchat.mapper.MessageMapper;
import com.Lchat.Lchat.model.Message;
import com.Lchat.Lchat.model.User;
import com.Lchat.Lchat.repository.MessageRepository;
import com.Lchat.Lchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    //send a message
    public MessageResponseDTO sendMessage(ChatMessageDTO chatMessageDTO){
        //get sender and user from the database
        User sender=userRepository.findById(chatMessageDTO.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found with id: " + chatMessageDTO.getSenderId()));
        User receiver=userRepository.findById(chatMessageDTO.getReceiverId())
           .orElseThrow(()->new RuntimeException("Receiver not found with id: "+chatMessageDTO.getReceiverId()));

           //convert DTO to entity
           Message message=messageMapper.toEntity(chatMessageDTO, sender, receiver);

           //set additional fields
           message.setTimeStamp(LocalDateTime.now());
           message.setIsRead(false);

           Message savedMessage=messageRepository.save(message);
           return messageMapper.toResponseDTO(savedMessage);
    }
    
}
