package com.urlshortener.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urlshortener.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beshoy
 *
 */
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	private String password;
	private User user;
	private boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser(User user) {

		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.username = user.getEmail();
		this.enabled = true;
		this.user = user;
		this.authorities = this.mapToGrantedAuthorties(new ArrayList<String>(Arrays.asList("ROLE_" + user.getRole())));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<? extends GrantedAuthority> mapToGrantedAuthorties(List<String> authorties) {
		return authorties.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtUser other = (JwtUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JwtUser [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", user=" + user + ", enabled=" + enabled + ", authorities=" + authorities + "]";
	}
}
