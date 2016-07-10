package com.xyz.authenticationserviceclient.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.authenticationserviceclient.beans.Principal;
import com.xyz.authenticationserviceclient.config.AuthConfiguration;
import com.xyz.authenticationserviceclient.response.PrincipalResponse;
import com.xyz.authenticationserviceclient.response.PrincipalsResponse;
import com.xyz.authenticationserviceclient.utilitybeans.PagedCommand;
import com.xyz.authenticationserviceclient.utilitybeans.PagedResult;

@Component
public class PrincipalClient extends AbstractAuthClient {
  public static final int MAX_USER_RECORDS = 10000;

  @Autowired
  private AuthRestTemplate restTemplate;

  public PrincipalClient(AuthConfiguration config) {
    super(config);
  }

  public Principal authenticate(String username, String password) {
    String url = getUrl("/principal/authenticate");

    Principal authRequest = new Principal();
    if (username == null) {
      username = "";
    }
    authRequest.setUsername(username.toLowerCase());
    authRequest.setPassword(password);
    PrincipalResponse response =
        restTemplate.postForObject(url, authRequest, PrincipalResponse.class);

    response.verify();
    return response.getPrincipal();
  }

  public Principal getPrincipal(String userName) {
    String url = getUrl("/principal/{username}");
    if (userName == null) {
      userName = "";
    }
    Map<String, String> varMap = new HashMap<>();
    varMap.put("username", userName.toLowerCase());
    PrincipalResponse response = restTemplate.getForObject(url, PrincipalResponse.class, varMap);
    response.verify();
    return response.getPrincipal();
  }

  public PagedResult<Principal> getPrincipals(PagedCommand command) {
    if (command == null) {
      command = new PagedCommand(MAX_USER_RECORDS);
    }
    String url = getUrl("/principal", command);
    PrincipalsResponse response = restTemplate.getForObject(url, PrincipalsResponse.class);
    response.verify();
    return response.getPrincipals();
  }

  public Principal createPrincipal(Principal principal) {
    principal.setUsername(principal.getUsername().toLowerCase());
    String url = getUrl("/principal/create");
    PrincipalResponse principalResponse =
        restTemplate.postForObject(url, principal, PrincipalResponse.class);
    principalResponse.verify();
    return principalResponse.getPrincipal();
  }
}
