package com.example.publishSubscriber.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "publishMaster")
public class PublishMaster {

    @Id
    private String id;

    @Field(name = "publish_sector")
    private String publishSector;

    private Date timestamp;
    private boolean flag;

    public PublishMaster(String publishSector) {
        this.publishSector = publishSector;
        this.timestamp = new Date(); // Set the current date as the default timestamp
        this.flag = true; // Set the default value for the flag
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishSector() {
        return publishSector;
    }

    public void setPublishSector(String publishSector) {
        this.publishSector = publishSector;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PublishMaster{" +
                "id='" + id + '\'' +
                ", publishSector='" + publishSector + '\'' +
                ", timestamp=" + timestamp +
                ", flag=" + flag +
                '}';
    }
}
