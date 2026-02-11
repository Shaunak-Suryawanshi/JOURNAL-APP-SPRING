package com.shaunak.journal.repository;

import com.shaunak.journal.entity.JournalEntry;
import com.shaunak.journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface UserRepository extends MongoRepository<User, ObjectId> {

     User findByUserName(String userName);

     void deleteByUserName(String userName);


}
