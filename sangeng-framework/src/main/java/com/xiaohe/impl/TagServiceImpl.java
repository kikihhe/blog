package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Tag;
import com.xiaohe.mapper.TagMapper;
import com.xiaohe.service.TagService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 16:37
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public PageVo getTagList(Integer pageNum, Integer pageSize, String name, String remark) {
        LambdaQueryWrapper<Tag> lambda = new LambdaQueryWrapper<>();
        if (!Strings.isEmpty(name)) {
            lambda.like(Tag::getName, name);
        }
        if (!Strings.isEmpty(remark)) {
            lambda.like(Tag::getRemark, remark);
        }
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<Tag> result = page(page, lambda);
        return new PageVo( result.getRecords(), result.getTotal());
    }

    @Override
    public Boolean addTag(String name, String remark) {
        // 查询name字段是否已经存在
        LambdaQueryWrapper<Tag> lambda = new LambdaQueryWrapper<>();
        lambda.eq(Tag::getName, name);
        Tag tag = tagMapper.selectOne(lambda);
        // 如果已经存在，返回false.
        if (!Objects.isNull(tag)) {
            return false;
        }
        Tag newTag = new Tag();
        newTag.setName(name);
        newTag.setRemark(remark);
        int insert = tagMapper.insert(newTag);

        return insert > 0;
    }
}
