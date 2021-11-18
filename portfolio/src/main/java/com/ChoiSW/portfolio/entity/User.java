package com.ChoiSW.portfolio.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//더티체킹으로 인해서 업데이트 되는 컬럼만 수정하도록 하는 어노테이션
//@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String userPassword;
    private Boolean userEnabled;
    private LocalDateTime createdDate;
    private Boolean isDeleted;

    private String role; // ROLE_ADMIN, ROLE_USER



//    @ManyToOne
//    @JoinTable(
//            name = "user_role",       //(테이블이름)
//            joinColumns = @JoinColumn(name = "user_id"),   //테이블 외래키
//            inverseJoinColumns = @JoinColumn(name = "role_id") //반대쪽 테이블 외래키
//    )
//    private Role role;



}
