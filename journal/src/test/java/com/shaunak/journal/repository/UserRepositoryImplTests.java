package com.shaunak.journal.repository;


import com.shaunak.journal.repository.UserRepositoryImpl;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.shaunak.journal.repository.UserRepositoryImpl;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void test() {

        Assertions.assertNotNull(userRepository.getUserForSA());
    }

}
