package com.Lchat.Lchat.model;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false,unique = true)
    private String name;
    
    @Column(nullable = false)
    private Long password;

    @Column(nullable = false,unique = true)
    private Long email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatStatus chatStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime lastActive;


    //constructors
    public User(){}

    public User(String name,String password,String email){
        this.name=name;
        this.password=password;
        this.email=email;
    }
    
}
