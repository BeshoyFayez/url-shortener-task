package com.urlshortener.service;

import java.util.List;

import com.urlshortener.entity.User;

/**
 * @author beshoy
 *
 */
public interface UserService {
	
  public User save(User user);

  public List<User> findAll();

  public User findUserByEmail(String name);
}
