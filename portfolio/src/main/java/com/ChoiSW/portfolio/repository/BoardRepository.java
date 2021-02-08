package com.ChoiSW.portfolio.repository;

import com.ChoiSW.portfolio.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardByTitle(String title);
    List<Board> findBoardByTitleOrContent(String title, String content);
    Page<Board> findBoardByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}