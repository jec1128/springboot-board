package com.ChoiSW.portfolio.service;


import com.ChoiSW.portfolio.entity.Role;
import com.ChoiSW.portfolio.entity.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    id 와 비밀번호만으로는 회원가입이 안된다
    그래서 이 세이브 함수안에
    권한설정과 비밀번호 암호화등 나머지 컬럼들을 채워주는 역할
     */
    @Transactional
    public int save(User user, int authority) throws Exception {
        try {
            // encoding the password
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);

            LocalDateTime currentTime = LocalDateTime.now();
            user.setCreatedDate(currentTime);

            // set the user role
            user.setUserEnabled(true);
            user.setIsDeleted(false);
            Role role = new Role();
            role.setRoleId(authority);   //1이 user 2가 admin
            user.getRoleList().add(role);
            userRepository.save(user);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("userService : save 함수안 " + e.getMessage());
        }
        return -1;
    }

    @Transactional
    public boolean isDeleted(Long userId){
        userRepository.updateisDeleted(userId);
        return true;
    }

}
