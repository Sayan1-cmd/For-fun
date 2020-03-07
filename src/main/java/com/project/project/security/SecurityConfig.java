package com.project.project.security;


import com.project.project.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetails;

    public SecurityConfig( PasswordEncoder encoder, UserDetailsServiceImpl userDetails) {
        this.encoder = encoder;
        this.userDetails = userDetails;
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails)
                .passwordEncoder(encoder)
                .and()
                .authenticationProvider(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(userDetails);
        return provider;
    }
}
