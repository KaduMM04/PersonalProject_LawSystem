package com.example.MyProject.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	SecurityFilter securityFilter;
	
   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()	
						.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
						 .requestMatchers("/h2-console/**").permitAll() // Permite acesso ao console do H2
						 .requestMatchers(HttpMethod.POST, "/client").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/user").permitAll()
						.requestMatchers(HttpMethod.GET, "/lawyer").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/email/**").hasAnyRole("USERLAWYER" , "USERCLIENT")
						.requestMatchers(HttpMethod.GET, "/cases/**").hasAnyRole("USERLAWYER" , "USERCLIENT")
						.anyRequest().authenticated()
				)
				.headers(headers -> headers.frameOptions().disable()) // Necessário para o console do H2
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
   
   
   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	   return authenticationConfiguration.getAuthenticationManager();
   }
   
   @Bean
   PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder();
   }
}
