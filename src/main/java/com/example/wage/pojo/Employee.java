package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Date;

/**
 * 员工实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_employee") // 数据库表名
public class Employee extends BaseEntity {
	private String name; 	// 员工姓名
	private Date entryDate; 	// 入职时间
	private String phone; 	// 电话
	private String email; 	// 邮箱
	private String bankCard; 	// 银行卡号
	private String loginName;	//	登录账号
	private String password;	//	登录密码
	private String permission;	//	账号权限
	private String departmentId; // 部门ID
	private String positionId; 	// 职位ID
}
