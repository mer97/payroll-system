package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 底薪实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_basic_salary") // 数据库表名
public class BasicSalary extends BaseEntity {
	private String positionId; //职位id
	private double baseWage; //底薪
	private String departmentId; //部门
	@TableField(exist = false)
	private String departmentName;
}
