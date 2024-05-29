package com.example.publishSubscriber.Service;

import com.example.publishSubscriber.Entity.Publisher;
import com.example.publishSubscriber.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher getPublisherByUsername(String username) {
        return publisherRepository.findByUsername(username);
    }

    public boolean validatePublisherCredentials(String username, String password) {
        Publisher publisher = publisherRepository.findByUsername(username);
        return publisher != null && publisher.getPassword().equals(password);
    }

    public void savePublisher(Publisher publisher) {
        publisher.setTimestamp(new Date()); // Set the current timestamp when saving
        publisherRepository.save(publisher);
    }
   
}
