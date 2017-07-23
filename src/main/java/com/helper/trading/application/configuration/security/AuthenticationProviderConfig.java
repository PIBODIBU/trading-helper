package com.helper.trading.application.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AuthenticationProviderConfig {
    @Bean(name = "SystemDataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/tradinghelper");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }
}
