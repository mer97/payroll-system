package com.example.wage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转控制器
 */
@Controller
@RequestMapping("/web")
public class WebController {

    /**
     * 页面跳转
     */
    @GetMapping("/admin/user-setting")
    public String userSetting() {
        return "admin/user-setting";
    }

    @GetMapping("/admin/user-password")
    public String userPassword() {
        return "admin/user-password";
    }

    @GetMapping("/admin/setting")
    public String setting() {
        return "admin/setting";
    }

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }

    @GetMapping("/admin/organization/department")
    public String department() {
        return "admin/organization/department";
    }

    @GetMapping("/admin/organization/departmentAdd")
    public String departmentAdd() {
        return "admin/organization/departmentAdd";
    }

    @GetMapping("/admin/organization/position")
    public String position() {
        return "admin/organization/position";
    }

    @GetMapping("/admin/organization/positionAdd")
    public String positionAdd() {
        return "admin/organization/positionAdd";
    }

    @GetMapping("/admin/organization/employee")
    public String employee() {
        return "admin/organization/employee";
    }

    @GetMapping("/admin/organization/employeeAdd")
    public String employeeAdd() {
        return "admin/organization/employeeAdd";
    }

    @GetMapping("/admin/salary/salarySheet")
    public String salarySheet() {
        return "admin/salary/salarySheet";
    }

    @GetMapping("/admin/salary/salarySheetAdd")
    public String salarySheetAdd() {
        return "admin/salary/salarySheetAdd";
    }

    @GetMapping("/admin/salary/statement")
    public String salaryStatement() {
        return "admin/salary/statement";
    }

    @GetMapping("/admin/salary/mySalarySheet")
    public String mySalarySheet() {
        return "admin/salary/mySalarySheet";
    }

}
