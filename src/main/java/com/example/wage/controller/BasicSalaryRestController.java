package com.example.wage.controller;

import com.example.wage.pojo.BasicSalary;
import com.example.wage.pojo.Department;
import com.example.wage.service.BasicSalaryService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 底薪控制器
 */
@RestController
@RequestMapping("/api/v1/basicSalary")
public class BasicSalaryRestController {

    @Resource private BasicSalaryService basicSalaryService;

    /**
     * 保存底薪
     * @param basicSalary 底薪
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResultUtil saveBasicSalary(@RequestBody BasicSalary basicSalary) {
        basicSalaryService.saveBasicSalary(basicSalary);
        return ResultUtil.success();
    }

    /**
     * 根据底薪id删除底薪
     * @param id 底薪id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResultUtil deleteBasicSalary(@PathVariable("id") String id) {
        basicSalaryService.removeById(id);
        return ResultUtil.success();
    }

    /**
     * 分页查询底薪
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/admin")
    public ResultUtil selectPage(PageVo<BasicSalary> pageVo) {
        return ResultUtil.success(basicSalaryService.selectPage(pageVo));
    }

}
