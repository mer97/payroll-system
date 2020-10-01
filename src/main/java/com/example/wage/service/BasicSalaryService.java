package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.mapper.BasicSalaryMapper;
import com.example.wage.pojo.BasicSalary;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 底薪服务类
 */
@Service
public class BasicSalaryService extends ServiceImpl<BasicSalaryMapper, BasicSalary> {

    public void saveBasicSalary(BasicSalary basicSalary) {
        if (StringUtils.isNotBlank(basicSalary.getId())) {
            basicSalary.updateById();
        } else {
            basicSalary.insert();
        }
    }

    public IPage<BasicSalary> selectPage(PageVo<BasicSalary> pageVo) {
        Page<BasicSalary> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<BasicSalary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            BasicSalary basicSalary = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(basicSalary.getDepartmentId())) {
                lambdaQueryWrapper.eq(BasicSalary::getDepartmentId, basicSalary.getDepartmentId());
            }
            if (StringUtils.isNotBlank(basicSalary.getPositionId())) {
                lambdaQueryWrapper.eq(BasicSalary::getPositionId, basicSalary.getPositionId());
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

}
