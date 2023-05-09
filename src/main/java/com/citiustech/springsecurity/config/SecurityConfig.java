package com.citiustech.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.citiustech.springsecurity.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter authfilter;

	@Bean
	// authentication
	public UserDetailsService userdetailservices() {
		/*
		 * UserDetails admin = User.withUsername("yaseen")
		 * .password(encoder.encode("yaseen123")).roles("ADMIN").build(); UserDetails
		 * user = User.withUsername("user")
		 * .password(encoder.encode("user123")).roles("USER").build();
		 * 
		 * return new InMemoryUserDetailsManager(admin,user);
		 */
		return new UserInfoUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFliterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/products/welcome", "/products/new","/products/authenticate")
				.permitAll().and().authorizeHttpRequests().requestMatchers("/products/**").authenticated().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationprovider())
				.addFilterBefore(authfilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	 
	  @Bean public AuthenticationProvider authenticationprovider() {
	  DaoAuthenticationProvider authenticationprovider = new
	  DaoAuthenticationProvider();
	  authenticationprovider.setUserDetailsService(userdetailservices());
	  authenticationprovider.setPasswordEncoder(passwordEncoder()); 
	  return authenticationprovider; 
	  }
	  
	  @Bean
	  public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		  return config.getAuthenticationManager();
	  }
	 
}
