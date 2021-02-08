package com.ChoiSW.portfolio.service;


import com.ChoiSW.portfolio.model.Role;
import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        user.setUserEnabled(true);

        Role role = new Role();
        role.setRoleId(1);
        user.getRoleList().add(role);
        System.out.println("userservice save함수안");
        return userRepository.save(user);
    }


}
