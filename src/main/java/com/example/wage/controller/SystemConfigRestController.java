package com.example.wage.controller;

import com.example.wage.pojo.SystemConfig;
import com.example.wage.service.SystemConfigService;
import com.example.wage.util.ResultUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统配置控制器
 */
@RestController
@RequestMapping("/api/v1/systemConfig")
public class SystemConfigRestController {

    @Resource private SystemConfigService systemConfigService;

    /**
     * 保存配置
     * @param systemConfig 配置
     * @return
     */
    @GetMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResultUtil saveSystemConfig(@RequestBody SystemConfig systemConfig) {
        systemConfigService.saveSystemConfig(systemConfig);
        return ResultUtil.success();
    }

    /**
     * 根据类型查询配置
     * @param type 类型
     * @return
     */
    @GetMapping("/{type}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResultUtil findSystemConfig(@PathVariable("type") String type) {
        return ResultUtil.success( systemConfigService.findByType(type));
    }

}
