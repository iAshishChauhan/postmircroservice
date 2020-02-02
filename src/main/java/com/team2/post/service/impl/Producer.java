package com.team2.post.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.post.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    public void kafkaProducer(NotificationDto notificationDto)
    {
        try {
            kafkaTemplate.send("FBListener", (new ObjectMapper()).writeValueAsString(notificationDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
