package com.shaunak.journal.scheduler;

import com.shaunak.journal.cache.AppCache;
import com.shaunak.journal.entity.JournalEntry;
import com.shaunak.journal.entity.User;
import com.shaunak.journal.enums.Sentiment;
import com.shaunak.journal.model.SentimentData;
import com.shaunak.journal.repository.UserRepositoryImpl;
import com.shaunak.journal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String , SentimentData> kafkaTemplate;




    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
           Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days" + mostFrequentSentiment).build();
                try{
                    kafkaTemplate.send("topic_0" , sentimentData.getEmail() , sentimentData);
                }catch (Exception e){
                    emailService.sendSimple(sentimentData.getEmail(),"Sentiment for prev week",sentimentData.getSentiment());
                }

            }

        }
    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
