package com.urlshortener.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author beshoy
 *
 */
public class PasswordUtil {
static  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String getPasswordHash(String password) {
   return  encoder.encode(password);
  }
}
