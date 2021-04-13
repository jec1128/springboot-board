package com.ChoiSW.portfolio.repository;

import com.ChoiSW.portfolio.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

     List<Board> findBoardByTitle(String title);
     List<Board> findBoardByTitleOrContent(String title, String content);
     Page<Board> findBoardByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

     @Modifying
     @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.boardId = :boardId")
     int updateViewCount(Long boardId);
}