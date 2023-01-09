package com.xiaohe.common;

import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    // 解决重复添加数据爆出的sql异常.
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e) {
        // 判断当前异常中是否有唯一约束报错
        if (e.getMessage().contains("Duplicate entry")) {
            String[] str = e.getMessage().split(" ");
            // 获取哪个用户重复了。
            String message = str[2] + "已存在";
            return Result.error(message);
        }
        return Result.error("未知错误");
    }






}
