package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.DepartmentMapper;
import com.example.wage.pojo.Department;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 部门管理服务类
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    public void saveDepartment(Department department) {
        LambdaQueryWrapper<Department> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Department::getName, department.getName());
        Department one = this.getOne(lambdaQueryWrapper);

        if (StringUtils.isNotBlank(department.getId())) {
            if (one == null || one.getId().equals(department.getId())) {
                department.updateById();
            } else {
                throw new WarningException("部门名称已存在");
            }
        } else {
            if (one == null) {
                department.insert();
            } else {
                throw new WarningException("部门名称已存在");
            }
        }
    }

    public IPage<Department> selectPage(PageVo<Department> pageVo) {
        Page<Department> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Department> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            Department department = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(department.getName())) {
                lambdaQueryWrapper.eq(Department::getName, department.getName());
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

}
