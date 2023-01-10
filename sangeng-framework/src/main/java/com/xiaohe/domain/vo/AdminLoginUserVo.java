package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginUserVo {
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String password;
}
