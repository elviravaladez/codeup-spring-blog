package com.spring.springblog;

import com.spring.springblog.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsLoader userDetailsLoader;

    public SecurityConfiguration(UserDetailsLoader userDetailsLoader) {
        this.userDetailsLoader = userDetailsLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Fluent interface: One where you do a bunch of method chaining

        /* Login configuration */
        //define how to log in
        http.formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/home") // user's home page, it can be any URL
            .permitAll() // Anyone can go to the login page - don't have to be logged in

            /* Logout configuration */
            .and()
            .logout()
            .logoutSuccessUrl("/login?logout") // append a query string value

            /* Pages that can be viewed without having to log in */
            .and()
            .authorizeRequests()
            .antMatchers("/", "/posts","/sign-up") // anyone can see the home and the posts pages
            .permitAll()

            /* Pages that require authentication */
            .and()
            .authorizeRequests()
            .antMatchers("/posts/*") // only authenticated users can create, edit, delete etc. posts
            .authenticated()
        ;
    }
}
