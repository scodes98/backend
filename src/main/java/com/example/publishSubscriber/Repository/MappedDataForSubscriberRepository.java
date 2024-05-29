package com.example.publishSubscriber.Repository;

import com.example.publishSubscriber.Entity.MappedDataForSubscriber;
import com.example.publishSubscriber.Entity.PublishedData;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MappedDataForSubscriberRepository extends MongoRepository<MappedDataForSubscriber, String> {

    // Find the last record for a given publishMasterId, ordered by the document's default order
    MappedDataForSubscriber findFirstByPublishMasterIdOrderByTimestampDesc(String publishMasterId);

    MappedDataForSubscriber findFirstByUsernameAndPublishMasterIdOrderByTimestampDesc(String username, String publishMasterId);

}
