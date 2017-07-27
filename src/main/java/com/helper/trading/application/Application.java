package com.helper.trading.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = {"com.helper.trading.model"})
@EnableJpaRepositories(basePackages = {"com.helper.trading.repository"}, transactionManagerRef = "txManager")
@EnableTransactionManagement
//@ComponentScan("com.helper.trading")
//@ImportResource("classpath:application-context.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}