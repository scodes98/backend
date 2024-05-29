package com.example.publishSubscriber.Repository;


import com.example.publishSubscriber.Entity.ApiLogSendSector;
import com.example.publishSubscriber.Entity.BrokerIpAddress;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrokerIpAddressRepository extends MongoRepository<BrokerIpAddress, String> {
    // You can add custom query methods if needed
}
