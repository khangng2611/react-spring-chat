package com.khangng.websockets.repository;

import com.khangng.websockets.entity.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicMessageRepository extends JpaRepository<PublicMessage, Integer> {
}
