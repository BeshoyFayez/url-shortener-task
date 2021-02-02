package com.urlshortener.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urlshortener.security.ErrorResponse;
import com.urlshortener.security.JwtTokenUtil;
import com.urlshortener.security.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class extends the Spring Web Filter OncePerRequestFilter class. For any
 * incoming request this Filter class gets executed. It checks if the request
 * has a valid JWT token. If it has a valid JWT Token then it sets the
 * Authentication in the context, to specify that the current user is
 * authenticated.
 * 
 * @author beshoy
 *
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailService userDetailsService;

	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		if ("/registeration".equals(path)) {
			filterChain.doFilter(request, response);
			return;
		}
		if ("/login".equals(path)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (path != null && path.contains("/h2/")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authToken = request.getHeader(AUTHORIZATION_HEADER);

		if (authToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authToken != null && authToken.startsWith("Bearer ")) {
			authToken = authToken.substring(7);
		}

		try {

			String userName = this.jwtTokenUtil.getUsernameFromToken(authToken);

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				boolean isVAlid = this.jwtTokenUtil.validateToken(authToken, userDetails);

				if (isVAlid) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}

			filterChain.doFilter(request, response);

		} catch (RuntimeException e) {

			// custom error response class used across my project
			ErrorResponse errorResponse = new ErrorResponse("An internal server error occurred.",e.getMessage());

			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(this.convertObjectToJson(errorResponse));
		}
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
