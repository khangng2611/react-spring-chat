package com.khangng.websockets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="room_id")
    private int roomId;
    
    @Column(name="sender_id")
    private int senderId;
    
    @Column(name="receiver_id")
    private int receiverId;
    
    @Column(name="content")
    private String content;
    
    @Column(name="created_at")
    private String createdAt;
}