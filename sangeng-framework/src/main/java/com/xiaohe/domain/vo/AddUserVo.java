package com.xiaohe.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xiaohe.utils.annotitions.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-17 21:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddUserVo {
    //用户名
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    //昵称
    private String nickName;

    //密码
    @NotEmpty(message = "密码不能为空")
    private String password;
    //手机号
    @Phone(message = "手机号不符合格式")
    @TableField("phonenumber")
    private String phoneNumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    @Email(message = "邮箱号不符合格式")
    private String email;

    // 角色ids

    private List<Long> roleIds;
}
