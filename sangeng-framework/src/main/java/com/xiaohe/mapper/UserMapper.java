package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 11:26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User getUserByUsername(@Param("username") String username);

    public List<User> getAllIdAndName();


    public Integer insertUserRole(@Param("userId") Long id, @Param("roleIds") List<Long> roleIds);
}
