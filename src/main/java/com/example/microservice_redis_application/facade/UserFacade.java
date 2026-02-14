package com.example.microservice_redis_application.facade;

import com.example.microservice_redis_application.model.User;
import com.example.microservice_redis_application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.rmi.server.LogStream.log;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFacade {
    private final UserService userService;
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;
    public User addNewUser(User newUser) {
        ValueOperations<String,String> valueOps= redisTemplate.opsForValue();
        try {
            User user=userService.addNewUser(newUser);
            valueOps.set("user:" + user.getId(), objectMapper.writeValueAsString(user),100, TimeUnit.SECONDS);
            return user;
        }catch (Exception ex){
            throw new RuntimeException("json conversion exception:"+ex.getMessage());
        }
    }
    public User viewUserById(Long userId){
        ValueOperations<String,String> valueOps= redisTemplate.opsForValue();
        String existingUser=valueOps.get("user:"+userId);
        if(Objects.nonNull(existingUser)){
            try {
                log("redis hit");
                System.out.println("redis");
                return objectMapper.readValue(existingUser, User.class);
            }catch (Exception ex){
                throw new RuntimeException(ex.getMessage());
            }

        }else{
            Optional<User> user=userService.findByUserId(userId);
            if(user.isPresent()){
                try {
                    valueOps.set("user:"+user.get().getId(),objectMapper.writeValueAsString(user.get()),100,TimeUnit.SECONDS);
                    return user.get();
                }catch (Exception ex){
                    throw new RuntimeException(ex.getMessage());
                }
            }else{
                throw new RuntimeException("User Not found");
            }
        }
    }
}
