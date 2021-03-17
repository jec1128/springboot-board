package com.ChoiSW.portfolio.service;


import com.ChoiSW.portfolio.model.Role;
import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*public User getUserByUserName(String userName) throws Exception {
        return userRepository.findByUserName(userName);
    }*/

    /*
    id 와 비밀번호만으로는 회원가입이 안된다
    그래서 이 세이브 함수안에
    권한설정과 비밀번호 암호화등 나머지 컬럼들을 채워주는 역할
     */
    @Transactional
    public int save(User user) throws Exception {
        try {
            //< encoding the password
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);

            //< set the user role
            user.setUserEnabled(true);
            /*Role userRole = null;
            if(user.getUsername().equals("admin")) {
                userRole = roleRepository.findByRole(ERole.ADMIN.getValue());
            }
            else if(user.getUsername().equals("user")) {
                userRole = roleRepository.findByRole(ERole.MANAGER.getValue());
            }
            else {
                userRole = roleRepository.findByRole(ERole.GUEST.getValue());
            }

            //< set the user roles
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));*/

            //< save the user information and return result
            Role role = new Role();
            role.setRoleId(1);
            user.getRoleList().add(role);
            System.out.println("userService : userRespoitory.save()");
            userRepository.save(user);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("userService : save 함수안 " + e.getMessage());
        }
        return -1;
    }

}
