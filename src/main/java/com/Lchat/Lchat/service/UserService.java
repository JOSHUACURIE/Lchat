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





}
