package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.PublishMaster;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishMasterRepository extends MongoRepository<PublishMaster, String> {
    List<PublishMaster> findByFlagIsTrue();
    boolean existsById(String id);
}