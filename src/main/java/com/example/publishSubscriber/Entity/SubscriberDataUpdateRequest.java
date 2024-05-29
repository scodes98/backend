package com.example.publishSubscriber.Entity;

import java.util.List;

public class SubscriberDataUpdateRequest {
    private String subscriberUsername;
    private List<String> publishSectorIds;

    // Constructors, getters, and setters...

    public String getSubscriberUsername() {
        return subscriberUsername;
    }

    public void setSubscriberUsername(String subscriberUsername) {
        this.subscriberUsername = subscriberUsername;
    }

    public List<String> getPublishSectorIds() {
        return publishSectorIds;
    }

    public void setPublishSectorIds(List<String> publishSectorIds) {
        this.publishSectorIds = publishSectorIds;
    }
}

