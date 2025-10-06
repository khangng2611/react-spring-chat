package com.hcmut.chatterbox.entity;

import com.hcmut.chatterbox.enums.RegisterStatus;
import com.hcmut.chatterbox.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="email", unique = true)
    private String email;
    
    @Column(name="phone", unique = true)
    private String phone;
    
    @Column(name="hash_password")
    private String hashPassword;
    
    @Column(name="full_name")
    private String fullName;
    
//    @Enumerated(EnumType.STRING)
//    @Column(name="status")
//    private UserStatus status = UserStatus.OFFLINE;
    
    @Enumerated(EnumType.STRING)
    @Column(name="register_status")
    private RegisterStatus registerStatus = RegisterStatus.PENDING;
}
