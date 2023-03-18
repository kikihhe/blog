package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateCategoryVo {

    private String description;
    @NotNull(message = "id不能为空")
    private Long id;
    @NotEmpty(message = "分类名不能为空")
    private String name;
    @NotEmpty(message = "分类状态不能为空")
    private String status;

}
