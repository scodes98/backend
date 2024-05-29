package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.ApiLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiLogRepository extends MongoRepository<ApiLog, String> {
    // You can add custom query methods if needed
}