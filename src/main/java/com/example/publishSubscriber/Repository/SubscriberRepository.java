package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
    Subscriber findByUsername(String username);
}