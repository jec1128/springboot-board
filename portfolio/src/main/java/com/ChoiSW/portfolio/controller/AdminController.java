package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.model.Board;
import com.ChoiSW.portfolio.model.User;
import com.ChoiSW.portfolio.repository.BoardRepository;
import com.ChoiSW.portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/main")
    public String main(){
        return "/admin/main";
    }

    @GetMapping("/userList")
    public String userList(Model model, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){

        Page<User> userList = userRepository.findUserByUserNameContains(searchText, pageable);
        int startPage = Math.max(1,userList.getPageable().getPageNumber() - 5);
        int endPage;
        if(userList.getTotalPages()==0){
            endPage=1;
        }
        else {
            endPage = Math.min(userList.getTotalPages(), userList.getPageable().getPageNumber() + 5);
        }
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("userList",userList);
        return "/admin/userList";
    }

    @GetMapping("/boardList")
    public String boardList(Model model, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String searchText){

        Page<Board> boardList = boardRepository.findBoardByTitleContainingOrContentContaining(searchText,searchText, pageable);
        int startPage = Math.max(1,boardList.getPageable().getPageNumber() - 5);
        int endPage;
        if(boardList.getTotalPages()==0){
            endPage=1;
        }
        else {
            endPage = Math.min(boardList.getTotalPages(), boardList.getPageable().getPageNumber() + 5);
        }
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        return "/admin/boardList";
    }

    @DeleteMapping("/userDelete/{id}")
    public ResponseEntity<?> userDelete(@PathVariable("id") Long userId){
        User user =userRepository.findById(userId).orElse(null);
        userRepository.delete(user);
        System.out.println("삭제 성공");
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/boardDelete/{id}")
    public ResponseEntity<?> boardDelete(@PathVariable("id") Long boardId){
        Board board =boardRepository.findById(boardId).orElse(null);
        boardRepository.delete(board);
        System.out.println("삭제 성공");
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}
