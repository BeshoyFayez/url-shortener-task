package com.urlshortener.service;

import com.urlshortener.entity.User;
import com.urlshortener.repository.UserRepository;
import com.urlshortener.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author beshoy
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User save(User user) {

		String password = PasswordUtil.getPasswordHash(user.getPassword());
		user.setPassword(password);
		User dbuser = this.userRepository.save(user);

		return dbuser;
	}

	@Override
	public List<User> findAll() {

		return this.userRepository.findAll();
	}

	@Override
	public User findUserByEmail(String name) {

		return userRepository.findByEmail(name);
	}
}
