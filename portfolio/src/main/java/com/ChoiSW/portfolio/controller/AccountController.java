package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.model.ServiceResponse;
import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import com.ChoiSW.portfolio.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String login() {
        return "/account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/account/register";
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ServiceResponse> register(@RequestBody User user){
        System.out.println(user.getUserName() + " " + user.getUserPassword());
        User userExists = userRepository.findByUserName(user.getUserName());
        if(userExists != null) {
            System.out.println("이미 존재함 user duplicate");
            ServiceResponse<User> response = new ServiceResponse<>("duplicate", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            try {
                if(userService.save(user) == 1) {   // 1이 성공 -1이 실패
                    System.out.println("회원가입 성공 register success");
                    ServiceResponse<User> response = new ServiceResponse<>("success", user);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                else{
                    System.out.println("회원가입 실패 register error ");
                    ServiceResponse<User> response = new ServiceResponse<>("error", user);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("회원가입 실패 register error " + e.getMessage());
            }

        }
        System.out.println("회원가입 에러 발생");
        ServiceResponse<User> response = new ServiceResponse<>("error", user);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
