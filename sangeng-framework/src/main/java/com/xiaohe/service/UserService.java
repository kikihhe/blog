package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.AddUserVo;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 19:08
 */
public interface UserService extends IService<User> {

    PageVo getAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber);


    public boolean addUser(AddUserVo addUserVo);
}
