package com.xyz.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {
  public static String getCurrentUser() {
    UserDetails details = getUserDetails();
    if (details != null) {
      return details.getUsername();
    } else {
      return null;
    }
  }

  private static UserDetails getUserDetails() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails details = null;
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      details = (UserDetails) auth.getPrincipal();
    }
    return details;
  }
}
