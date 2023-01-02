package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticle {
    private Long id;
    private String title;
    private Long viewCount;
}
