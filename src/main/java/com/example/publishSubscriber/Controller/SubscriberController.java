package com.example.publishSubscriber.Controller;

import com.example.publishSubscriber.Entity.PublishedData;
import com.example.publishSubscriber.Entity.Subscriber;
import com.example.publishSubscriber.Model.PublishSectorRequest;
import com.example.publishSubscriber.Model.ValidationResponse;
import com.example.publishSubscriber.Service.PublishedDataService;
import com.example.publishSubscriber.Service.SubscriberService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/registerSubscriber")
    public ResponseEntity<ValidationResponse> registerSubscriber(@RequestBody Subscriber credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Check if the username already exists
        if (subscriberService.getSubscriberByUsername(username) != null) {
            String errorMessage = "Conflict: Username already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ValidationResponse(HttpStatus.CONFLICT.value(), errorMessage));
        }

        // Create a new subscriber and save it to the database
        Subscriber newSubscriber = new Subscriber(username, password);
        subscriberService.saveSubscriber(newSubscriber);

        String successMessage = "Success: Subscriber registered successfully";
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(HttpStatus.CREATED.value(), successMessage));
    }

    @PostMapping("/validateSubscriber")
    public ResponseEntity<ValidationResponse> validateSubscriber(@RequestBody Subscriber credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        if (subscriberService.validateSubscriberCredentials(username, password)) {
            return ResponseEntity.ok(new ValidationResponse(HttpStatus.OK.value(), "Success: Subscriber credentials are valid"));
        } else {
            Subscriber subscriber = subscriberService.getSubscriberByUsername(username);
            if (subscriber != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ValidationResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Incorrect password"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ValidationResponse(HttpStatus.NOT_FOUND.value(), "Not Found: Subscriber not found"));
            }
        }
    }
    
}

