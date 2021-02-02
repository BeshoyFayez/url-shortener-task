package com.urlshortener.controller;

import com.urlshortener.entity.User;
import com.urlshortener.security.JwtTokenUtil;
import com.urlshortener.security.JwtUser;
import com.urlshortener.security.Response;
import com.urlshortener.security.UserDto;
import com.urlshortener.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author beshoy
 *
 */
@RestController
public class AuthenticationController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
			String jwtToken = jwtTokenUtil.generateToken(jwtUser);
			response.setHeader("token", jwtToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		      return  new ResponseEntity<UserDto>(new UserDto(jwtToken), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response("access is denied"), HttpStatus.UNAUTHORIZED);

		}
	}

	@PostMapping("/registeration")
	public ResponseEntity<Response> registeration(@RequestBody User user) {

		User existUser = this.userService.findUserByEmail(user.getEmail());
		if (existUser != null) {
			return new ResponseEntity<Response>(new Response("email is already exist"), HttpStatus.BAD_REQUEST);
		}

		User createdUuser = this.userService.save(user);

		if (createdUuser != null) {
			return new ResponseEntity<Response>(new Response("User is saved successfully"), HttpStatus.CREATED);
		}

		return new ResponseEntity<Response>(new Response("Error "), HttpStatus.BAD_REQUEST);

	}
}
