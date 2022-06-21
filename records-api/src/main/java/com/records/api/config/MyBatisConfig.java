package com.records.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 *
 * @author changfa
 */
@AutoConfiguration
@EnableTransactionManagement
@MapperScan({"com.records.api.***.mapper"})
public class MyBatisConfig {
}
