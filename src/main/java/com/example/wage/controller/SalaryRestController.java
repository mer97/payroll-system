package com.example.wage.controller;

import com.example.wage.pojo.Salary;
import com.example.wage.service.SalaryService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资记录控制器
 */
@RestController
@RequestMapping("/api/v1/salary")
public class SalaryRestController {

    @Resource private SalaryService salaryService;

    /**
     * 保存工资条
     * @param salary 工资条
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResultUtil saveSalary(@RequestBody Salary salary) {
        salaryService.saveSalary(salary);
        return ResultUtil.success();
    }

    /**
     * 根据工资条id删除工资条
     * @param id 员工id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResultUtil deleteSalary(@PathVariable("id") String id) {
        salaryService.removeById(id);
        return ResultUtil.success();
    }

    /**
     * 根据工资条id批量删除工资条
     * @param ids 职位id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/batch/{ids}")
    public ResultUtil deleteSalaryBatch(@PathVariable("ids") List<String> ids) {
        salaryService.removeByIds(ids);
        return ResultUtil.success();
    }

    /**
     * 分页查询
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/page")
    public ResultUtil selectPage(@RequestBody PageVo<Salary> pageVo) {
        return ResultUtil.success(salaryService.selectPage(pageVo));
    }

}
