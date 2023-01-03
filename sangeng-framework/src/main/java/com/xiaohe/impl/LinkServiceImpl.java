package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.vo.LinkVo;
import com.xiaohe.mapper.LinkMapper;
import com.xiaohe.service.LinkService;
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
}
