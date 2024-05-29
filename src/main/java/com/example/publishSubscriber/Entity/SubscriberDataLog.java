package com.example.publishSubscriber.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Transient;

import com.example.publishSubscriber.Repository.SubscriberDataLogRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.Map;



@Document(collection = "subscriberDataLogs")
public class SubscriberDataLog {

    @Id
    private String id;
    private String publishMasterId; // Assuming publishMasterId is the non-primary key column
    private String publishedDataId;
    private String publishSector;
    private String publishMessage;
    private String subscriberUsername;
    private Long offset;
    private boolean isActive;
    private long timestamp;

    // Map to store offset values for each publishMasterId
    @Field("offsets")
    private Map<String, Long> offsets = new HashMap<>();

    @Transient
    private transient SubscriberDataLogRepository subscriberDataLogRepository;
    
    // @Autowired
    // private SubscriberDataLogRepository subscriberDataLogRepository;

    // Default constructor for Spring to instantiate the class
    public SubscriberDataLog() {
        // Default constructor
    }

    // Constructor with parameters
    public SubscriberDataLog(String id, String publishMasterId, String publishedDataId, String publishSector,
                             String publishMessage, String subscriberUsername, Long offset, boolean isActive, long timestamp) {
        this.id = id;
        this.publishMasterId = publishMasterId;
        this.publishedDataId = publishedDataId;
        this.publishSector = publishSector;
        this.publishMessage = publishMessage;
        this.subscriberUsername = subscriberUsername;
        this.offset = offset;
        this.isActive = isActive;
        this.timestamp = timestamp;
    }
    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishMasterId() {
        return publishMasterId;
    }

    public void setPublishMasterId(String publishMasterId) {
        this.publishMasterId = publishMasterId;
    }

    public String getPublishedDataId() {
        return publishedDataId;
    }

    public void setPublishedDataId(String publishedDataId) {
        this.publishedDataId = publishedDataId;
    }

    public String getPublishSector() {
        return publishSector;
    }

    public void setPublishSector(String publishSector) {
        this.publishSector = publishSector;
    }

    public String getPublishMessage() {
        return publishMessage;
    }

    public void setPublishMessage(String publishMessage) {
        this.publishMessage = publishMessage;
    }

    public String getSubscriberUsername() {
        return subscriberUsername;
    }

    public void setSubscriberUsername(String subscriberUsername) {
        this.subscriberUsername = subscriberUsername;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public void calculateAndSetOffset() {
        // Fetch the last offset value for the publishMasterId
        Long lastOffsetForMasterId = getLastOffsetValueForMasterId(publishMasterId);
    
        // Increment and set the offset in the map
        Long currentOffset = lastOffsetForMasterId + 1;
        this.offset = currentOffset;
        offsets.put(publishMasterId, currentOffset);
    }
    
    private Long getLastOffsetValueForMasterId(String publishMasterId) {
        // Implement logic to get the last offset value for a specific publishMasterId from your data store
        // You can use a repository method to find the max offset value for a given publishMasterId.
        Long lastOffset = subscriberDataLogRepository.findMaxOffsetByPublishMasterId(publishMasterId);
    
        // Ensure that if the lastOffset is null, return 0 to avoid null-related issues
        return lastOffset != null ? lastOffset : 0L;
    }


    // Other methods...
}
