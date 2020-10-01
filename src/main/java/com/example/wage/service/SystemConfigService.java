package com.example.wage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wage.mapper.SystemConfigMapper;
import com.example.wage.pojo.Salary;
import com.example.wage.pojo.SystemConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 系统配置服务类
 */
@Service
public class SystemConfigService extends ServiceImpl<SystemConfigMapper, SystemConfig> {

    public void saveSystemConfig(SystemConfig systemConfig){
        if (StringUtils.isNotBlank(systemConfig.getId())) {
            systemConfig.updateById();
        } else {
            systemConfig.insert();
        }
    }

    public SystemConfig findByType(String type) {
        LambdaQueryWrapper<SystemConfig> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(SystemConfig::getType, type);
        return super.getOne(lambdaQueryWrapper);
    }

}
