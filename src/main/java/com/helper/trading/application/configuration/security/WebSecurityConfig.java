package com.helper.trading.application.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected DataSource dataSource;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("SystemDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                //.passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password,is_enabled from sys_user where username=?")
                .authoritiesByUsernameQuery(
                        "select username,role_name from sys_rel_user_role where username=?");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                //.passwordEncoder(passwordEncoder())
        ;
    }

    // Beans
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Autowiring
    @Autowired
    public void setUserDetailsService(@Qualifier("UserDetailsService")
                                              UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/me/**").access("isAuthenticated()")
                .antMatchers("/tx/**").access("isAuthenticated()")
                .and()
                .formLogin().defaultSuccessUrl("/tx/list").loginPage("/app/login").failureUrl("/app/login?error")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/app/login/?logout")
                .and()
                .httpBasic().disable()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }
}