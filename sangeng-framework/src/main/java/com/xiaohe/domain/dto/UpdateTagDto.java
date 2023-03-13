package com.xiaohe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 19:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateTagDto {
    private Long id;
    private String name;
    private String remark;
}
