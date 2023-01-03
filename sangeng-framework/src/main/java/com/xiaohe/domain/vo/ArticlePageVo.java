package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 15:39
 */
@Data
public class ArticlePageVo {
    private List<ArticleListVo> rows;
    private Long total;

    public ArticlePageVo(List<ArticleListVo> rows) {
        this.rows = rows;
        this.total = Long.valueOf(rows.size());
    }
}
