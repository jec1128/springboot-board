package com.ChoiSW.portfolio.service;


import com.ChoiSW.portfolio.dto.RegisterDTO;
import com.ChoiSW.portfolio.entity.User;
import com.ChoiSW.portfolio.error.exception.DuplicateIdException;
import com.ChoiSW.portfolio.error.exception.NotExistedException;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean register(RegisterDTO registerDTO) {
        System.out.println(registerDTO.getUserName());

        Optional<User> userExists = userRepository.findByUserName(registerDTO.getUserName());


        if(userExists.isPresent()){
            System.out.println("이미 존재함 user duplicate");
            throw new DuplicateIdException("user duplicate exception");

        }

        return save(registerDTO); //1이 성공

    }
    /*
    id 와 비밀번호만으로는 회원가입이 안된다
    그래서 이 세이브 함수안에
    권한설정과 비밀번호 암호화등 나머지 컬럼들을 채워주는 역할
     */
    @Transactional
    public boolean save(RegisterDTO registerDTO) {

        User user = new User();
        user.setUserName(registerDTO.getUserName());
        String encodedPassword = passwordEncoder.encode(registerDTO.getUserPassword());
        user.setUserPassword(encodedPassword);

        if(registerDTO.getUserAuthority() == 1)
            user.setRole("ROLE_USER");
        else
            user.setRole("ROLE_ADMIN");

        user.setCreatedDate(LocalDateTime.now());

        // set the user role
        user.setUserEnabled(true);
        user.setIsDeleted(false);
        userRepository.save(user);

        System.out.println(user.getUserName()+" 님이 회원가입에 성공하셨습니다");
        return true;
    }


    @Transactional
    public boolean isDeleted(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new NotExistedException("user not existed excpetion"));
        user.setIsDeleted(true);
        return true;
    }

}
