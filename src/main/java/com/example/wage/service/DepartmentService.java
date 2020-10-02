package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.base.pojo.BaseEntity;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.DepartmentMapper;
import com.example.wage.pojo.Department;
import com.example.wage.pojo.Position;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理服务类
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    @Resource private PositionService positionService;

    /**
     * 保存部门，保存前校验部门名称是否已存在
     * @param department
     */
    public void saveDepartment(Department department) {
        if (StringUtils.isNotBlank(department.getId())) {
            String s = checkName(department);
            if (StringUtils.isNotBlank(s) && !department.getId().equals(s)) {
                throw new WarningException("部门名称已存在");
            } else {
                department.updateById();
            }
        } else {
            if (StringUtils.isNotBlank(checkName(department))) {
                throw new WarningException("部门名称已存在");
            } else {
                department.insert();
            }
        }
    }

    /**
     * 根据部门名称获取部门id
     * @param department
     * @return
     */
    public String checkName(Department department) {
        LambdaQueryWrapper<Department> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Department::getName, department.getName());
        Department one = this.getOne(lambdaQueryWrapper);
        return one != null ? one.getId() : null;
    }

    /**
     * 分页查询部门
     * @param pageVo
     * @return
     */
    public IPage<Department> selectPage(PageVo<Department> pageVo) {
        Page<Department> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Department> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByDesc(BaseEntity::getCreateDate);
        if (pageVo.getSearchParams() != null) {
            Department department = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(department.getId())) {
                lambdaQueryWrapper.eq(Department::getId, department.getId());
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

    /**
     * 根据部门id删除职位及旗下所有职位
     * @param id 部门id
     * @return
     */
    public void deleteDepartment(String id) {
        super.removeById(id);
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Position::getDepartmentId, id);
        List<String> positionId = positionService.list(lambdaQueryWrapper)
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        if (positionId.size() > 0) {
            positionService.deletePositionBatch(positionId);
        }
    }

    /**
     * 根据部门id批量删除部门及旗下所有职位
     * @param ids 部门id
     * @return
     */
    public void deleteDepartmentBatch(List<String> ids) {
        super.removeByIds(ids);
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(Position::getDepartmentId, ids);
        List<String> positionId = positionService.list(lambdaQueryWrapper)
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        if (positionId.size() > 0) {
            positionService.deletePositionBatch(positionId);
        }
    }
}
