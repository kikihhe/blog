package com.xiaohe.common;

import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result usernameNotFoundException(UsernameNotFoundException exception) {
        log.error(exception.toString());
        return Result.error(exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public Result exception(Exception exception) {
        log.error(exception.toString());
        return Result.error(exception.getMessage());
    }


}
