package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_department")
public class Department extends BaseEntity {
	private String name; //部门名称
}
