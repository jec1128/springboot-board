package com.ChoiSW.portfolio.repository;


import com.ChoiSW.portfolio.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{

    User findByUserName(String userName);
    Page<User> findUserByUserNameContains(String userName, Pageable pageable);

}
