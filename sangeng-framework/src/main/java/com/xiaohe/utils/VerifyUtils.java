package com.xiaohe.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @description : 验证手机号、身份证号、密码、验证码、邮箱的工具类
 * @author : 小何
 */
public class VerifyUtils {
    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 密码正则。4~32位的字母、数字、下划线
     */
    public static final String PASSWORD_REGEX = "^\\w{4,32}$";

    /**
     * 验证码正则, 6位数字或字母
     */
    public static final String VERIFY_CODE_REGEX = "^[a-zA-Z\\d]{6}$";

    /**
     * 身份证号正则
     */
    public static final String ID_CARD_NUMBER_REGEX_18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    public static final String ID_CARD_NUMBER_REGEX_15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";





    /**
     * 手机号是否合法
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneLegal(String phone){
        return match(phone, PHONE_REGEX);
    }
    /**
     * 是否是无效邮箱格式
     * @param email 要校验的邮箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmailLegal(String email){
        return match(email, EMAIL_REGEX);
    }

    /**
     * 是否是无效验证码格式
     * @param code 要校验的验证码
     * @return true:符合，false：不符合
     */
    public static boolean isCodeLegal(String code){
        return match(code, VERIFY_CODE_REGEX);
    }

    // 校验是否不符合正则格式
    private static boolean match(String str, String regex){
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches(regex);
    }

    /**
     * 验证身份证号是否合法
     * @param idCard 身份证号
     * @return true: 合法；    false:不合法
     */
    public static boolean isIdCardLegal(String idCard) {
        if (idCard.length() == 18) {
            return match(idCard, ID_CARD_NUMBER_REGEX_18);
        } else {
            return match(idCard, ID_CARD_NUMBER_REGEX_15);
        }
    }
}