package com.example.publishSubscriber.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;

import com.example.publishSubscriber.Model.PublishDataApiBrokerRequest;

@Document(collection = "apiLogs")
public class ApiLogSendSector {

    @Id
    private String id;
    private HttpEntity<List<PublishSectorOffset>> request;
    private String response;
    private String apiName;  // New field for API name
    private long timestamp;

    // Constructors, getters, setters...

    public ApiLogSendSector(HttpEntity<List<PublishSectorOffset>> request, String response, String apiName, long timestamp) {
        this.request = request;
        this.response = response;
        this.apiName = apiName;
        this.timestamp = timestamp;
    }

    // Getters and setters...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HttpEntity<List<PublishSectorOffset>> getRequest() {
        return request;
    }

    public void setRequest(HttpEntity<List<PublishSectorOffset>> request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
