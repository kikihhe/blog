package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Category;
import com.xiaohe.mapper.CategoryMapper;
import com.xiaohe.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 13:29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
