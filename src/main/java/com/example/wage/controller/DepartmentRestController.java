package com.example.wage.controller;

import com.example.wage.pojo.Department;
import com.example.wage.service.DepartmentService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentRestController {

    @Resource private DepartmentService departmentService;

    /**
     * 保存部门
     * @param department 部门
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResultUtil saveDepartment(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return ResultUtil.success();
    }

    /**
     * 根据部门id删除职位
     * @param id 部门id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResultUtil deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
        return ResultUtil.success();
    }

    /**
     * 根据部门id批量删除部门
     * @param ids 部门id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/batch/{ids}")
    public ResultUtil deleteDepartmentBatch(@PathVariable("ids") List<String> ids) {
        departmentService.deleteDepartmentBatch(ids);
        return ResultUtil.success();
    }

    /**
     * 分页查询部门
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/page")
    public ResultUtil selectPage(@RequestBody PageVo<Department> pageVo) {
        return ResultUtil.success(departmentService.selectPage(pageVo));
    }

    /**
     * 查询所有部门
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResultUtil all() {
        return ResultUtil.success(departmentService.list());
    }

}
