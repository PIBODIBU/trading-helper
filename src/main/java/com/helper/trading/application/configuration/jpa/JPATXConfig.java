package com.helper.trading.application.configuration.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JPATXConfig {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("SystemDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(name = "txManager")
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }
}
