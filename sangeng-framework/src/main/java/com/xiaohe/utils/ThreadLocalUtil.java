package com.xiaohe.utils;

import com.xiaohe.domain.entity.LoginUser;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-04-06 19:20
 */
public class ThreadLocalUtil {
    private static volatile ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();
    public static void setLoginUser(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }
    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

}
