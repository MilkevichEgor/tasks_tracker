package com.fusiontech.milkevich.tasktracker.config;

import com.fusiontech.milkevich.tasktracker.security.AuthEntryPointJwt;
import com.fusiontech.milkevich.tasktracker.security.AuthTokenFilter;
import com.fusiontech.milkevich.tasktracker.security.JwtUtils;
import com.fusiontech.milkevich.tasktracker.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures the security settings for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  /**
   * method for authorization for public routes.
   */
  public final String[] publicRoutes = {
      "/auth/**",
  };

  /**
   * Creates and configures the JWT token filter for authentication.
   *
   * @return the configured AuthTokenFilter
   */
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter(jwtUtils, userDetailsService);
  }

  /**
   * Creates and configures the DaoAuthenticationProvider for authentication.
   *
   * @return The configured DaoAuthenticationProvider instance.
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  /**
   * Creates and configures the AuthenticationManager for authentication.
   *
   * @param authConfig - the AuthenticationConfiguration object
   * @return the configured AuthenticationManager
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  /**
   * Configures the password encoder for the application.
   *
   * @return the configured PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain for the application.
   * Disables CSRF protection, sets the authentication entry point for exceptions,
   * configures session management, and authorizes requests.
   * Adds an authentication provider and a JWT token filter to the filter chain.
   *
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain object
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exception ->
            exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req ->
            req
                .requestMatchers(publicRoutes).permitAll()
                .anyRequest().authenticated());

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
