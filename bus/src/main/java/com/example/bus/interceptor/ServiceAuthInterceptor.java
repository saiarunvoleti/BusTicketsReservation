package com.example.bus.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.bus.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ServiceAuthInterceptor implements HandlerInterceptor{

	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
			String authHeader = request.getHeader("Authorization");
	
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return false;
	        }
	
	        String token = authHeader.substring(7);
	
	        // Validate token here (e.g. using JwtUtil)
	        String role = jwtUtil.getRoleFromToken(token);
	
	        System.out.println(role);
	        if (!"Traveller".equalsIgnoreCase(role)) {
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            return false;
	        }
		return true;
	}
	
}
