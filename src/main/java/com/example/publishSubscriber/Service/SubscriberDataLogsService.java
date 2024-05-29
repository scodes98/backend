package com.example.publishSubscriber.Service;

import com.example.publishSubscriber.Entity.SubscriberDataLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.publishSubscriber.Repository.SubscriberDataLogRepository;
import com.example.publishSubscriber.Entity.PublishedData;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SubscriberDataLogsService {

    @Autowired
    private SubscriberDataLogRepository subscriberDataLogsRepository;

    @Transactional
    public void insertSubscriberDataLogs(PublishedData publishedData) {
        // Extract the desired fields from the PublishedData object
        String publishMasterId = publishedData.getPublishMasterId();
        String publishSector = publishedData.getPublishSector();
        String publishMessage = publishedData.getPublishMessage();
        String id = publishedData.getId();
        String subscriberUsername = "null";

        // SubscriberDataLog subscriberDataLog = new SubscriberDataLog(subscriberDataLogsRepository);

        // Create a new SubscriberDataLogs object with the extracted fields
        SubscriberDataLog subscriberDataLogs = new SubscriberDataLog();
        subscriberDataLogs.setPublishMasterId(publishMasterId);
        subscriberDataLogs.setPublishSector(publishSector);
        subscriberDataLogs.setPublishedDataId(id);
        subscriberDataLogs.setPublishMessage(publishMessage);
        subscriberDataLogs.setSubscriberUsername(subscriberUsername);

        // Calculate and set the offset based on masterId
        // subscriberDataLogs.calculateAndSetOffset();

        // Insert into subscriberDataLogs table
        subscriberDataLogsRepository.save(subscriberDataLogs);
    }
}

