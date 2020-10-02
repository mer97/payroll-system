package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.base.pojo.BaseEntity;
import com.example.wage.exception.DangerException;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.EmployeeMapper;
import com.example.wage.mapper.PositionMapper;
import com.example.wage.pojo.Employee;
import com.example.wage.pojo.Position;
import com.example.wage.vo.PageVo;
import com.example.wage.vo.UpdatePasswordVo;
import com.example.wage.vo.UserInfoVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工管理服务类
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> {

    @Resource
    private PositionMapper positionMapper;

    /**
     * 保存员工，保存前校验员工名称和登录账号是否已存在
     *
     * @param employee
     */
    public void saveEmployee(Employee employee) {
        // amdin为系统内置管理员账号
        if ("admin".equals(employee.getLoginName())) {
            throw new WarningException("登录账号已存在");
        }

        if (StringUtils.isNotBlank(employee.getId())) {
            String s = checkName(employee);
            String s1 = checkLoginName(employee);
            if (StringUtils.isNotBlank(s) && !employee.getId().equals(s)) {
                throw new WarningException("员工名称已存在");
            } else if (StringUtils.isNotBlank(s1) && !employee.getId().equals(s1)) {
                throw new WarningException("登录账号已存在");
            } else {
                employee.updateById();
            }
        } else {
            if (StringUtils.isNotBlank(checkName(employee))) {
                throw new WarningException("员工名称已存在");
            } else if (StringUtils.isNotBlank(checkLoginName(employee))) {
                throw new WarningException("登录账号已存在");
            } else {
                employee.insert();
            }
        }
    }

    /**
     * 根据员工名称获取员工id
     *
     * @param employee
     * @return
     */
    public String checkName(Employee employee) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getName, employee.getName());
        Employee one = this.getOne(lambdaQueryWrapper);
        return one != null ? one.getId() : null;
    }

    /**
     * 根据员登录账号获取员工id
     *
     * @param employee
     * @return
     */
    public String checkLoginName(Employee employee) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getLoginName, employee.getLoginName());
        Employee one = this.getOne(lambdaQueryWrapper);
        return one != null ? one.getId() : null;
    }

    /**
     * 分页查询
     *
     * @param pageVo 参数
     * @return
     */
    public IPage<Employee> selectPage(PageVo<Employee> pageVo) {
        Page<Employee> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByDesc(BaseEntity::getCreateDate);
        if (pageVo.getSearchParams() != null) {
            Employee employee = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(employee.getDepartmentId())) {
                lambdaQueryWrapper.eq(Employee::getDepartmentId, employee.getDepartmentId());
            }
            if (StringUtils.isNotBlank(employee.getPositionId())) {
                lambdaQueryWrapper.eq(Employee::getPositionId, employee.getPositionId());
            }
            if (StringUtils.isNotBlank(employee.getId())) {
                lambdaQueryWrapper.eq(Employee::getId, employee.getId());
            }
        }

        IPage<Employee> iPage = super.baseMapper.selectPage(page, lambdaQueryWrapper);
        iPage.getRecords().forEach(item -> {
            Position position = positionMapper.selectById(item.getPositionId());
            item.setDepartmentId(position.getDepartmentId());
        });

        return iPage;
    }

    /**
     * 根据职位id获取所有员工
     *
     * @return
     */
    public List<Employee> getAllByPositionId(String positionId) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getPositionId, positionId);
        return super.list(lambdaQueryWrapper);
    }

    /**
     * 修改信息
     *
     * @param userInfoVo
     */
    public void saveUserInfo(UserInfoVo userInfoVo) {
        // 获取当前登录的用户
        Employee employee = getCurrentLoginEmployee();
        try {
            BeanUtils.copyProperties(employee, userInfoVo);
            employee.updateById();
        } catch (Exception e) {
            throw new DangerException("保存失败，请稍后重试");
        }
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public Employee getCurrentLoginEmployee() {
        // 获取当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LambdaQueryWrapper<Employee> employeeQueryWrapper = Wrappers.lambdaQuery();
        employeeQueryWrapper.eq(Employee::getLoginName, authentication.getName());
        return super.getOne(employeeQueryWrapper);
    }

    // 修改登录密码
    public void updatePassword(UpdatePasswordVo updatePasswordVo) {
        // 获取当前登录的用户
        Employee employee = getCurrentLoginEmployee();
        if (updatePasswordVo.getOldPassword().equals(employee.getPassword())) {
            employee.setPassword(updatePasswordVo.getNewPassword());
            employee.updateById();
        } else {
            throw new DangerException("旧的密码错误");
        }
    }
}
