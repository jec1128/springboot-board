package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.dto.RegisterForm;
import com.ChoiSW.portfolio.entity.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import com.ChoiSW.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RegisterForm registerForm){
        System.out.println(registerForm.getUserName() + "  " + registerForm.getUserPassword() + "  " + " 권한 : " + registerForm.getUserAuthority());
        User user = new User();
        user.setUserName(registerForm.getUserName());
        user.setUserPassword(registerForm.getUserPassword());
        User userExists = userRepository.findByUserName(user.getUserName());

        if(userExists != null) {
            System.out.println("이미 존재함 user duplicate");
            return new ResponseEntity<>("{}", HttpStatus.CONFLICT);
        }
        else {
            try {
                if(userService.save(user,registerForm.getUserAuthority()) == 1) {   // 1이 성공 -1이 실패
                    System.out.println("userName : " +user.getUserName() + "  userPassword : " +user.getUserPassword() +  " 회원가입 성공 register success");
                    return new ResponseEntity<>("{}", HttpStatus.OK);
                }
                else{
                    System.out.println("회원가입 실패 register fail");
                    return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("회원가입 실패 register error " + e.getMessage());
            }

        }
        System.out.println("회원가입 에러 발생");
        return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);

    }


}
