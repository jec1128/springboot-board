package com.ChoiSW.portfolio.repository;


import com.ChoiSW.portfolio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User,Long>{

    Optional<User> findByUserName(String username);
    Page<User> findUserByUserNameContainsAndIsDeletedFalse(String username, Pageable pageable);

}
