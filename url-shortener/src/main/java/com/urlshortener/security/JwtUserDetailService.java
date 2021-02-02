package com.urlshortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.urlshortener.entity.User;
import com.urlshortener.repository.UserRepository;

/**
 * @author beshoy
 *
 */
@Service
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepository.findByEmail(username);

		if (user == null) {

			throw new UsernameNotFoundException(String.format("No user found with username %s", username));
		}

		return new JwtUser(user);
	}

}
