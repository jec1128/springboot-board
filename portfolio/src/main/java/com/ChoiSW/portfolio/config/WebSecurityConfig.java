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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;


import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;  //application.properties 의 datasource 설정들을 주입시킴

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;


    //인증절차에 관한 설명
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //이 설정이 https 리퀘스트 헤더를 받지 않겟다는 말인듯
                //구글링해보니 이걸로 해결했다고 해서 일단 적어놓음
                //강제적으로 https로만 접속하겠다는 기능을 disable함.
                .headers().httpStrictTransportSecurity().disable()
                    .and()

                .httpBasic()
                    .and()

                .authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .antMatchers("/", "/account/register", "/css/**","/api/**","/js/**","/account/login*","/access-denied").permitAll()
                    .anyRequest().authenticated()
                    .and()

                .formLogin()
                    .loginPage("/account/login")
                    .usernameParameter("userName")
                    .passwordParameter("userPassword")
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()

                .exceptionHandling()

                    .accessDeniedHandler(accessDeniedHandler);



    }

    //인증을 위한 사용자 설명
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                //인증처리
                .usersByUsernameQuery("select user_name, user_password, user_enabled "
                        + "from user "
                        + "where user_name = ?")
                //권한처리
                .authoritiesByUsernameQuery("select user_name, role from user "
                        + "where user_name = ?");
    }

    //비밀번호 암호화해주는 함수
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //request was rejected because the URL contained a potentially malicious String "%2e" 에 대한 에러 수정때문에 추가
    @Bean
    public RequestRejectedHandler requestRejectedHandler() {
        return new HttpStatusRequestRejectedHandler();
    }


    /*
    org.springframework.security.web.firewall.RequestRejectedException
    The request was rejected because the HTTP method "SOHF" was not included within the list of allowed HTTP methods
    [HEAD, DELETE, POST, GET, OPTIONS, PATCH, PUT]
    이런 에러가 떠서 수정하기위해 방화벽에 예외로 sohf 와 PROPFIND를 추가함
    그러나 왜 이런에러가 뜨는지 모르겠음
    나는 저런 헤더로 보낸 리퀘스트가 없음
     */
    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("HEAD", "DELETE", "POST", "GET", "OPTIONS", "PATCH", "PUT", "PROPFIND", "SOHF"));
        return firewall;
    }
}