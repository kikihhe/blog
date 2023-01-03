package com.xiaohe.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2022-02-02 12:29:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category  {
    // 主键
    @TableId
    private Long id;

    // 分类名
    private String name;

    // 父分类id，如果没有父分类为-1
    private Long pid;

    // 描述
    private String description;

    // 状态0:正常,1禁用
    private String status;

    // 创建人的id
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 更改人的id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    // 最新更改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
