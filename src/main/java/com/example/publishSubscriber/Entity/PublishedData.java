package com.example.publishSubscriber.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "publishedData")
public class PublishedData {

    @Id
    private String id;
    private String publishMasterId;
    private String publishSector;
    private String publishMessage;
    private boolean fetchedForBroker;
    private boolean siddhesh; // New column
    private boolean piyush; // New column

    // Constructors
    public PublishedData() {
        // Default constructor
        this.fetchedForBroker = false;
        this.siddhesh = false; // Set the default value to false
        this.piyush = false; // Set the default value to false
    }

    public PublishedData(String id, String publishMasterId, String publishSector, String publishMessage, boolean fetchedForBroker, boolean siddhesh, boolean piyush) {
        this.id = id;
        this.publishMasterId = publishMasterId;
        this.publishSector = publishSector;
        this.publishMessage = publishMessage;
        this.fetchedForBroker = fetchedForBroker;
        this.siddhesh = siddhesh;
        this.piyush = piyush;
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

    public String getPublishMessage() {
        return publishMessage;
    }

    public void setPublishMessage(String publishMessage) {
        this.publishMessage = publishMessage;
    }

    public boolean isFetchedForBroker() {
        return fetchedForBroker;
    }

    public void setFetchedForBroker(boolean fetchedForBroker) {
        this.fetchedForBroker = fetchedForBroker;
    }

    public boolean isSiddhesh() {
        return siddhesh;
    }

    public void setSiddhesh(boolean siddhesh) {
        this.siddhesh = siddhesh;
    }

    public boolean isPiyush() {
        return piyush;
    }

    public void setPiyush(boolean piyush) {
        this.piyush = piyush;
    }

    // Other methods...
}
