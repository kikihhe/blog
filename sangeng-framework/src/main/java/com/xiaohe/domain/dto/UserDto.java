package com.xiaohe.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xiaohe.utils.annotitions.Phone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 11:53
 */
@Data
public class UserDto {
    //昵称
    private String nickName;

    //手机号
    private String phoneNumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //账号状态（0正常 1停用）
    private String status;
    private String email;
}
