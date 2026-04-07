package com.Lchat.Lchat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class UserRegistrationDTO {

    @NotBlank(message = "username is reeuired")
    @Size(min = 3,max = 3,message = "username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 100,message = "name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "password is required")
    @Size(min = 6,message = "password must be atleast 6 characters")
    private String password;
    
    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;


    //constructors
    public UserRegistrationDTO(){}

    public UserRegistrationDTO(String username,String name,String password,String email){
        this.username=username;
        this.name=name;
        this.password=password;
        this.email=email;
    }

    //getters and setters
    public String getUsername(){ return username;}
    public void setUsername(String username){ this.username=username;}


    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}

    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}


    public String getEmail(){ return email;}
    public void setEmail(String email){ this.email=email;}
}
