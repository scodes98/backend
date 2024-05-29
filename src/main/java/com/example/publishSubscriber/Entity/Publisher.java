package com.example.publishSubscriber.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "publishers")
public class Publisher {

    @Id
    private String id;
    private String username;
    private String password;
    private Date timestamp;
    private boolean flag;

    public Publisher(String username, String password) {
        this.username = username;
        this.password = password;
        this.timestamp = new Date(); // Set the current date as the default timestamp
        this.flag = true; // Set the default value for the flag
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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
        return "Publisher{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", timestamp=" + timestamp +
                ", flag=" + flag +
                '}';
    }
}
