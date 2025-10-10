package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
