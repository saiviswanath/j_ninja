package com.xyz.authenticationservice.services;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * Simplistic brute force login detection. After 3 failed login attempts, prevents login for 5
 * minutes. Login attempts made during these 5 minutes do not reset the 5 minute timer.
 */
@Service
public class UserLockingService implements ApplicationListener<AbstractAuthenticationEvent> {
  private static final int MAX_LOGIN_FAILURES = 3;
  private static final int LOCK_DURATION_SECONDS = 5 * 60;

  @Autowired
  private UserDetailsManager userDetailsManager;

  @Override
  public void onApplicationEvent(AbstractAuthenticationEvent event) {
    String username = event.getAuthentication().getName();
    if (username == null) {
      username = "";
    }
    String lowerCaseUserName = username.toLowerCase();
    if (event instanceof AuthenticationFailureBadCredentialsEvent) {
      // ignore login events for non-users since their attempts will never succeed
      if (userDetailsManager.userExists(lowerCaseUserName)) {
        registerLoginFailure(lowerCaseUserName);
      }
    } else if (event instanceof AuthenticationSuccessEvent) {
      registerLoginSuccess(lowerCaseUserName);
    }
  }

  private final Map<String, FailedLogin> loginFailures = new HashMap<String, FailedLogin>();

  public synchronized boolean isUserLocked(String username) {
    FailedLogin failedLogin = loginFailures.get(username);
    return failedLogin != null && failedLogin.isLocked();
  }

  public synchronized void registerLoginFailure(String username) {
    if (username == null) {
      username = "";
    }
    String lowerCaseUserName = username.toLowerCase();
    FailedLogin failedLogin = loginFailures.get(lowerCaseUserName);
    if (failedLogin == null) {
      failedLogin = new FailedLogin();
      loginFailures.put(lowerCaseUserName, failedLogin);
    }
    failedLogin.recordFailedLogin();
  }

  public synchronized void registerLoginSuccess(String username) {
    if (username == null) {
      username = "";
    }
    String lowerCaseUserName = username.toLowerCase();
    loginFailures.remove(lowerCaseUserName);
  }

  private static class FailedLogin {

    private int failureCount;
    private DateTime lockedUntil;

    public FailedLogin() {
      failureCount = 0;
      lockedUntil = null;
    }

    /**
     * Until user is locked, increment failure count and last failure time.
     */
    public void recordFailedLogin() {
      // is timer has expired, clear it and reset failure counter and
      if (lockedUntil != null && lockedUntil.isBeforeNow()) {
        failureCount = 0;
        lockedUntil = null;
      }

      // if user isn't already locked, increment failure counter up to MAX_LOGIN_FAILURES
      // (subsequent failures
      // don't matter)
      if (lockedUntil == null && failureCount < MAX_LOGIN_FAILURES) {
        failureCount++;
      }

      // if user is now locked, set lock expiration timer
      if (failureCount >= MAX_LOGIN_FAILURES) {
        lockedUntil = new DateTime().plusSeconds(LOCK_DURATION_SECONDS);
      }
    }

    public boolean isLocked() {
      return lockedUntil != null && lockedUntil.isAfterNow();
    }

  }
}
