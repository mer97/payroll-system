package com.example.wage.pojo;

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
	private String employeeId; 	//员工
	private Date date; 		//日期（月）
	private double workday; 	// 工作日
	private double absenteeism; 	// 缺勤天数
	private double allowance;	// 津贴
	private double paid;	// 实发工资

	// 薪资 = （底薪 / 工作日 * 缺勤天数） + 津贴
}
