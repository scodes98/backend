package com.example.publishSubscriber.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mappedDataForSubscriber")
public class MappedDataForSubscriber {

    @Id
    private String id;

    private String publishMasterId;
    private String publishSector;
    private int offset;
    private LocalDateTime timestamp;
    private String username;

    // Constructors
    public MappedDataForSubscriber() {
        // Default constructor
    }

    public MappedDataForSubscriber(String publishMasterId, String publishSector, String username) {
        this.publishMasterId = publishMasterId;
        this.publishSector = publishSector;
        this.offset = 0; // Default offset value, you can modify this based on your logic
        this.timestamp = LocalDateTime.now(); // Set the timestamp to the current date and time
        this.username = username;
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

    public String getPublishSector() {
        return publishSector;
    }

    public void setPublishSector(String publishSector) {
        this.publishSector = publishSector;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
