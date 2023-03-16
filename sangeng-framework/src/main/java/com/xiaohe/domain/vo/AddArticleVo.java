package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-16 14:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddArticleVo {
    private Long id;
    //标题
    @NotEmpty(message = "文章标题不能为空")
    private String title;
    //文章内容
    @NotEmpty(message = "文章内容不能为空")
    private String content;
    //文章摘要
    @NotEmpty(message = "文章摘要不能为空")
    private String summary;
    //所属分类id
    @NotNull(message = "必须选择文章所属分类")
    private Long categoryId;

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    private List<Long> tags;
}
