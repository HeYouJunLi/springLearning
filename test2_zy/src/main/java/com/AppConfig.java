package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 15:23
 */
@Configuration   //表示当前的类是一个配置类
@ComponentScan(basePackages = "com") //将来要托管的bean要扫描的包及子包
public class AppConfig {
    @Bean
    public Random r(){
        return new Random();
    }
}
