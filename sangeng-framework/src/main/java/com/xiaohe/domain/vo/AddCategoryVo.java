package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 13:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddCategoryVo {
    @NotEmpty(message = "分类名不能为空")
    private String name;
    private String description;
    @NotEmpty(message = "状态不能为空")
    private String status;
}
