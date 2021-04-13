package com.ChoiSW.portfolio.service;

import com.ChoiSW.portfolio.model.Board;
import com.ChoiSW.portfolio.model.User;
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
        return boardRepository.save(board);
    }

    @Transactional
    public int updateViewCount(Long boardId){
        return boardRepository.updateViewCount(boardId);
    }
}
