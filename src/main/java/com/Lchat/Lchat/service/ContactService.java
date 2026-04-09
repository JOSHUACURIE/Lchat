package com.Lchat.Lchat.service;

import com.Lchat.Lchat.dto.ContactResponseDTO;
import com.Lchat.Lchat.model.Contact;
import com.Lchat.Lchat.model.User;
import com.Lchat.Lchat.repository.ContactRepository;
import com.Lchat.Lchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private UserRepository userRepository;
    
   public ContactResponseDTO sendFriendRequest(Long userId, Long contactId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    User contactPerson = userRepository.findById(contactId)  // Renamed to avoid conflict
        .orElseThrow(() -> new RuntimeException("Contact not found"));
    
    // Check if already friends
    if (contactRepository.existsByUserIdAndContactIdAndStatus(userId, contactId, Contact.ContactStatus.ACCEPTED)) {
        throw new RuntimeException("Already friends");
    }
    
    // Check if friend request already sent
    if (contactRepository.existsByUserIdAndContactIdAndStatus(userId, contactId, Contact.ContactStatus.PENDING)) {
        throw new RuntimeException("Friend request already sent");
    }
    
    // Check if there's a pending request from the other user
    if (contactRepository.existsByUserIdAndContactIdAndStatus(contactId, userId, Contact.ContactStatus.PENDING)) {
        // Auto-accept if request exists
        return acceptFriendRequest(userId, contactId);
    }
    
    // Create new contact request
    Contact newContact = new Contact(user, contactPerson, Contact.ContactStatus.PENDING);
    contactRepository.save(newContact);
    
    return convertToDTO(newContact);
}

// Accept friend request
public ContactResponseDTO acceptFriendRequest(Long userId, Long contactId) {
    // Find the pending request
    Contact pendingContact = contactRepository.findByUserIdAndContactId(contactId, userId)
        .orElseThrow(() -> new RuntimeException("Friend request not found"));
    
    // Update status to ACCEPTED
    pendingContact.setStatus(Contact.ContactStatus.ACCEPTED);
    contactRepository.save(pendingContact);
    
    // Create reverse relationship (if not exists)
    if (!contactRepository.existsByUserIdAndContactIdAndStatus(userId, contactId, Contact.ContactStatus.ACCEPTED)) {
        Contact reverseContact = new Contact(
            pendingContact.getContact(),  // This is the user who sent the request
            pendingContact.getUser(),     // This is the user who received the request
            Contact.ContactStatus.ACCEPTED
        );
        contactRepository.save(reverseContact);
    }
    
    return convertToDTO(pendingContact);
}
    // Reject friend request
    public void rejectFriendRequest(Long userId, Long contactId) {
        Contact contact = contactRepository.findByUserIdAndContactId(contactId, userId)
            .orElseThrow(() -> new RuntimeException("Friend request not found"));
        
        contactRepository.delete(contact);
    }
    
    // Remove contact
    public void removeContact(Long userId, Long contactId) {
        contactRepository.deleteByUserIdAndContactId(userId, contactId);
        contactRepository.deleteByUserIdAndContactId(contactId, userId);
    }
    
    // Get all accepted contacts
    public List<ContactResponseDTO> getAcceptedContacts(Long userId) {
        List<User> contacts = contactRepository.findAcceptedContacts(userId);
        return contacts.stream()
            .map(user -> convertToDTO(user, "ACCEPTED"))
            .collect(Collectors.toList());
    }
    
    // Get pending friend requests received
    public List<ContactResponseDTO> getPendingRequests(Long userId) {
        List<User> pendingRequests = contactRepository.findPendingRequests(userId);
        return pendingRequests.stream()
            .map(user -> convertToDTO(user, "PENDING"))
            .collect(Collectors.toList());
    }
    
    // Get sent friend requests
    public List<ContactResponseDTO> getSentRequests(Long userId) {
        List<User> sentRequests = contactRepository.findSentRequests(userId);
        return sentRequests.stream()
            .map(user -> convertToDTO(user, "SENT"))
            .collect(Collectors.toList());
    }
    
    // Search users excluding contacts and self
    public List<ContactResponseDTO> searchUsers(Long userId, String searchTerm) {
        List<User> acceptedContacts = contactRepository.findAcceptedContacts(userId);
        List<Long> contactIds = acceptedContacts.stream().map(User::getId).collect(Collectors.toList());
        contactIds.add(userId);
        
        List<User> searchResults = userRepository.searchUsersExcludingContacts(contactIds, searchTerm);
        return searchResults.stream()
            .map(user -> convertToDTO(user, "NONE"))
            .collect(Collectors.toList());
    }
    
    // Helper method to convert Contact to DTO
    private ContactResponseDTO convertToDTO(Contact contact) {
        ContactResponseDTO dto = new ContactResponseDTO();
        User contactUser = contact.getContact();
        dto.setId(contactUser.getId());
        dto.setName(contactUser.getName());
        dto.setUsername(contactUser.getUsername());
        dto.setEmail(contactUser.getEmail());
        dto.setStatus(contactUser.getChatStatus());
        dto.setRelationshipStatus(contact.getStatus().toString());
        return dto;
    }
    
    // Helper method to convert User to DTO
    private ContactResponseDTO convertToDTO(User user, String relationshipStatus) {
        ContactResponseDTO dto = new ContactResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getChatStatus());
        dto.setRelationshipStatus(relationshipStatus);
        return dto;
    }
}