package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

  String[] staticResources = {
    "/resources/**",
    "/static/**",
    "/css/**",
    "/js/**",
    "/*.js"};

  String[] permittededUrls = {
    "/login",
    "/register",
    "/check_email**"};

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;



  // https://www.baeldung.com/spring-security-login 
  // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests((authz) -> authz
              .requestMatchers(staticResources).permitAll()
              .requestMatchers(permittededUrls).permitAll()
              .requestMatchers("/admins/**").hasRole("ADMIN")
              .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
              .anyRequest().authenticated())
              .formLogin(login -> login.loginPage("/login")
                      .loginProcessingUrl("/login/process")
                      .usernameParameter("email")
                      .successForwardUrl("/index")
                      .failureUrl("/login?error")
                      .permitAll())
              .logout(logout -> logout.logoutUrl("/logout")
                      .logoutSuccessUrl("/login?logout")
                      .invalidateHttpSession(true)
                      .deleteCookies("JSESSIONID")
                      .permitAll())
              .csrf((csrf) -> csrf.disable())
              .cors((cors) -> cors.disable())
              .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager() {
    return authentication -> authenticationProvider().authenticate(authentication);
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

}
