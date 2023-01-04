package com.xiaohe.constants;


/**
 * 全局所有的常量
 */
public interface Constants {
    interface Article {
        /**
         * 正式文章
         */
        public static final Integer Formal_Article = 0;
        /**
         * 草稿
         */
        public static final Integer Draft_Article = 1;
    }

    interface User {
        /**
         * 博客前台登录的token
         */
        public static final String BLOG_LOGIN_TOKEN = "blog:login:token:";
    }
}
