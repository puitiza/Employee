package com.forceclose.Employee.config.jwt;

import com.forceclose.Employee.services.config.JwtUserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***For any incoming request this Filter class gets executed. It checks if the request has a valid JWT token.
 *  If it has a valid JWT Token then it sets the Authentication in the context, to specify that the current user
 *  is authenticated.*/
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/*public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
								  JwtTokenUtil jwtTokenUtil, JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl) {
		super(authenticationManager);
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtUserDetailsServiceImpl = jwtUserDetailsServiceImpl;
	}*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			final String requestTokenHeader = request.getHeader(JwtProperties.HEADER_STRING);

			String username = null;
			String jwtToken = null;
			// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
			if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
				}
			} else {
				logger.warn("JWT Token does not begin with Bearer String");
			}

			//Once we get the token validate it.
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.jwtUserDetailsServiceImpl.loadUserByUsername(username);

				// if token is valid configure Spring Security to manually set authentication
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					//JwtUserInfo principal = new JwtUserInfo(userDetails);

					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							userDetails.getUsername(), null, userDetails.getAuthorities());
					//auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			chain.doFilter(request, response);
		}catch (Exception e){
			logger.error(e.getMessage());
		}

	}

}
