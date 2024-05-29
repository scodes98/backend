package com.example.publishSubscriber.Model;

import java.util.List;

public class PublishSectorRequest {
    private List<String> publishSectors;
    private List<String> publishSectorIds;

    public List<String> getPublishSectorIds() {
        return publishSectorIds;
    }

    public void setPublishSectorIds(List<String> publishSectorIds) {
        this.publishSectorIds = publishSectorIds;
    }

    public List<String> getPublishSectors() {
        return publishSectors;
    }

    public void setPublishSectors(List<String> publishSectors) {
        this.publishSectors = publishSectors;
    }

    @Override
    public String toString() {
        return "PublishSectorRequest{" +
                "publishSectorIds=" + publishSectorIds +
                '}';
    }
}

