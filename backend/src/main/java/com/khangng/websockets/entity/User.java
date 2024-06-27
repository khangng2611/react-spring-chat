package com.khangng.websockets.entity;

import com.khangng.websockets.config.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="username")
    private String username;
    
//    @Column(name="password")
//    private String password;
    
    @Column(name="full_name")
    private String fullName;
    
    @Column(name="status")
    private Status status;
    
    public User(String username, String fullName, Status status) {
        this.username = username;
        this.fullName = fullName;
        this.status = status;
    }
}
