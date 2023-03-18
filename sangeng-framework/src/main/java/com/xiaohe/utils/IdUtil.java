package com.xiaohe.utils;

import io.jsonwebtoken.lang.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 17:14
 */

public class IdUtil {
    public static List<Long> stringCastToList(String id) {
        // 将 "1,2,3,4,5"形式的字符串转换为 [1,2,3,4,5] 的List集合
        if (!Strings.hasText(id)) {
            return new ArrayList<Long>();
        }
        String[] split = id.split(",");
        List<Long> ids = new ArrayList<>();
        for (String s : split) {
            ids.add(Long.valueOf(s));
        }
        return ids;
    }
}
