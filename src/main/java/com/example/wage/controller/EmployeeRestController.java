package com.example.wage.controller;

import com.example.wage.pojo.Employee;
import com.example.wage.service.EmployeeService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeRestController {

    @Resource private EmployeeService employeeService;

    /**
     * 保存员工
     * @param employee 员工
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResultUtil saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResultUtil.success();
    }

    /**
     * 根据员工id删除员工
     * @param id 员工id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResultUtil deleteEmployee(@PathVariable("id") String id) {
        employeeService.removeById(id);
        return ResultUtil.success();
    }

    /**
     * 根据员工id批量删除员工
     * @param ids 职位id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/batch/{ids}")
    public ResultUtil deletePosition(@PathVariable("ids") List<String> ids) {
        employeeService.removeByIds(ids);
        return ResultUtil.success();
    }

    /**
     * 分页查询
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/page")
    public ResultUtil selectPage(@RequestBody PageVo<Employee> pageVo) {
        return ResultUtil.success(employeeService.selectPage(pageVo));
    }


    /**
     * 获取所有员工
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResultUtil all() {
        return ResultUtil.success(employeeService.list());
    }

    /**
     * 根据职位id获取所有员工
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all/{positionId}")
    public ResultUtil allByPositionId(@PathVariable("positionId") String positionId) {
        return ResultUtil.success(employeeService.getAllByPositionId(positionId));
    }

}
