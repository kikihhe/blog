package com.xiaohe.controller;

import com.xiaohe.domain.entity.Link;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.LinkVo;
import com.xiaohe.service.LinkService;
import com.xiaohe.utils.IdUtil;
import com.xiaohe.utils.Result;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 16:39
 */
@RestController
@RequestMapping("/content/link")
@Slf4j
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, String name, String status) {
        PageVo pageVo = linkService.selectPage(pageNum, pageSize, name, status);
        return Result.success(pageVo);
    }
    @DeleteMapping("/{id}")
    public Result deleteLink(@PathVariable("id") String id) {
        if (!Strings.hasText(id)) {
            return Result.error("请先传入要删除的id");
        }
        List<Long> ids = IdUtil.stringCastToList(id);

        boolean b = linkService.removeByIds(ids);
        if (b) {
            return Result.success("删除成功");
        } else {
            log.error("删除友链时失败!, 需要删除的id为: {}",id);
            return Result.error("删除失败");
        }
    }

    @PostMapping
    public Result addLink(@RequestBody @Validated LinkVo linkVo) {
        Link link = new Link();
        BeanUtils.copyProperties(linkVo, link);
        boolean save = linkService.save(link);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }

    }
}
