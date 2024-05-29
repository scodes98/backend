package com.example.publishSubscriber.Service;

import com.example.publishSubscriber.Entity.Subscriber;
import com.example.publishSubscriber.Repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber getSubscriberByUsername(String username) {
        return subscriberRepository.findByUsername(username);
    }

    public boolean validateSubscriberCredentials(String username, String password) {
        Subscriber subscriber = subscriberRepository.findByUsername(username);
        return subscriber != null && subscriber.getPassword().equals(password);
    }

    public void saveSubscriber(Subscriber subscriber) {
        subscriber.setTimestamp(new Date()); // Set the current timestamp when saving
        subscriberRepository.save(subscriber);
    }
}
