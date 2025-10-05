package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.entity.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicMessageRepository extends JpaRepository<PublicMessage, Integer> {
}
