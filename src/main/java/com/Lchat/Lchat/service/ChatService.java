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
    //get conversation between two users
    public List<MessageResponseDTO>getConversation(Long userId1,Long userId2){
        List<Message> conversation=messageRepository.findConversation(userId1, userId2);

        return messageMapper.toResponseDTOList(conversation);
    }

    //get all unread messages for a user
    public List<MessageResponseDTO> getUnreadMessages(Long userId){
        List<Message>unreadMessages=messageRepository.findByReceiverIdAndIsReadFalse(userId);
        return messageMapper.toResponseDTOList(unreadMessages);

    }
    //mark messages unread
    public void markMessagesAsRead(Long userId1,Long userId2){
        messageRepository.markMessagesAsRead(userId1, userId2);
    }

    //mark a single message as read
    public void markSingleMessageAsRead(Long messageId){
        Message message=messageRepository.findById(messageId)
        .orElseThrow(()->new RuntimeException("message not found with id: "+messageId));

        message.setIsRead(true);
        messageRepository.save(message);
    }

    //getting message between two last users
    public MessageResponseDTO getLastMessages(Long userId1,long userId2){
        Message lastMessage=messageRepository.findTopBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(userId1, userId1, userId1, userId1);
             return lastMessage != null ? messageMapper.toResponseDTO(lastMessage) : null;
    }

    //get all chat partners
    public List<User> getChatPartners(Long userId){
        return messageRepository.findChatPartners(userId);
    }

    //delete message for a user
    public void deleteMessage(Long messageId,Long userId){
        Message message=messageRepository.findById(userId)
        .orElseThrow(()->new RuntimeException("message not found with id: "+messageId));

        //check if the user is sender or receiver
        if(message.getSender().getId().equals(userId)|| message.getReceiver().getId().equals(userId)){
            messageRepository.delete(message);
        }else{
            throw new RuntimeException("You are not authorized to delete this message");
        }
    }

    // Get message by ID
    public MessageResponseDTO findMessageById(Long messageId){
        Message message=messageRepository.findById(messageId)
        .orElseThrow(()->new RuntimeException("message not found with id: "+messageId));

        return messageMapper.toResponseDTO(message);
    }

    public List<MessageResponseDTO>getRecentMessages(Long userId){
        List<Message> message=messageRepository.findRecentMessagesForUser(userId);

     return messageMapper.toResponseDTOList(message);
    }
    public boolean isUserOnline(Long userId){
              return userRepository.findById(userId)
                .map(user -> user.getChatStatus() == com.Lchat.Lchat.model.ChatStatus.ONLINE)
                .orElse(false);
    }
    }


