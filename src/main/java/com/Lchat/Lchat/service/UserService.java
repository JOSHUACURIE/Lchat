package com.Lchat.Lchat.service;


import com.Lchat.Lchat.dto.UserDTO;
import com.Lchat.Lchat.dto.UserLoginDTO;
import com.Lchat.Lchat.dto.UserRegistrationDTO;
import com.Lchat.Lchat.dto.UserResponseDTO;
import com.Lchat.Lchat.dto.UserUpdateDTO;
import com.Lchat.Lchat.mapper.UserMapper;
import com.Lchat.Lchat.model.ChatStatus;
import com.Lchat.Lchat.model.User;
import com.Lchat.Lchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO){

    if(userRepository.existsByEmail(registrationDTO.getEmail())){
        throw new RuntimeException("email already exists");
    }
     
    if(userRepository.existsByUsername(registrationDTO.getUsername())){
        throw new RuntimeException("Username already exists");
    }

    //convert DTO to user entity
    User user=userMapper.toEntity(registrationDTO);

    //setting default values
    user.setChatStatus(ChatStatus.OFFLINE);
    user.setLastActive(LocalDateTime.now());

    //save to db
    User savedUser=userRepository.save(user);

    //converting response to dto before sending
    return userMapper.toResponseDTO(savedUser);
    
}

//authentication for user login
public UserResponseDTO login(UserLoginDTO loginDTO){
    //find user by email or username
    Optional<User> userOptional=userRepository.findByEmailorUsername(
        loginDTO.getUsernameOrEmail(),
        loginDTO.getUsernameOrEmail()
    );

    if(userOptional.isEmpty()){ throw new RuntimeException("Invalid credentials");}


    User user=userOptional.get();

    //checking password
    if(!user.getPassword().equals(loginDTO.getPassword())){ throw new RuntimeException("Invalid credentials");}


    //updating user status
    user.setChatStatus(ChatStatus.ONLINE);
    user.setLastActive(LocalDateTime.now());
    userRepository.save(user);

    return userMapper.toResponseDTO(user);
}

//logout user
public void logout(Long userId){
Optional<User>userOptional=userRepository.findById(userId);

if(userOptional.isPresent()){
  User user=userOptional.get();
  user.setChatStatus(ChatStatus.OFFLINE);
  user.setLastActive(LocalDateTime.now());
  userRepository.save(user);

}
}
//get user by ID
public UserResponseDTO getUserById(Long id){
    User user=userRepository.findById(id)
           .orElseThrow(()-> new RuntimeException("user not found with id: "+id));
return userMapper.toResponseDTO(user);
}

//get user by username
public UserResponseDTO getUserByUsername(String username){
    User user=userRepository.findByUsername(username)
    .orElseThrow(()->new RuntimeException("user not found with username: "+username));

return userMapper.toResponseDTO(user);
}
//get all online users
public List<UserDTO> getAllOnlineUsers(){
    List<User>onlineUsers=userRepository.findByStatus(ChatStatus.ONLINE);

    return onlineUsers.stream()
    .map(userMapper::toDTO)
    .collect(Collectors.toList());
}

//search for users by name
public List<UserDTO> searchUsers(String searchTerm,Long currentUserId){
    List<User> users=userRepository.searchUsersExcludingCurrent(currentUserId, searchTerm);

    return users.stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());

}
//update user profile
public UserResponseDTO updateUser(Long userId,UserUpdateDTO updateDTO){
       User user = userRepository.findById(userId)
       .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

       //updating fields using mapper
       userMapper.updateEntity(updateDTO, user);

       //save updated user
       User updatedUser=userRepository.save(user);

return userMapper.toResponseDTO(updatedUser);
}
//update user status 
public void updateUserStatus(Long userId,ChatStatus status){
    User user=userRepository.findById(userId)
    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));


     user.setChatStatus(status);
     user.setLastActive(LocalDateTime.now());
     userRepository.save(user);
}
 // Get all users (for admin)
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
