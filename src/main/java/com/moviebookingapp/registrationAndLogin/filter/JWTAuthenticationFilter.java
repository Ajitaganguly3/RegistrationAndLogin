package com.moviebookingapp.registrationAndLogin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviebookingapp.registrationAndLogin.util.JWTUtil;

@WebFilter(urlPatterns = "/*")
@Component
public class JWTAuthenticationFilter implements Filter{

	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String requestTokenHeader = request.getHeader("Authorization");
		String loginId = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
				 loginId = jwtUtil.getloginIdFromToken(jwtToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		filterChain.doFilter(request, response);
	}
	
}
