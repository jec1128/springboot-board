package com.ChoiSW.portfolio.controller;



import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    List<User> all(){
        List<User> userList = userRepository.findAll();
        return userList;
    }


    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.getBoardList().clear();
                    user.getBoardList().addAll(newUser.getBoardList());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    //newUser.setuserId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
