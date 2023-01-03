package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Category;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 13:29
 */
public interface CategoryService extends IService<Category> {
    // 查询分类列表，必须是正式发布的文章，且状态正常
    public List<Category> getCategoryList();
}
