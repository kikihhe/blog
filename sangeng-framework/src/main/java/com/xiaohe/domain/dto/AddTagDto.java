package com.xiaohe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 19:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddTagDto {
    @NotEmpty(message = "标签名不能为空")
    private String name;
    private String remark;
}
