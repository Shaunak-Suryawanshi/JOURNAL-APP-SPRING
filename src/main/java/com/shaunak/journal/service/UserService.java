package com.shaunak.journal.service;

import com.shaunak.journal.entity.User;
import com.shaunak.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user) {
        try{
            user.setPassword( passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public void saveAdmin(User user) {
           user.setPassword( passwordEncoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
    }

    public void saveUser(User user) {

        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findByUserName(String userName) {
       return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);

    }
}
