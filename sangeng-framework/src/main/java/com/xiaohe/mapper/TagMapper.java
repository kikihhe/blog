package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 16:34
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

//    public PageVo getTagList(@Param("begin") Integer begin,
//                             @Param("pageSize") Integer pageSize,
//                             @Param("name") String name,
//                             @Param("remark") String remark);
}
