package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.base.pojo.BaseEntity;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.DepartmentMapper;
import com.example.wage.mapper.PositionMapper;
import com.example.wage.pojo.Department;
import com.example.wage.pojo.Employee;
import com.example.wage.pojo.Position;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职位管理服务类
 */
@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> {

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmployeeService employeeService;

    /**
     * 保存职位，保存前校验职位名称是否已存在
     * @param position
     */
    public void savePosition(Position position) {
        if (StringUtils.isNotBlank(position.getId())) {
            String s = checkName(position);
            if (StringUtils.isNotBlank(s) && !position.getId().equals(s)) {
                throw new WarningException("职位名称已存在");
            } else {
                position.updateById();
            }
        } else {
            if (StringUtils.isNotBlank(checkName(position))) {
                throw new WarningException("职位名称已存在");
            } else {
                position.insert();
            }
        }
    }

    /**
     * 根据职位名称获取职位id
     * @param position
     * @return
     */
    private String checkName(Position position) {
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Position::getName, position.getName());
        Position one = this.getOne(lambdaQueryWrapper);
        return one != null ? one.getId() : null;
    }

    /**
     * 分页查询职位
     * @param pageVo
     * @return
     */
    public IPage<Position> selectPage(PageVo<Position> pageVo) {
        Page<Position> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByDesc(BaseEntity::getCreateDate);
        if (pageVo.getSearchParams() != null) {
            Position position = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(position.getDepartmentId())) {
                lambdaQueryWrapper.eq(Position::getDepartmentId, position.getDepartmentId());
            }
            if (StringUtils.isNotBlank(position.getId())) {
                lambdaQueryWrapper.eq(Position::getId, position.getId());
            }
        }

        IPage<Position> iPage = super.baseMapper.selectPage(page, lambdaQueryWrapper);
        iPage.getRecords().forEach(item -> {
            Department department = departmentMapper.selectById(item.getDepartmentId());
            item.setDepartmentName(department.getName());
        });

        return iPage;
    }

    /**
     * 根据部门id获取所有职位
     * @param departmentId
     * @return
     */
    public List<Position> getAllByDepartmentId(String departmentId) {
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Position::getDepartmentId, departmentId);
        return super.list(lambdaQueryWrapper);
    }

    /**
     * 根据职位id删除职位及旗下员工
     * @param id
     */
    public void deletePosition(String id) {
        super.removeById(id);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getPositionId, id);
        employeeService.remove(lambdaQueryWrapper);
    }

    /**
     * 根据职位id批量删除职位及旗下员工
     * @param ids
     */
    public void deletePositionBatch(List<String> ids) {
        super.removeByIds(ids);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(Employee::getPositionId, ids);
        employeeService.remove(lambdaQueryWrapper);
    }
}
