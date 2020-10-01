package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.mapper.SalaryMapper;
import com.example.wage.pojo.BasicSalary;
import com.example.wage.pojo.Salary;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 工资记录服务类
 */
@Service
public class SalaryService extends ServiceImpl<SalaryMapper, Salary> {

    public IPage<Salary> selectPage(PageVo<Salary> pageVo) {
        Page<Salary> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Salary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            Salary salary = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(salary.getEmployeeId())) {
                lambdaQueryWrapper.eq(Salary::getEmployeeId, salary.getEmployeeId());
            }
            if (salary.getDate() != null) {
                lambdaQueryWrapper.between(Salary::getDate, salary.getDate(), DateUtils.addDays(salary.getDate(), 1));
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

}
