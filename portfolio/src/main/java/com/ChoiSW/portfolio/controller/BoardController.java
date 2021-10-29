package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.entity.Board;
import com.ChoiSW.portfolio.error.exception.MethodArgumentInvalidException;
import com.ChoiSW.portfolio.error.exception.InternalServerException;
import com.ChoiSW.portfolio.repository.BoardRepository;
import com.ChoiSW.portfolio.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;

//import com.ChoiSW.portfolio.validator.BoardValidator;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;



    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){

        Page<Board> boardList = boardRepository.findBoardByIsDeletedFalseAndTitleContainingAndUserIsDeletedFalseOrIsDeletedFalseAndContentContainingAndUserIsDeletedFalse(searchText,searchText,pageable);

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


    @GetMapping("/view")
    public String view(Model model, Long boardId){
        Board board =boardRepository.findById(boardId).orElse(null);
        boardService.updateViewCount(boardId);
        model.addAttribute("board", board);
        return "board/view";
    }

    @GetMapping("/write")  // write.html 보여주기
    public String write(Model model){
        model.addAttribute("board", new Board());

        return "board/write";
    }

    @PostMapping("/write") //write.html 내용을 처리하기
    public String write(@Valid Board board, Errors errors, Authentication authentication){

        if(errors.hasErrors()){
            throw new MethodArgumentInvalidException("bingresults has error so writing failure");
        }

        String userName = authentication.getName();
        boardService.save(userName, board);
        System.out.println("write 성공");

        return "redirect:/board/list";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Long boardId){
        Board board =boardRepository.findById(boardId).orElse(null);
        model.addAttribute("board", board);

        return "board/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long boardId, @Valid Board board, Errors errors, Authentication authentication){

        if(errors.hasErrors()){
            throw new MethodArgumentInvalidException("bingresults has error so updating failure");
        }

        String userName = authentication.getName();
        LocalDateTime currentTime = LocalDateTime.now();
        board.setUpdatedDate(currentTime);
        boardService.save(userName, board);
        System.out.println(board.getBoardId()+"번" +" "+ ", title : "+ board.getTitle() + " update 성공");

        return "redirect:/board/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long boardId){

        if(boardService.isDeleted(boardId)){
            System.out.println("board"+boardId+"번"  + " delete 성공");
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
        else{
            System.out.println("board"+boardId + " delete 실패");
            throw new InternalServerException("boardController board delete failure");
        }

    }
}
