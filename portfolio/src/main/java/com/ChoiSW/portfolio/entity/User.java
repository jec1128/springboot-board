package com.ChoiSW.portfolio.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String userPassword;
    private Boolean userEnabled;
    private LocalDateTime createdDate;
    private Boolean isDeleted;


    @ManyToOne
    @JoinTable(
            name = "user_role",       //(테이블이름)
            joinColumns = @JoinColumn(name = "user_id"),   //테이블 외래키
            inverseJoinColumns = @JoinColumn(name = "role_id") //반대쪽 테이블 외래키
    )
    private Role role;


}
