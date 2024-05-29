package com.example.publishSubscriber.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.publishSubscriber.Entity.SubscriberDataLog;


import java.lang.Long;


public interface SubscriberDataLogRepository extends MongoRepository<SubscriberDataLog, String> {
    List<SubscriberDataLog> findByPublishMasterIdIn(List<String> publishMasterIds);
    List<SubscriberDataLog> findByPublishMasterIdInAndSubscriberUsernameAndIsActive(
            List<String> publishMasterIds, String subscriberUsername, boolean isActive);
            

     // Define a method to find the maximum offset for a given publishMasterId
    @Query("SELECT s FROM SubscriberDataLog s WHERE s.publishMasterId = :publishMasterId ORDER BY s.offset DESC LIMIT 1")
    Long findMaxOffsetByPublishMasterId(@Param("publishMasterId") String publishMasterId);

}

