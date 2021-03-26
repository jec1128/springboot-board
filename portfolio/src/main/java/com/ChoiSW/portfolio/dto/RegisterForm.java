package com.ChoiSW.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    private String userName;
    private String userPassword;
    private int userAuthority;
}
