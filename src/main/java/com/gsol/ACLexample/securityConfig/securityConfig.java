package com.gsol.ACLexample.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//            .passwordEncoder(passwordEncoder())
//            .authoritiesByUsernameQuery("select E.USER_NAME as USERNAME, EMP.ROLE as ROLE\n" +
//                    "from ( Select ER.EMPLOYEE_ID,R.ROLE\n" +
//                    "    from employee_role ER,role  R\n" +
//                    "    where R.ROLE_ID = ER.ROLE_ID )  EMP, employee E\n" +
//                    "where EMP.EMPLOYEE_ID = E.ID and E.USER_NAME = ?")
//            .usersByUsernameQuery("select USER_NAME as USERNAME, PASSWORD, 1 as enabled  from employee where USER_NAME = ?");

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN");

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
                .antMatchers(HttpMethod.GET, "**/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "**/employees").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "**/employees").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "**/employees/**").hasRole("ADMIN")
                .and()
                .formLogin().disable();
    }

//        @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return true;
//            }
//        };
//    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(new ArrayList<String>(Arrays.asList("*")));
//        configuration.setAllowedMethods(new ArrayList<String>(Arrays.asList("HEAD",
//                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(new ArrayList<String>(Arrays.asList("*")));
//        configuration.setExposedHeaders(new ArrayList<String>(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials")));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
