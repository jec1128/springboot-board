package com.ChoiSW.portfolio.repository;

import com.ChoiSW.portfolio.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

     //clearautomatically : 캐쉬초기화 같은 의미임 , 즉 create, update, delete 후엔 해줘야함.

     Page<Board> findBoardByIsDeletedFalseAndTitleContainingAndUserIsDeletedFalseOrIsDeletedFalseAndContentContainingAndUserIsDeletedFalse(String title, String content, Pageable pageable);


     /*@Query("UPDATE Board b SET b.isDeleted = true WHERE b.boardId = :boardId")
          void updateIsDeleted(Long boardId);*/
}