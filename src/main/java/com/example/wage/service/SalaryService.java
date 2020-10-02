package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.EmployeeMapper;
import com.example.wage.mapper.SalaryMapper;
import com.example.wage.pojo.Department;
import com.example.wage.pojo.Employee;
import com.example.wage.pojo.Salary;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 工资记录服务类
 */
@Service
public class SalaryService extends ServiceImpl<SalaryMapper, Salary> {

    @Resource private EmployeeMapper employeeMapper;

    /**
     * 保存工资条
     * @param salary
     */
    public void saveSalary(Salary salary) {
        if (StringUtils.isNotBlank(salary.getId())) {
            String s = checkMonth(salary);
            if (StringUtils.isNotBlank(s) && !salary.getId().equals(s)) {
                throw new WarningException("该员工已创建" + salary.getMonth() + "的工资条");
            } else {
                salary.updateById();
            }
        } else {
            if (StringUtils.isNotBlank(checkMonth(salary))) {
                throw new WarningException("该员工已创建" + salary.getMonth() + "的工资条");
            } else {
                salary.insert();
            }
        }
    }

    /**
     * 根据员工id和月份获取工资条id
     * @param salary
     * @return
     */
    public String checkMonth(Salary salary) {
        LambdaQueryWrapper<Salary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Salary::getEmployeeId, salary.getEmployeeId());
        lambdaQueryWrapper.eq(Salary::getMonth, salary.getMonth());
        Salary one = this.getOne(lambdaQueryWrapper);
        return one != null ? one.getId() : null;
    }

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    public IPage<Salary> selectPage(PageVo<Salary> pageVo) {
        Page<Salary> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Salary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            Salary salary = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(salary.getDepartmentId())) {
                lambdaQueryWrapper.eq(Salary::getDepartmentId, salary.getDepartmentId());
            }
            if (StringUtils.isNotBlank(salary.getPositionId())) {
                lambdaQueryWrapper.eq(Salary::getPositionId, salary.getPositionId());
            }
            if (StringUtils.isNotBlank(salary.getEmployeeId())) {
                lambdaQueryWrapper.eq(Salary::getEmployeeId, salary.getEmployeeId());
            }
            if (StringUtils.isNotBlank(salary.getMonth())) {
                lambdaQueryWrapper.eq(Salary::getMonth, salary.getMonth());
            }
        }
        IPage<Salary> iPage = super.baseMapper.selectPage(page, lambdaQueryWrapper);
        iPage.getRecords().forEach(item -> {
            Employee employee = employeeMapper.selectById(item.getEmployeeId());
            if (employee != null) {
                item.setEmployeeName(employee.getName());
            }
        });

        return iPage;
    }

}
