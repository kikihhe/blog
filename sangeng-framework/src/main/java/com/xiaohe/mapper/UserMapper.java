package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-04 18:01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
