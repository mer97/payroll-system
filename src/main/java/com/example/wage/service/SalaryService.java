package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.base.pojo.BaseEntity;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.SalaryMapper;
import com.example.wage.pojo.Employee;
import com.example.wage.pojo.Salary;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工资记录服务类
 */
@Service
public class SalaryService extends ServiceImpl<SalaryMapper, Salary> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryService.class);

    @Resource private EmployeeService employeeService;

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
        lambdaQueryWrapper.orderByAsc(Salary::getMonth);
        lambdaQueryWrapper.orderByDesc(BaseEntity::getCreateDate);

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
            Employee employee = employeeService.getById(item.getEmployeeId());
            if (employee != null) {
                item.setEmployeeName(employee.getName());
            }
        });

        return iPage;
    }

    /**
     * 报表：每月平均工资
     * @return
     */
    public Map<String, Object> avgSalaryStatement() {
        Map<String, Object> data = new HashMap<>();
        LambdaQueryWrapper<Salary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.groupBy(Salary::getMonth);
        lambdaQueryWrapper.orderByAsc(Salary::getMonth);
        List<Salary> salaryList = super.list(lambdaQueryWrapper);
        if (salaryList.size() > 0) {
            String month = salaryList.get(0).getMonth();
            int y = Integer.parseInt(month.split("-")[0]);
            int m = Integer.parseInt(month.split("-")[1]);
            // 开始日期为第一条工资条日期
            LocalDateTime startDate = LocalDateTime.of(y, m, 1, 0, 0);
            LocalDateTime now = LocalDateTime.now();
            // 截止日期为上个月
            LocalDateTime endDate = LocalDateTime.of(now.getYear(), now.getMonthValue(), 1, 0, 0);
            LOGGER.info("统计区间：{} - {}", startDate, endDate);
            while (startDate.isBefore(endDate)) {
                String currentMonth = localDateTimeFormat("yyyy-MM", startDate);
                LambdaQueryWrapper<Salary> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(Salary::getMonth, currentMonth);
                List<Salary> list = super.list(queryWrapper);
                if (list.size() > 0) {
                    double sum = list.stream().mapToDouble(Salary::getPaid).sum();
                    if (sum != 0) {
                        // 人均工资
                        double avg = sum / list.size();
                        data.put(currentMonth, avg);
                    } else {
                        data.put(currentMonth, 0);
                    }
                } else {
                    data.put(currentMonth, 0);
                }

                startDate = startDate.plusMonths(1);
            }
        }

        return data;
    }

    /**
     * 获取最大、最小、总实发工资
     * @return
     */
    public Map<String, Object> maxMinSumPaid() {
        Map<String, Object> data = new HashMap<>();

        LambdaQueryWrapper<Salary> maxQueryWrapper = Wrappers.lambdaQuery();
        maxQueryWrapper.orderByDesc(Salary::getPaid).last("limit 1");
        Salary maxSalary = super.getOne(maxQueryWrapper);

        LambdaQueryWrapper<Salary> minQueryWrapper = Wrappers.lambdaQuery();
        minQueryWrapper.orderByAsc(Salary::getPaid).last("limit 1");
        Salary minSalary = super.getOne(minQueryWrapper);

        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(paid) as paid");
        Salary sumSalary = super.getOne(queryWrapper);

        data.put("max", maxSalary != null ? maxSalary.getPaid() : 0);
        data.put("min", minSalary != null ? minSalary.getPaid() : 0);
        data.put("sum", sumSalary != null ? sumSalary.getPaid() : 0);

        return data;
    }

    private String localDateTimeFormat(String format, LocalDateTime dateTime) {
        if (!StringUtils.isBlank(format) && dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return formatter.format(dateTime);
        } else {
            return null;
        }
    }

    /**
     * 分页查询当前登录用户的工资条
     * @param pageVo
     * @return
     */
    public IPage<Salary> selectPageByCurrentLoginName(PageVo<Salary> pageVo) {
        Page<Salary> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Salary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByAsc(Salary::getMonth);

        // 获取当前登录的用户
        Employee employee = employeeService.getCurrentLoginEmployee();
        lambdaQueryWrapper.eq(Salary::getEmployeeId, employee.getId());

        if (pageVo.getSearchParams() != null) {
            Salary salary = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(salary.getMonth())) {
                lambdaQueryWrapper.eq(Salary::getMonth, salary.getMonth());
            }
        }

        IPage<Salary> iPage = super.baseMapper.selectPage(page, lambdaQueryWrapper);
        iPage.getRecords().forEach(item -> {
           item.setEmployeeName(employee.getName());
        });

        return iPage;
    }
}
