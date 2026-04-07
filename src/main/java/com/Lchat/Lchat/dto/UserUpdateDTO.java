package com.Lchat.Lchat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
    @Size(min = 3,max = 50,message = "username must be between 3 and 50 characters")
    private String username;

    @Size(min = 2,max=100,message = "name must be between 2 and 100 characters")
    private String name;
    
    @Size(min = 6,message="password must be atleast 6 characters")
    private String password;

    @Email(message = "invalid email format")
    private String email;

    public UserUpdateDTO(){}
    
    public String getUsername(){ return username;}
    public void setUsername(String username){ this.username=username;}


    public String getEmail(){ return email;}
    public void setEmail(String email){ this.email=email;} 

    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}

    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}



}
