package com.helper.trading.application.configuration.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AuthenticationProviderConfig {
    @Bean(name = "SystemDataSource")
    public DriverManagerDataSource dataSource() {
        /*DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/heroku_0deeb3e187c3980");
        dataSource.setUsername("b9ee1f875e9873");
        dataSource.setPassword("dde5ca57");*/

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://81.162.228.39/tradinghelper");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }
}