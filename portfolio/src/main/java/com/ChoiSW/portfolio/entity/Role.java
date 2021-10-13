package com.ChoiSW.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String roleName;

    @ManyToMany(mappedBy = "roleList")   //model/User 안에 있는 컬럼이름(private List<Role> roleList;)
    @JsonIgnore
    private List<User> userList = new ArrayList<>();
}
