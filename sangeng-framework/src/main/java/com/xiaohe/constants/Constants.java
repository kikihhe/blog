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

        public static final String BLOG_Article_VIEWCOUNT = "blog:article:view_count";
    }

    interface User {
        public static final String AUTHENTICATION_NAME = "token";
        /**
         * 博客前台登录的token
         */
        public static final String BLOG_LOGIN_TOKEN = "blog:login:token:";
        public static final String ADMIN_LOGIN_TOKEN = "admin:login:token:";

        // 用户的状态，0表示正常，1表示异常
        public static final String SIMPLE_STATUS = "0";
        public static final String FORBIDDEN_STATUS = "1";

        // 用户登录时从json提交的键名字
        public static final String LOGIN_PARAMETER_USERNAME = "userName";
        public static final String LOGIN_PARAMETER_PASSWORD = "password";

        // 未登录，匿名访问的用户的principle
        public static final String ANONYMOUS_USER = "anonymousUser";

        public static final String ALIYUN_OSS = "https://typorehwf.oss-cn-chengdu.aliyuncs.com/";

    }
    interface CONTENT_TYPE {
        public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
        public static final String MULTIPART = "multipart/form-data";

    }


}
