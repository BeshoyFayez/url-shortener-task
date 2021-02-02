package com.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.urlshortener.entity.User;

/**
 * @author beshoy
 *
 */
@CrossOrigin
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
