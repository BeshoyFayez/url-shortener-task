package com.urlshortener.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;
import com.urlshortener.entity.Url;
import com.urlshortener.entity.User;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.UserService;

/**
 * @author beshoy
 *
 */
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	UrlRepository urlRepository;

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping(value = "/{id}")
	public ResponseEntity<String> redirectToUrl(@PathVariable String id) throws Exception {

		Url restoredUrl = this.urlRepository.findById(id).get();
		restoredUrl.setAccessCount(restoredUrl.getAccessCount() + 1L);
		this.urlRepository.save(restoredUrl);
		return ResponseEntity.ok(restoredUrl.getLongUrl());
	}

	@PostMapping
	public Url shortenUrl(@RequestBody Url url) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;

		if (principal instanceof UserDetails) {
			String email = ((UserDetails) principal).getUsername();
			user = this.userService.findUserByEmail(email);
		}

		String newUrl = "http://bit/";
		final String id = Hashing.murmur3_32().hashString(url.getLongUrl() + user.getEmail(), StandardCharsets.UTF_8)
				.toString();
		String requestUrl = newUrl;
		String prefix = requestUrl.substring(0, requestUrl.indexOf("/", "http://".length()));
		Url restoredUrl = this.urlRepository.findById(id)
				.orElse(new Url(id, prefix + "/" + id, url.getLongUrl(), 0L, 0L, user));
		restoredUrl.setShortenedCount(restoredUrl.getShortenedCount() + 1L);
		this.urlRepository.save(restoredUrl);
		return restoredUrl;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Url>> statistics() {
		List<Url> urls = this.urlRepository.findAll();
		return new ResponseEntity<>(urls, HttpStatus.OK);
	}

}
