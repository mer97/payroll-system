package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职位实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_position") // 数据库表名
public class Position extends BaseEntity {
    private String name; // 职位名称
    private String departmentId; // 所属部门id
    @TableField(exist = false)
    private String departmentName; // 所属部门名称
}
