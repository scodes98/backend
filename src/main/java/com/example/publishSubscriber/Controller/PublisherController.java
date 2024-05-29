package com.example.publishSubscriber.Controller;

import com.example.publishSubscriber.Entity.MappedDataForSubscriber;
import com.example.publishSubscriber.Entity.PublishMaster;
import com.example.publishSubscriber.Entity.PublishSectorOffset;
import com.example.publishSubscriber.Entity.PublishedData;
import com.example.publishSubscriber.Entity.Publisher;
import com.example.publishSubscriber.Entity.SubscriberDataLog;
import com.example.publishSubscriber.Entity.SubscriberDataUpdateRequest;
import com.example.publishSubscriber.Entity.BrokerIpAddress;
import com.example.publishSubscriber.Model.PublishDataApiBrokerRequest;
import com.example.publishSubscriber.Model.PublishSectorRequest;
import com.example.publishSubscriber.Model.ValidationResponse;
import com.example.publishSubscriber.Model.BrokerIpRequest;
import com.example.publishSubscriber.Repository.PublishedDataRepository;
import com.example.publishSubscriber.Repository.SubscriberDataLogRepository;
import com.example.publishSubscriber.Repository.BrokerIpAddressRepository;
import com.example.publishSubscriber.Service.GlobalService;
import com.example.publishSubscriber.Service.PublishMasterService;
import com.example.publishSubscriber.Service.PublishedDataService;
import com.example.publishSubscriber.Service.PublisherService;
import com.example.publishSubscriber.Service.SubscriberDataLogsService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/publisher")
public class PublisherController {

    private final GlobalService globalService;

    @Autowired
    private PublisherService publisherService;
    @Autowired
    private PublishMasterService publishMasterService;
    @Autowired
    private PublishedDataService publishedDataService;
    @Autowired
    private SubscriberDataLogsService subscriberDataLogsService;
    @Autowired
    private PublishedDataRepository publishedDataRepository;
    @Autowired
    private SubscriberDataLogRepository subscriberDataLogRepository;
    @Autowired
    private BrokerIpAddressRepository brokerIpAddressRepository;

    public PublisherController(GlobalService globalService) {
        this.globalService = globalService;
    }

    @PostMapping("/registerPublisher")
    public ResponseEntity<ValidationResponse> registerPublisher(@RequestBody Publisher credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
    
        // Check if the username already exists
        if (publisherService.getPublisherByUsername(username) != null) {
            String errorMessage = "Conflict: Username already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ValidationResponse(HttpStatus.CONFLICT.value(), errorMessage));
        }
    
        // Create a new publisher and save it to the database
        Publisher newPublisher = new Publisher(username, password);
        publisherService.savePublisher(newPublisher);
    
        String successMessage = "Success: Publisher registered successfully";
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(HttpStatus.CREATED.value(), successMessage));
    }
    
    @PostMapping("/validatePublisher")
    public ResponseEntity<ValidationResponse> validatePublisher(@RequestBody Publisher credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
    
        if (publisherService.validatePublisherCredentials(username, password)) {
            return ResponseEntity.ok(new ValidationResponse(HttpStatus.OK.value(), "Success: Publisher credentials are valid"));
        } else {
            Publisher publisher = publisherService.getPublisherByUsername(username);
            if (publisher != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ValidationResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Incorrect password"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ValidationResponse(HttpStatus.NOT_FOUND.value(), "Not Found: Publisher not found"));
            }
        }
    }

    @PostMapping("/dumpPublishMaster")
    public ResponseEntity<ValidationResponse> dumpPublishMaster(@RequestBody PublishSectorRequest request) {
        List<String> publishSectors = request.getPublishSectors();
        publishMasterService.insertPublishSectors(publishSectors);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(HttpStatus.CREATED.value(), "Published sectors inserted into publishMaster table."));
    }

    @GetMapping("/fetchPublishMaster")
    public ResponseEntity<List<PublishMaster>> getPublishMasterWithFlagTrue() {
        List<PublishMaster> publishMasters = publishMasterService.getAllPublishMasterWithFlagTrue();
        return ResponseEntity.ok(publishMasters);
    }

    //initial code before external api integration
    //  @PostMapping("/publishData")
    // public ResponseEntity<ValidationResponse> publishData(@RequestBody PublishedData data) {
    //     try {
    //         // Insert into PublishedData table
    //         PublishedData insertedData = publishedDataService.insertPublishedData(data);
    
    //         // Insert into subscriberDataLogs table
    //         subscriberDataLogsService.insertSubscriberDataLogs(insertedData);

    //         // Call the common service method
    //         publishedDataService.sendPublishDataToBrokerAndLog(insertedData);
    
    //         return ResponseEntity.status(HttpStatus.CREATED)
    //             .body(new ValidationResponse(HttpStatus.CREATED.value(), "Data published successfully."));
    //     } catch (RuntimeException e) {
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //             .body(new ValidationResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    //     }
    // }

    @PostMapping("/publishData")
    public ResponseEntity<ValidationResponse> publishData(@RequestBody PublishedData data) {
        try {
            // Insert into PublishedData table
            PublishedData insertedData = publishedDataService.insertPublishedData(data);
    
            // Insert into subscriberDataLogs table
            subscriberDataLogsService.insertSubscriberDataLogs(insertedData);
    
            // Call the common service method and get the response
            String responseFromService = publishedDataService.sendPublishDataToBrokerAndLog(insertedData);
    
            // Return the response received from the service
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ValidationResponse(HttpStatus.CREATED.value(), responseFromService));
        } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/sendPublishDataToBrokerEndpoint")
    public ResponseEntity<String> externalApiEndpoint(@RequestBody PublishDataApiBrokerRequest apiRequest) {
        try {
            // Your logic to process the received data
            String responseMessage = "API Request Processed Successfully";
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the API request: " + e.getMessage());
        }
    }

    @PostMapping("/sendSectorNameAndOffsetToBrokerEndpoint")
    public ResponseEntity<String> sendSectorNameAndOffsetToBrokerEndpoint(@RequestBody List<MappedDataForSubscriber> apiRequest) {
        try {
            // Your logic to process the received data
            String responseMessage = "API Request Processed Successfully";
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the API request: " + e.getMessage());
        }
    }

    // @GetMapping("/fetchMappedDataForSubscriber")
    // public String fetchMappedDataForSubscriber() {
    //     return publishedDataService.fetchAndStoreMappedDataForSubscriber();
    // }


    //  @PostMapping("/fetchMappedDataForSubscriber")
    // public List<PublishSectorOffset> fetchLatestPublishedData(@RequestBody SubscriberDataUpdateRequest updateRequest) {
    //         return publishedDataService.fetchAndStoreMappedDataForSubscriber(updateRequest);

    //     // try {
    //     //     String response = 
    
    //     //     if (response.contains("Failed")) {
    //     //         return new ResponseEntity<>("Error processing request: " + response, HttpStatus.BAD_GATEWAY);
    //     //     } else {
    //     //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     //     }
    //     // } catch (Exception e) {
    //     //     return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     // }
    // }


    @PostMapping("/fetchMappedDataForSubscriber")
    public ResponseEntity<String> fetchLatestPublishedData(@RequestBody SubscriberDataUpdateRequest updateRequest) {
        try {
            String response = publishedDataService.fetchAndStoreMappedDataForSubscriber(updateRequest);
    
            if (response.contains("Failed")) {
                return new ResponseEntity<>("Error processing request: " + response, HttpStatus.BAD_GATEWAY);
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fetchBrokerIp")
    public ResponseEntity<String> fetchBrokerIp(@RequestBody BrokerIpRequest request) {
        try {
            // Extract IP address from the request
            String ipAddress = request.getIpAddress();
            System.out.println("Broker address saved successfully : " + ipAddress);

            globalService.setGlobalIpAddress(ipAddress);

            // Create and save BrokerIpAddress entity
            BrokerIpAddress brokerIpAddress = new BrokerIpAddress(ipAddress);
            // Set the createdAt timestamp manually
            brokerIpAddress.setCreatedAt(new Date());
            brokerIpAddressRepository.save(brokerIpAddress);

            return new ResponseEntity<>("IP address saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // @PostMapping("/fetchPublishedDataById")
    // public ResponseEntity<List<PublishedData>> fetchPublishedDataById(@RequestBody PublishSectorRequest request) {
    //     List<String> publishSectorIds = request.getPublishSectorIds();

    //     // Validate and handle empty request
    //     if (publishSectorIds == null || publishSectorIds.isEmpty()) {
    //         String errorMessage = "Bad Request: publishSectorIds cannot be empty";
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
    //     }

    //     List<PublishedData> result = publishedDataService.fetchDataByPublishMasterIds(publishSectorIds);

    //     // Check if any data is found
    //     if (result.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
    //     }

    //     return ResponseEntity.ok(result);
    // }

    // @PostMapping("/fetchsubscriberDataLog")
    // public ResponseEntity<List<SubscriberDataLog>> fetchSubscriberDataLog(
    //         @RequestBody SubscriberDataUpdateRequest updateRequest) {

    //     String newSubscriberUsername = updateRequest.getSubscriberUsername();
    //     List<String> publishSectorIds = updateRequest.getPublishSectorIds();
    
    //     // Validate and handle empty request
    //     if (publishSectorIds == null || publishSectorIds.isEmpty()) {
    //         String errorMessage = "Bad Request: publishSectorIds cannot be empty";
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
    //     }
    
    //     // Fetch data from SubscriberDataLog based on publishMasterIds
    //     List<SubscriberDataLog> dataLogs = subscriberDataLogRepository.findByPublishMasterIdIn(publishSectorIds);
    
    //     // Check if any data is found
    //     if (dataLogs.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
    //     }
    
    //     // Update fields in fetched records
    //     if (newSubscriberUsername != null) {
    //         dataLogs.forEach(log -> {
    //             // Update fields based on request values
    //             log.setSubscriberUsername(newSubscriberUsername);
    //             log.setActive(true);
    //             log.setTimestamp(System.currentTimeMillis());
    //         });
    //     }
    
    //     // Save the updated data logs to the repository
    //     subscriberDataLogRepository.saveAll(dataLogs);
    
    //     return ResponseEntity.ok(dataLogs);
    // }

//     @PostMapping("/fetchsubscriberDataLog")
// public ResponseEntity<List<SubscriberDataLog>> fetchSubscriberDataLog(
//         @RequestBody SubscriberDataUpdateRequest updateRequest) {

//     String newSubscriberUsername = updateRequest.getSubscriberUsername();
//     List<String> publishSectorIds = updateRequest.getPublishSectorIds();

//     // Validate and handle empty request
//     if (publishSectorIds == null || publishSectorIds.isEmpty()) {
//         String errorMessage = "Bad Request: publishSectorIds cannot be empty";
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
//     }

//     // Fetch data from SubscriberDataLog based on publishMasterIds
//     List<SubscriberDataLog> dataLogs = subscriberDataLogRepository.findByPublishMasterIdIn(publishSectorIds);

//     // Check if any data is found
//     if (dataLogs.isEmpty()) {
//         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
//     }

//    // Update fields in fetched records and increment offset based on publishMasterId
//     Map<String, Long> masterIdToOffsetMap = new HashMap<>();
//     if (newSubscriberUsername != null) {
//         dataLogs.forEach(log -> {
//             // Update fields based on request values
//             log.setSubscriberUsername(newSubscriberUsername);
//             log.setActive(true);
//             log.setTimestamp(System.currentTimeMillis());

//             // Increment offset based on publishMasterId
//             String publishMasterId = log.getPublishMasterId();
//             long currentOffset = masterIdToOffsetMap.getOrDefault(publishMasterId, 0L);
//             log.setOffset(currentOffset + 1);
//             masterIdToOffsetMap.put(publishMasterId, currentOffset + 1);
//         });
//     }

//     // Save the updated data logs to the repository
//     subscriberDataLogRepository.saveAll(dataLogs);


//     return ResponseEntity.ok(dataLogs);
// }


 



//     @PostMapping("/fetchLatestPublishedData")
//     public ResponseEntity<List<SubscriberDataLog>> fetchLatestPublishedData(
//         @RequestBody SubscriberDataUpdateRequest updateRequest) {

//     String newSubscriberUsername = updateRequest.getSubscriberUsername();
//     List<String> publishSectorIds = updateRequest.getPublishSectorIds();

//     // Validate and handle empty request
//     if (publishSectorIds == null || publishSectorIds.isEmpty()) {
//         String errorMessage = "Bad Request: publishSectorIds cannot be empty";
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
//     }

//     // Fetch data from SubscriberDataLog based on publishMasterIds, subscriberUsername="null", isActive=false
//     List<SubscriberDataLog> dataLogs = subscriberDataLogRepository.findByPublishMasterIdInAndSubscriberUsernameAndIsActive(
//             publishSectorIds, "null", false);

//     // Check if any data is found
//     if (dataLogs.isEmpty()) {
//         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
//     }

//     // Update fields in fetched records
//     if (newSubscriberUsername != null) {
//         dataLogs.forEach(log -> {
//             // Update fields based on request values
//             log.setSubscriberUsername(newSubscriberUsername);
//             log.setActive(true);
//             log.setTimestamp(System.currentTimeMillis());
//         });
//     }

//     // Save the updated data logs to the repository
//     subscriberDataLogRepository.saveAll(dataLogs);

//     return ResponseEntity.ok(dataLogs);
// }

    

}
