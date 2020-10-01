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

    @GetMapping("/admin/welcome")
    public String welcome() {
        return "admin/welcome";
    }

    @GetMapping("/admin/welcome-2")
    public String welcome2() {
        return "admin/welcome-2";
    }

    @GetMapping("/admin/welcome-3")
    public String welcome3() {
        return "admin/welcome-3";
    }

    @GetMapping("/admin/menu")
    public String menu() {
        return "admin/menu";
    }

    @GetMapping("/admin/table")
    public String table() {
        return "admin/table";
    }

    @GetMapping("/admin/table/add")
    public String tableAdd() {
        return "admin/table/add";
    }

    @GetMapping("/admin/table/edit")
    public String tableEdit() {
        return "admin/table/edit";
    }

    @GetMapping("/admin/form")
    public String form() {
        return "admin/form";
    }

    @GetMapping("/admin/form-step")
    public String formStep() {
        return "admin/form-step";
    }

    @GetMapping("/admin/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/admin/login-2")
    public String login2() {
        return "admin/login-2";
    }

    @GetMapping("/admin/login-3")
    public String login3() {
        return "admin/login-3";
    }

    @GetMapping("/admin/404")
    public String notFound() {
        return "admin/404";
    }

    @GetMapping("/admin/button")
    public String button() {
        return "admin/button";
    }

    @GetMapping("/admin/layer")
    public String layer() {
        return "admin/layer";
    }
    @GetMapping("/admin/icon")
    public String icon() {
        return "admin/icon";
    }
    @GetMapping("/admin/icon-picker")
    public String iconPicker() {
        return "admin/icon-picker";
    }
    @GetMapping("/admin/color-select")
    public String colorSelect() {
        return "admin/color-select";
    }
    @GetMapping("/admin/table-select")
    public String tableSelect() {
        return "admin/table-select";
    }


    @GetMapping("/admin/upload")
    public String upload() {
        return "admin/upload";
    }

    @GetMapping("/admin/editor")
    public String editor() {
        return "admin/editor";
    }

    @GetMapping("/admin/area")
    public String area() {
        return "admin/area";
    }

    // ---------------------------------------------------------

    @GetMapping("/admin/organization/department")
    public String department() {
        return "admin/organization/department";
    }

    @GetMapping("/admin/organization/departmentAdd")
    public String departmentAdd() {
        return "admin/organization/departmentAdd";
    }

//    /**
//     * 页面跳转
//     */
//    @GetMapping("/payrollRecord")
//    public String payrollRecord(){
//        return "web/web/admin/payrollRecord";
//    }
//
//    /**
//     * 页面跳转
//     */
//    @GetMapping("/basicSalary")
//    public String basicSalary(){
//        return "web/web/admin/basicSalary";
//    }
//
//    /**
//     * 页面跳转
//     */
//    @GetMapping("/departmentManagement")
//    public String departmentManagement(){
//        return "web/web/admin/departmentManagement";
//    }
//
//    /**
//     * 页面跳转
//     */
//    @GetMapping("/employeeManagement")
//    public String employeeManagement(){
//        return "web/web/admin/employeeManagement";
//    }
//
//    /**
//     * 页面跳转
//     */
//    @GetMapping("/positionManagement")
//    public String positionManagement(){
//        return "web/web/admin/positionManagement";
//    }

}
