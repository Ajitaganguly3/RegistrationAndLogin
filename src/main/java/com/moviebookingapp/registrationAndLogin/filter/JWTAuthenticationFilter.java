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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.moviebookingapp.registrationAndLogin.service.UserProfileServiceImpl;
import com.moviebookingapp.registrationAndLogin.util.JWTUtil;

@WebFilter(urlPatterns = "/*")
@Component
public class JWTAuthenticationFilter implements Filter{

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	UserProfileServiceImpl userProfileServiceImpl;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String requestTokenHeader = request.getHeader("Authorization");
		String userName = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
				 userName = jwtUtil.getloginIdFromToken(jwtToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.userProfileServiceImpl.loadUserByUsername(userName);
			
			if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Token is not validated");
			}
		
		}
		
		filterChain.doFilter(request, response);
	}
	
}
