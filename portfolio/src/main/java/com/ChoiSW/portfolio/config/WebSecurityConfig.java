package com.ChoiSW.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;  //application.properties 의 datasource 설정들을 주입시킴



    //인증절차에 관한 설명
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/account/register", "/css/**","/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    .usernameParameter("userName")
                    .passwordParameter("userPassword")
                    .defaultSuccessUrl("/",true)
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    //인증을 위한 사용자 설명
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                //인증처리
                .usersByUsernameQuery("select userName, userPassword, userEnabled "
                        + "from user "
                        + "where userName = ?")
                //권한처리
                .authoritiesByUsernameQuery("select u.userName, r.roleName "
                        + "from user_role ur inner join user u on ur.user_id = u.userId "
                        + "inner join role r on ur.role_id = r.roleId "
                        + "where u.userName = ?");
    }

    //비밀번호 암호화해주는 함수
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}