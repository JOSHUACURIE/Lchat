package com.Lchat.Lchat.repository;

import com.Lchat.Lchat.model.Contact;
import com.Lchat.Lchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    // Find all accepted contacts for a user
    @Query("SELECT c.contact FROM Contact c WHERE c.user.id = :userId AND c.status = 'ACCEPTED'")
    List<User> findAcceptedContacts(@Param("userId") Long userId);
    
    // Find pending requests received by a user
    @Query("SELECT c.user FROM Contact c WHERE c.contact.id = :userId AND c.status = 'PENDING'")
    List<User> findPendingRequests(@Param("userId") Long userId);
    
    // Find sent requests by a user
    @Query("SELECT c.contact FROM Contact c WHERE c.user.id = :userId AND c.status = 'PENDING'")
    List<User> findSentRequests(@Param("userId") Long userId);
    
    // Find contact relationship between two users
    Optional<Contact> findByUserIdAndContactId(Long userId, Long contactId);
    
    // Check if contact exists with specific status
    boolean existsByUserIdAndContactIdAndStatus(Long userId, Long contactId, Contact.ContactStatus status);
    
    // Delete contact relationship
    void deleteByUserIdAndContactId(Long userId, Long contactId);
}