package com.example.microservice_redis_application.service;

import com.example.microservice_redis_application.model.User;
import com.example.microservice_redis_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User addNewUser(User user){
       return userRepository.save(user);
    }
    public List<User> viewUsers(){
        return userRepository.findAll();
    }
    public Optional<User> findByUserId(Long userId){
        return userRepository.findById(userId);
    }
}
