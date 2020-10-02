package com.example.wage.controller;

import com.example.wage.pojo.Salary;
import com.example.wage.service.SalaryService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 工资记录控制器
 */
@RestController
@RequestMapping("/api/v1/salary")
public class SalaryRestController {

    @Resource private SalaryService salaryService;

    /**
     * 分页查询工资记录
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/page")
    public ResultUtil selectPage(@RequestBody PageVo<Salary> pageVo) {
        return ResultUtil.success(salaryService.selectPage(pageVo));
    }

}
