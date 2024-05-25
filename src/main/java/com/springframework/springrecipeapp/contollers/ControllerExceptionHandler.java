package com.springframework.springrecipeapp.contollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ModelAndView handleNumberFormatException(Exception ex){
        log.error("Exception message" + " "+ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("400badRequest");
        mav.addObject("exMessage", ex.getMessage());
        return mav;
    }
}
