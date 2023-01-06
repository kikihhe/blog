package com.xiaohe.security;

import com.xiaohe.filter.LoginFiler;
import com.xiaohe.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 16:46
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFiler loginFiler() throws Exception {
        LoginFiler loginFiler = new LoginFiler();
        loginFiler.setAuthenticationManager(authenticationManagerBean());

        loginFiler.setAuthenticationSuccessHandler(new LoginSuccessHandler(stringRedisTemplate));
        loginFiler.setAuthenticationFailureHandler(new LoginFailureHandler());
        return loginFiler;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .anyRequest().permitAll();

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.formLogin().loginProcessingUrl("/login");

        http.addFilterAt(loginFiler(), UsernamePasswordAuthenticationFilter.class);
    }
}
