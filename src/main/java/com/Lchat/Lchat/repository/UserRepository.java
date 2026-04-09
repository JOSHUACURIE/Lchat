package com.Lchat.Lchat.repository;

import com.Lchat.Lchat.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findByEmail(String email);
 
List<User> findByNameIgnoreCase(String name);
    

//find by username for login or display
Optional<User> findByUsername(String name);

//find by email or password for flexible login
// ✅ CORRECT - Using @Query annotation
@Query("SELECT u FROM User u WHERE u.email = :emailOrUsername OR u.username = :emailOrUsername")
Optional<User> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

//search by name
List<User>findByNameContainingIgnoreCase(String name);

//find users by status
   List<User> findByChatStatus(ChatStatus chatStatus);
// Search users excluding contacts
@Query("SELECT u FROM User u WHERE u.id NOT IN :excludedIds AND (u.name LIKE %:searchTerm% OR u.username LIKE %:searchTerm% OR u.email LIKE %:searchTerm%)")
List<User> searchUsersExcludingContacts(@Param("excludedIds") List<Long> excludedIds, @Param("searchTerm") String searchTerm);
//find all online users
@Query("SELECT u FROM User u WHERE u.chatStatus = 'ONLINE'")
List<User> findAllOnlineUsers();


   // 7. Search users excluding current user (for new chat)
    @Query("SELECT u FROM User u WHERE u.id != :currentUserId AND (u.username LIKE %:searchTerm% OR u.email LIKE %:searchTerm%)")
    List<User> searchUsersExcludingCurrent(@Param("currentUserId") Long currentUserId, @Param("searchTerm") String searchTerm);
    
    // 8. Find users inactive for last N minutes (for auto status update)
    List<User> findByLastActiveBefore(LocalDateTime time);
    
    // 9. Check if user exists by email
    boolean existsByEmail(String email);
    
    // 10. Check if user exists by username
    boolean existsByUsername(String username);
    
    // 11. Find recently active users
    @Query("SELECT u FROM User u WHERE u.lastActive > :since ORDER BY u.lastActive DESC")
    List<User> findRecentlyActive(@Param("since") LocalDateTime since);



    
}
