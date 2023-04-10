package com.example.carcenter.config;

import com.example.carcenter.pojo.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
public class CarCenterConfig {

    @Bean
    @Scope("prototype")
    public Company getCompany() {
        return new Company();
    }

}
