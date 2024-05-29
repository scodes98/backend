package com.example.publishSubscriber.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;

import com.example.publishSubscriber.Model.PublishDataApiBrokerRequest;

@Document(collection = "apiLogs")
public class ApiLog {

    @Id
    private String id;
    private HttpEntity<PublishDataApiBrokerRequest> request;
    private String response;
    private String apiName;  // New field for API name
    private long timestamp;

    // Constructors, getters, setters...

    public ApiLog(HttpEntity<PublishDataApiBrokerRequest> request, String response, String apiName, long timestamp) {
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

    public HttpEntity<PublishDataApiBrokerRequest> getRequest() {
        return request;
    }

    public void setRequest(HttpEntity<PublishDataApiBrokerRequest> request) {
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
