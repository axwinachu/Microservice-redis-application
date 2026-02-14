package com.example.microservice_redis_application.controller;

import com.example.microservice_redis_application.facade.UserFacade;
import com.example.microservice_redis_application.model.User;
import com.example.microservice_redis_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserFacade userFacade;
    @PostMapping
    public User addNewUser(@RequestBody User user){
        return userFacade.addNewUser(user);
    }
    @GetMapping("/{id}")
    public User viewUserById(@PathVariable Long id){
        return userFacade.viewUserById(id);
    }

}
