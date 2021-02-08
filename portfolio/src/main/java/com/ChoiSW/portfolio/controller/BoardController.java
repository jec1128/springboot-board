package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.model.Board;
import com.ChoiSW.portfolio.repository.BoardRepository;
//import com.ChoiSW.portfolio.validator.BoardValidator;
import com.ChoiSW.portfolio.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    /*@Autowired
    private BoardValidator boardValidator;*/


    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
        //Page<Board> boardList = boardRepository.findAll(pageable);
        Page<Board> boardList = boardRepository.findBoardByTitleContainingOrContentContaining(searchText,searchText,pageable);

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
        return "board/list";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false) Long boardId){

        if(boardId==null){
            model.addAttribute("board", new Board());
            System.out.println("새 글 쓰러가기");
        }
        else{
            Board board =boardRepository.findById(boardId).orElse(null);
            model.addAttribute("board", board);
            System.out.println("기존에 있던 글보기");
        }

        return "board/write";
    }

    @PostMapping("/write")
    public String writingSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        /*boardValidator.validate(board,bindingResult);

        if(bindingResult.hasErrors()){
            return "board/write";
        }*/
        if(bindingResult.hasErrors()){
            return "board/write";
        }
        String userName = authentication.getName();
        boardService.save(userName, board);


        System.out.println("write 성공");
        return "redirect:/board/list"; // 결과값을 받아서 새로운 board까지 리스트 페이지에 보이게 하도록함.
    }
}
