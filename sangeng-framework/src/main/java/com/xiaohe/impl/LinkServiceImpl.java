package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.LinkVo;
import com.xiaohe.mapper.LinkMapper;
import com.xiaohe.service.LinkService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 19:44
 */
@Service
@Transactional
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public List<LinkVo> getAllLinks() {
        List<LinkVo> links = linkMapper.getAllLinks();
        return links;
    }

    /**
     * 分页查询友链
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @Override
    public PageVo selectPage(Integer pageNum, Integer pageSize, String name, String status) {
        // 状态、名字
        LambdaQueryWrapper<Link> lambda = new LambdaQueryWrapper<>();
        lambda.eq(Strings.hasText(status), Link::getStatus, status);
        lambda.like(Strings.hasText(name), Link::getName, name);
        // 分页
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<Link> page1 = page(page, lambda);
        // 封装返回数据。
        PageVo pageVo = new PageVo();
        pageVo.setRows(page1.getRecords());
        pageVo.setTotal(page1.getTotal());
        return pageVo;
    }
}
