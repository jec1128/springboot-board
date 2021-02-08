package com.ChoiSW.portfolio.service;

import com.ChoiSW.portfolio.model.Board;
import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.BoardRepository;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String userName, Board board) {
        User user = userRepository.findByUserName(userName);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
