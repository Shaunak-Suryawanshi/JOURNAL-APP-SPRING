package com.shaunak.journal.service;

import com.shaunak.journal.enums.Sentiment;
import com.shaunak.journal.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {


    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "topic_0" , groupId = "topic_0-group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendSimple(sentimentData.getEmail() ,"Sentiment for prev week", sentimentData.getSentiment());
    }
}
