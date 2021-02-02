package com.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author beshoy
 *
 */
@Entity
@Table(name = "Url")

public class Url {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "SHORTURL")
	private String shortUrl;

	@Column(name = "LONGURL")
	private String longUrl;

	@Column(name = "SHORTENED_COUNT")
	private Long shortenedCount;

	@Column(name = "ACCESSED_COUNT")
	private Long accessCount;

	@Column(name = "USER_ID", insertable = false, updatable = false)
	private Long userId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@JsonIgnore
	@Transient
	private String userName;

	public Url() {
	}

	public Url(String id, String shortUrl, String longUrl, long shortenedCount, long accessCount, User user) {
		this.id = id;
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
		this.shortenedCount = shortenedCount;
		this.accessCount = accessCount;
		this.setUser(user);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Long getShortenedCount() {
		return shortenedCount;
	}

	public void setShortenedCount(Long shortenedCount) {
		this.shortenedCount = shortenedCount;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;

		if (this.user != null && this.user.getId() != null) {
			this.userId = this.user.getId();
			this.userName = this.user.getEmail();
		} else {
			this.userId = null;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		Url other = (Url) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Url [id=" + id + ", shortUrl=" + shortUrl + ", longUrl=" + longUrl + ", shortenedCount="
				+ shortenedCount + ", accessCount=" + accessCount + ", userId=" + userId + ", user=" + user
				+ ", userName=" + userName + "]";
	}

}
