package com.xiaohe.controller;

import com.xiaohe.domain.vo.LinkVo;
import com.xiaohe.service.LinkService;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 友链
 */
@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 显示所有友链
     *
     */
    @GetMapping("/getAllLink")
    public Result getAllLinks() {
        List<LinkVo> links = linkService.getAllLinks();
        return Result.success(links);
    }
}
