package com.example.publishSubscriber.Service;

import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    private String globalIpAddress;

    public String getGlobalIpAddress() {
        return globalIpAddress;
    }

    public void setGlobalIpAddress(String globalIpAddress) {
        this.globalIpAddress = globalIpAddress;
    }
}