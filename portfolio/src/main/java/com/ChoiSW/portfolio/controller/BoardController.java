package com.ChoiSW.portfolio.controller;

import com.ChoiSW.portfolio.model.Board;
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

    /*@Autowired
    private BoardValidator boardValidator;*/


    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){

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
    public String write(@Valid Board board, @RequestParam(value = "file", required = false) MultipartFile file , BindingResult bindingResult, Authentication authentication){

        if(bindingResult.hasErrors()){
            return "board/write";
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
        System.out.println("수정하는 페이지(getmapping) 컨트롤러");

        return "board/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long boardId, @Valid Board board, BindingResult bindingResult, Authentication authentication){

        if(bindingResult.hasErrors()){
            return "board/update";
        }

        String userName = authentication.getName();
        LocalDateTime currentTime = LocalDateTime.now();
        board.setUpdatedDate(currentTime);
        boardService.save(userName, board);
        System.out.println("update 성공");

        return "redirect:/board/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long boardId){
        Board board =boardRepository.findById(boardId).orElse(null);
        boardRepository.delete(board);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
