package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.MappedDataForSubscriber;
import com.example.publishSubscriber.Entity.PublishedData;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishedDataRepository extends MongoRepository<PublishedData, String> {
    List<PublishedData> findByPublishMasterIdIn(List<String> publishMasterIds);

     // Find all records where fetchedForBroker is false
    //  List<PublishedData> findByFetchedForBrokerFalse();

    //  List<PublishedData> findByFetchedForBrokerFalseAndPublishMasterIdIn(List<String> publishSectorIds);

    @Query("{'publishMasterId': { $in: ?1 }, ?0: false}")
    List<PublishedData> findByUsernameAndFetchedForBrokerFalse(String columnName, List<String> publishMasterIds);


}

