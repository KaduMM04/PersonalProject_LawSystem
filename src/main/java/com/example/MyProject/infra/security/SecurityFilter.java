package com.example.MyProject.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.MyProject.exceptions.CustomSecurityException;
import com.example.MyProject.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	    try {
	        // Recupera o token do request
	        var token = this.recoverToken(request);
	        
	        if (token != null) {
	            // Valida o token e recupera o email do usuário
	            var login = tokenService.validateToken(token);
	            UserDetails user = userRepository.findByEmail(login);
	            
	            // Verifica se o usuário foi encontrado
	            if (user == null) {
	                throw new CustomSecurityException("User not found for the provided token.");
	            }

	            // Configura a autenticação no contexto de segurança
	            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }
	        
	       
	        filterChain.doFilter(request, response);
	        
	    } catch (CustomSecurityException e) {
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.getWriter().write(e.getMessage());
	    } catch (Exception e) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        response.getWriter().write("An unexpected error occurred.");
	        e.printStackTrace();
	    }
	}
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		
		if (authHeader == null) return null;
		return authHeader.substring(7);// Remove "Bearer " do início
	}

}
