package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.ApiLogSendSector;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiLogSendSectorRepository extends MongoRepository<ApiLogSendSector, String> {
    // You can add custom query methods if needed
}