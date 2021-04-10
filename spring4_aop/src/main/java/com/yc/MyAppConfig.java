package com.yc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-09 20:21
 */
@Configuration
@ComponentScan(basePackages = {"com.yc"})
@EnableAspectJAutoProxy //启动aspectJ支持
public class MyAppConfig {
}
