package com.xiaohe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TagListDto {
    private String name;
    private String remark;
}
