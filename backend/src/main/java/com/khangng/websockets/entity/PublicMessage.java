package com.khangng.websockets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "public_message")
public class PublicMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="sender_id")
    private int senderId;
    
    @Column(name="content", length = 1000)
    private String content;
    
    @Column(name="created_at")
    @CreationTimestamp
    private String createdAt;
}