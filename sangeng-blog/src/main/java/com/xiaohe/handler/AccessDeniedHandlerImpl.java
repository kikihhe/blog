package com.xiaohe.handler;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : 小何
 * @Description : 授权失败处理器
 * @date : 2023-01-06 20:59
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(Constants.CONTENT_TYPE.APPLICATION_JSON);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(e.getMessage(), HttpStatus.HTTP_FORBIDDEN)));

    }
}
