package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.exception.WarningException;
import com.example.wage.mapper.PositionMapper;
import com.example.wage.pojo.Position;
import com.example.wage.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 职位管理服务类
 */
@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> {

    public void savePosition(Position position) {
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Position::getName, position.getName());
        Position one = this.getOne(lambdaQueryWrapper);

        if (StringUtils.isNotBlank(position.getId())) {
            if (one == null || one.getId().equals(position.getId())) {
                position.updateById();
            } else {
                throw new WarningException("职位名称已存在");
            }
        } else {
            if (one == null) {
                position.insert();
            } else {
                throw new WarningException("职位名称已存在");
            }
        }
    }

    public IPage<Position> selectPage(PageVo<Position> pageVo) {
        Page<Position> page = new Page<>(pageVo.getPage() - 1, pageVo.getLimit());
        LambdaQueryWrapper<Position> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (pageVo.getSearchParams() != null) {
            Position position = pageVo.getSearchParams();
            if (StringUtils.isNotBlank(position.getName())) {
                lambdaQueryWrapper.eq(Position::getName, position.getName());
            }
        }
        return super.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

}
