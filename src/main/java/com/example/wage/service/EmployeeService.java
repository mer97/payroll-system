package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.EmployeeMapper;
import com.example.wage.pojo.Employee;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 员工管理服务类
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> {

    public void saveEmployee(Employee employee) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getName, employee.getName());
        Employee one = this.getOne(lambdaQueryWrapper);

        if (StringUtils.isNotBlank(employee.getId())) {
            if (one == null || one.getId().equals(employee.getId())) {
                employee.updateById();
            } else {
                throw new WarningException("员工名称已存在");
            }
        } else {
            if (one == null) {
                employee.insert();
            } else {
                throw new WarningException("员工名称已存在");
            }
        }
    }

    public IPage<Employee> selectPage(PageVo<Employee> pageVo) {
        Page<Employee> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            Employee employee = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(employee.getName())) {
                lambdaQueryWrapper.eq(Employee::getName, employee.getName());
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

}
