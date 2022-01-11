package com.ChoiSW.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterDTO {
    private String userName;
    private String userPassword;
    private int userAuthority;   //1이 유저, 2가 admin
}
