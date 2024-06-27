package com.khangng.websockets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "public_message")
public class PublicMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne()
    @JoinColumn(name="sender_id")
    private User sender;
    
    @Column(name="content", length = 1000)
    private String content;
    
    @Column(name="created_at")
    @CreationTimestamp
    private String createdAt;
    
    public PublicMessage(User sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}