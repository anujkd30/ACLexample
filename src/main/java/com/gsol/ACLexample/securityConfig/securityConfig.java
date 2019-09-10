package com.gsol.ACLexample.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .authoritiesByUsernameQuery("select E.USER_NAME as USERNAME, EMP.ROLE as ROLE\n" +
                    "from ( Select ER.EMPLOYEE_ID,R.ROLE\n" +
                    "    from employee_role ER,role  R\n" +
                    "    where R.ROLE_ID = ER.ROLE_ID )  EMP, employee E\n" +
                    "where EMP.EMPLOYEE_ID = E.ID and E.USER_NAME = ?")
            .usersByUsernameQuery("select USER_NAME as USERNAME, PASSWORD, 1 as enabled  from employee where USER_NAME = ?");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**/employees").hasAnyRole("MANAGER","ADMIN","IT")
                .antMatchers(HttpMethod.PUT, "/**/employees").hasAnyRole("ADMIN","IT")
                .antMatchers(HttpMethod.DELETE, "/**/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/**/employees/**").hasRole("MANAGER")
                .and()
                .formLogin().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}
