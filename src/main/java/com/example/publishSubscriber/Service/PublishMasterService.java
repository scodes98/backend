package com.example.publishSubscriber.Service;

import com.example.publishSubscriber.Entity.PublishMaster;
import com.example.publishSubscriber.Repository.PublishMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublishMasterService {

    @Autowired
    private PublishMasterRepository publishMasterRepository;

    public void insertPublishSectors(List<String> publishSectors) {
        for (String sector : publishSectors) {
            PublishMaster publishMaster = new PublishMaster(sector);
            publishMasterRepository.save(publishMaster);
        }
    }

    public List<PublishMaster> getAllPublishMasterWithFlagTrue() {
        return publishMasterRepository.findByFlagIsTrue();
    }

}

