/*
package com.ChoiSW.portfolio.validator;

import com.ChoiSW.portfolio.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Board.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors e) {
        Board board = (Board) obj;
        if(StringUtils.isEmpty(board.getTitle())){
            e.rejectValue("title","key","제목을 입력하세요");
        }
    }
}
*/