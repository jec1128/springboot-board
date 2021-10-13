package com.ChoiSW.portfolio.repository;


import com.ChoiSW.portfolio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository <User,Long>{

    User findByUserName(String userName);
    Page<User> findUserByUserNameContainsAndIsDeletedFalse(String userName, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.userId = :userId")
    void updateisDeleted(Long userId);

}
