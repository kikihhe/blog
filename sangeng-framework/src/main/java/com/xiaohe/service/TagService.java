package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Tag;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 16:37
 */
public interface TagService extends IService<Tag> {
    public PageVo getTagList(Integer begin, Integer pageSize, String name, String remark);

    public Boolean addTag(String name, String remark) ;
}
