package com.example.wage.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.wage.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wg_system_config") // 数据库表名
public class SystemConfig extends BaseEntity {
    private String type;
    private String config;
}
