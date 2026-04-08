package com.Lchat.Lchat.mapper;

import com.Lchat.Lchat.dto.*;
import com.Lchat.Lchat.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.*;

@Component
public class UserMapper {
    
//convert registration dto into entity for user creation
public User toEntity(UserRegistrationDTO dto){
    if(dto==null){
        return null;
    }
    User user=new User();
    user.setUsername(dto.getUsername());
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setChatStatus(ChatStatus.OFFLINE);
    user.setLastActive(LocalDateTime.now());

return user;
}
    
//convert entity into dto
public UserResponseDTO toResponseDTO(User user){
    if(user==null){ return null;}

    UserResponseDTO dto=new UserResponseDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setChatStatus(user.getChatStatus());
    dto.setLastActive(user.getLastActive());
    dto.setCreatedAt(user.getCreatedAt());

return dto;
}
//update entity from dto
public void updateEntity(UserUpdateDTO dto,User user ){
    if( dto == null || user ==null){ return; }

    if(dto.getUsername() != null){ user.setUsername(dto.getUsername());}

    if(dto.getName() != null){ user.setName(dto.getName());}

    if(dto.getEmail() != null){ user.setEmail(dto.getEmail());}

    if(dto.getPassword() != null){ user.setPassword(dto.getPassword());}

}
public UserDTO toDTO(User user) {
    if (user == null) {
        return null;
    }
    
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setChatStatus(user.getChatStatus());
    
    return dto;
}

// Convert list of Users to list of UserDTOs
public List<UserDTO> toDTOList(List<User> users) {
    if (users == null) {
        return null;
    }
    
    return users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}
}
