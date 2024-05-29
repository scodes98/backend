package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends MongoRepository<Publisher, String> {
    Publisher findByUsername(String username);
}
