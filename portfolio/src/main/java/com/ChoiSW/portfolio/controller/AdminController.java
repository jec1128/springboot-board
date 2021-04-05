package com.ChoiSW.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/main")
    public String main(){
        System.out.println("admin main 페이지로 이동");
        return "/admin/main";
    }
}
