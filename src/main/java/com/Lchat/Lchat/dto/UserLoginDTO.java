package com.Lchat.Lchat.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    @NotBlank(message = "email or username is required")
    private String usernameOrEmail;

    @NotBlank(message="password is required")
    private String password;

    public UserLoginDTO(){}

    public UserLoginDTO(String usernameOrEmail,String password){
        this.usernameOrEmail=usernameOrEmail;
        this.password=password;
    }

    public String getUsernameOrEmail(){ return usernameOrEmail;}
    public void setUsernameOrEmail(String usernameOrEmail){ this.usernameOrEmail=usernameOrEmail;}

    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}

    
}
