package com.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlshortener.entity.Url;

/**
 * @author beshoy
 *
 */
@Repository
public interface UrlRepository extends JpaRepository<Url,String> {
}
