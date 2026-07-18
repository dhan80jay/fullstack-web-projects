package com.dhananjay.hospitalmanagement.security;

 
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	JwtService jwtService;
	ApplicationContext context;
	
	
	public JwtFilter(JwtService jwtService,ApplicationContext context) {
 		this.jwtService = jwtService;
 		this.context = context;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException
			 {
		
		String authHeader = request.getHeader("Authorization");
		String userName = null;
		String token = null;
	    try {
		//Getting Token and UserName
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
		    
			token = authHeader.substring(7);
			userName = jwtService.extractUserNameFromToken(token);
		
		}
		
		//Validating Token
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails =  context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);
			
			
			if(jwtService.validateToken(token,userDetails)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		}
	 	filterChain.doFilter(request, response);

	    
	    } catch (ExpiredJwtException e) {
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	response.getWriter().write("JWT Token has expired");
	    	return;
 		
	    } catch (SignatureException e) {
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	response.getWriter().write("Invalid JWT Signature");
	    	return;
 		
		} catch (MalformedJwtException e) {
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.getWriter().write("Malformed JWT Token");
		    return;
		} catch (UnsupportedJwtException e) {
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.getWriter().write("Unsupported JWT Token");
		    return;
		}catch (IllegalArgumentException e) {
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.getWriter().write("JWT Token is missing");
		    return;
		} catch (UsernameNotFoundException e) {
	    	System.out.println(e.getMessage());

		} catch (IOException e) {
	    	System.out.println(e.getMessage());
  		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
