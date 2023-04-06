package com.xiaohe.aop;

import com.xiaohe.annotation.Permission;
import com.xiaohe.utils.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Aspect
public class PermissionAspect {

    // 关联注解
    @Pointcut("@annotation(com.xiaohe.annotation.Permission)")
    private void permission() {

    }
    @Around("permission()")
    public void advice(ProceedingJoinPoint joinPoint) throws Throwable  {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        // 没有加这个注解，pass
        if(!method.isAnnotationPresent(Permission.class)) {
            joinPoint.proceed();
        } else {
            // 取出该接口所需要的权限
            Permission annotation = method.getAnnotation(Permission.class);
            String[] permissions = annotation.value();
            List<String> list = Arrays.asList(permissions);

            // 取出当前登录用户的权限
            List<String> permission = ThreadLocalUtil.getLoginUser().getPermission();
            if (permission.containsAll(list)) {
                joinPoint.proceed();
            } else {
                throw new RuntimeException("您无权访问此接口");
            }

        }


    }

}
