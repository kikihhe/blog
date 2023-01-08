package com.xiaohe.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiaohe.domain.entity.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 16:33
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        Long id = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        metaObject.setValue("createBy", id);
        metaObject.setValue("updateBy", id);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());

        Long id = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        metaObject.setValue("updateBy", id);
    }
}
