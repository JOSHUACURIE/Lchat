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

  
    
    // 6. Delete messages older than (for cleanup)
    void deleteByTimestampBefore(LocalDateTime date);
    
    // 7. Count unread messages for a user
    long countByReceiverIdAndIsReadFalse(Long receiverId);
    

 @Query("SELECT m FROM Message m WHERE (m.sender.id = :user1 AND m.receiver.id = :user2) OR (m.sender.id = :user2 AND m.receiver.id = :user1) ORDER BY m.timestamp ASC")
List<Message> findConversation(@Param("user1") Long user1, @Param("user2") Long user2);

// Get unread messages for a user
List<Message> findByReceiverIdAndIsReadFalse(Long receiverId);

// Mark messages as read
@Modifying
@Query("UPDATE Message m SET m.isRead = true WHERE m.receiver.id = :receiverId AND m.sender.id = :senderId")
void markMessagesAsRead(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

// Get last message between two users
Message findTopBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
    Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);

// Get chat partners (distinct users a user has chatted with)
@Query("SELECT DISTINCT u FROM User u WHERE u.id IN " +
       "(SELECT m.sender.id FROM Message m WHERE m.receiver.id = :userId) OR u.id IN " +
       "(SELECT m.receiver.id FROM Message m WHERE m.sender.id = :userId)")
List<User> findChatPartners(@Param("userId") Long userId);

// Get recent messages for a user (last 50)
@Query("SELECT m FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId ORDER BY m.timestamp DESC LIMIT 50")
List<Message> findRecentMessagesForUser(@Param("userId") Long userId);
}
