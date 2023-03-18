package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.LinkVo;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 19:43
 */
public interface LinkService extends IService<Link> {
    public List<LinkVo> getAllLinks();

    /**
     * 分页条件查询友链
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    PageVo selectPage(Integer pageNum, Integer pageSize, String name, String status);
}
