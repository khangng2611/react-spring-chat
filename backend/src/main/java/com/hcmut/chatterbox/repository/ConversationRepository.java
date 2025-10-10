package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
}
