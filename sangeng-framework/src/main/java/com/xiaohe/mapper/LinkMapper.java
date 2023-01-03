package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.vo.LinkVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 19:42
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {
    public List<LinkVo> getAllLinks();
}
