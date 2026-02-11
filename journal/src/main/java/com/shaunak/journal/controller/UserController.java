package com.shaunak.journal.controller;


import com.shaunak.journal.api.response.WeatherResponse;
import com.shaunak.journal.entity.User;
import com.shaunak.journal.repository.UserRepository;
import com.shaunak.journal.service.UserService;
import com.shaunak.journal.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Tag(name = "User API's" , description = "read , update , delete")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Pune");
        String greeting=" ";
        if(weatherResponse != null) {
            greeting = "Weather feels liken " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("hi"+authentication.getName()+greeting,HttpStatus.OK);
    }


}


