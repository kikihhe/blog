package com.xiaohe.domain.entity;

import com.xiaohe.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private User user;
    private List<String> permission;

}
