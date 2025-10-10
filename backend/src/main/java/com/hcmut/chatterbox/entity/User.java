package com.hcmut.chatterbox.entity;

import com.hcmut.chatterbox.enums.RegisterStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name="email", unique = true)
    private String email;
    
    @Column(name="hash_password")
    private String hashPassword;
    
    @Column(name="full_name")
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(name="register_status")
    private RegisterStatus registerStatus = RegisterStatus.PENDING;
}
