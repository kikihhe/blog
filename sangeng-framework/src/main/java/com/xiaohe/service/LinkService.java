package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.vo.LinkVo;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 19:43
 */
public interface LinkService extends IService<Link> {
    public List<LinkVo> getAllLinks();
}
