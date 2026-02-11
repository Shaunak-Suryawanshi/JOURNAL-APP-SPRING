package com.shaunak.journal.repository;

import com.shaunak.journal.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmail() {
        emailService.sendSimple("shaunak436@gmail.com" ,"TESTING JAVA MAIL" ,"HELLOOOO");
    }
}
