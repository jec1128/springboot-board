package com.ChoiSW.portfolio.service;

import com.ChoiSW.portfolio.entity.Board;
import com.ChoiSW.portfolio.entity.User;
import com.ChoiSW.portfolio.repository.BoardRepository;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Board save(String userName, Board board) {
        User user = userRepository.findByUserName(userName);
        board.setUser(user);
        LocalDateTime currentTime = LocalDateTime.now();
        board.setCreatedDate(currentTime);
        board.setUpdatedDate(currentTime);
        board.setViewCount(0);
        board.setIsDeleted(false);
        return boardRepository.save(board);
    }

    @Transactional
    public void updateViewCount(Long boardId){
        boardRepository.updateViewCount(boardId);
    }

    @Transactional
    public boolean isDeleted(Long boardId){
        boardRepository.updateIsDeleted(boardId);
        return true;
    }

}
