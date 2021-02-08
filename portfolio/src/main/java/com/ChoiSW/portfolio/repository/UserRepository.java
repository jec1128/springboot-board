package com.ChoiSW.portfolio.repository;

import com.ChoiSW.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long>{

    User findByUserName(String userName);
}
