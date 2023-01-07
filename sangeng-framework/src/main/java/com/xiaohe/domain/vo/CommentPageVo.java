package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 18:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageVo {
    private List<CommentVo> rows;
    private Long total;

    public CommentPageVo(List<CommentVo> rows) {
        this.rows = rows;
        this.total = Long.valueOf(rows.size());
    }
}
