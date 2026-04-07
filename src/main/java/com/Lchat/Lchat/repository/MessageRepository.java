package com.Lchat.Lchat.repository;

import org.springframework.stereotype.Repository;
import com.Lchat.Lchat.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{

       // 1. Get all messages between two users (conversation)
    List<Message>findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
        Long senderId1,Long receiverId1,Long senderId2,Long receiverId2
    );

    //find unread messages for a user
    List<Message>findByReceiverIdAndIsReadFalse(Long receiverId);


    //get last message between two people
        Message findTopBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
        Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);
         // 4. Mark messages as read
    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.receiver.id = :receiverId AND m.sender.id = :senderId")
    void markMessagesAsRead(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);
    
    // 5. Get recent conversations for a user (distinct chat partners)
    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.receiver.id = :userId " +
           "UNION SELECT DISTINCT m.receiver FROM Message m WHERE m.sender.id = :userId")
    List<User> findChatPartners(@Param("userId") Long userId);
    
    // 6. Delete messages older than (for cleanup)
    void deleteByTimestampBefore(LocalDateTime date);
    
    // 7. Count unread messages for a user
    long countByReceiverIdAndIsReadFalse(Long receiverId);
    
}
