package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Date;

/**
 * 工资记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_salary") // 数据库表名
public class Salary extends BaseEntity {
	private String departmentId; // 部门ID
	private String positionId; 	// 职位ID
	private String employeeId; 	//员工ID
	private String month; 		//月份
	private double baseSalary; // 底薪
	private double workday; 	// 应工作日
	private double absenteeism; // 缺勤天数
	private double allowance;	// 津贴
	private double paid;	// 实发工资
	private String bankCard; 	//收款银行卡号
	@TableField(exist = false)
	private String employeeName; //员工名称
}
