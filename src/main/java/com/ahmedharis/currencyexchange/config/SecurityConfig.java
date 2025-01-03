package com.ahmedharis.currencyexchange.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user1 =
        User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

    UserDetails user2 =
        User.builder()
            .username("employee")
            .password(passwordEncoder.encode("employee123"))
            .roles("EMPLOYEE")
            .build();

    UserDetails user3 =
        User.builder()
            .username("affiliate")
            .password(passwordEncoder.encode("affiliate123"))
            .roles("AFFILIATE")
            .build();
    UserDetails user4 =
        User.builder()
            .username("customer")
            .password(passwordEncoder.encode("customer123"))
            .roles("CUSTOMER")
            .build();

    return new InMemoryUserDetailsManager(user1, user2, user3);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(
            authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/calculate")
                    .hasAnyRole("ADMIN", "EMPLOYEE", "AFFILIATE", "CUSTOMER")
                    .anyRequest()
                    .permitAll())
        .httpBasic(withDefaults());
    return http.build();
  }
}
