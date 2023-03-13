package com.xiaohe.controller;

import com.alibaba.excel.util.StringUtils;
import com.xiaohe.domain.dto.AddTagDto;
import com.xiaohe.domain.dto.TagListDto;
import com.xiaohe.domain.dto.UpdateTagDto;
import com.xiaohe.domain.entity.PageVo;

import com.xiaohe.domain.entity.Tag;
import com.xiaohe.service.TagService;
import com.xiaohe.utils.Result;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 16:31
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        PageVo tagList = tagService.getTagList(pageNum, pageSize, tagListDto.getName(), tagListDto.getRemark());


        return Result.success(tagList);
    }


    @PostMapping
    public Result addTag(@RequestBody @Validated AddTagDto addTagDto) {
        // 如果remark为空，它的值等于name
        if (StringUtils.isEmpty(addTagDto.getRemark())) {
            addTagDto.setRemark(addTagDto.getName());
        }
        Boolean add = tagService.addTag(addTagDto.getName(), addTagDto.getRemark());
        if (add) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败，检查标签名是否重复");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteTag(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return Result.error("发生了令程序员无法理解的错误😢");
        }
        boolean b = tagService.removeById(id);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }

    }

    @PutMapping
    public Result updateTag(@RequestBody UpdateTagDto updateTagDto) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(updateTagDto, tag);
        boolean b = tagService.updateById(tag);
        if (b) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @GetMapping("/{id}")
    public Result getTag(@PathVariable("id")Long id) {
        Tag byId = tagService.getById(id);
        return Result.success(byId);
    }
}
