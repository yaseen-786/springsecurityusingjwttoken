package com.citiustech.springsecurity.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.citiustech.springsecurity.config.UserInfoUserDetails;
import com.citiustech.springsecurity.config.UserInfoUserDetailsService;
import com.citiustech.springsecurity.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtservice;
	
	@Autowired
	private UserInfoUserDetailsService userdetailsservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authheader = request.getHeader("Authorization");
		String token = null;
		String username=null;
		if(authheader!=null && authheader.startsWith("Bearer ")) {
			token = authheader.substring(7);
			username = jwtservice.extractUsername(token);
		}
		
		if(username!=null &&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails =userdetailsservice.loadUserByUsername(username);
			if(jwtservice.validateToken(token, userdetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( userdetails,null,userdetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
