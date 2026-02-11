package com.shaunak.journal.repository;

import com.shaunak.journal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;

    }
}
