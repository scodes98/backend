package com.example.publishSubscriber.Model;


public class PublishDataApiBrokerRequest {
    private String publishSector;
    private String publishMessage;

    // Default constructor
    public PublishDataApiBrokerRequest() {
    }

    // Parameterized constructor
    public PublishDataApiBrokerRequest(String publishSector, String publishMessage) {
        this.publishSector = publishSector;
        this.publishMessage = publishMessage;
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

      // Override toString method for easy printing
      @Override
      public String toString() {
          return "ExternalApiRequest{" +
                  "publishSector='" + publishSector + '\'' +
                  ", publishMessage='" + publishMessage + '\'' +
                  '}';
      }

    // Additional methods or overrides can be added as needed...
}
