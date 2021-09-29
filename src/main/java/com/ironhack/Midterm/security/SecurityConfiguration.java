package com.ironhack.Midterm.security;

import com.ironhack.Midterm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    @Primary
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/accounts/balance/*").hasAnyRole("ACCOUNT_HOLDER","ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/users/*").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/accounts/transfer").hasAnyRole("ACCOUNT_HOLDER", "THIRD_PARTY");
    }

}


