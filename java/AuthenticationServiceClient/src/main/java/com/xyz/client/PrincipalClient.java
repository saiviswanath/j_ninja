package com.xyz.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.xyz.beans.PagedCommand;
import com.xyz.beans.PagedResult;
import com.xyz.beans.Principal;
import com.xyz.beans.SortablePagedCommand;
import com.xyz.config.Configuration;
import com.xyz.response.PrincipalResponse;
import com.xyz.response.PrincipalsResponse;

@Component
public class PrincipalClient extends AbstractAuthClient {
  public static final int MAX_USER_RECORDS = 10000;

  @Autowired
  private RestTemplate restTemplate;

  public PrincipalClient(Configuration config) {
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
  
  public static void main(String... args) {
    PrincipalClient pc= new PrincipalClient(new Configuration());
    SortablePagedCommand pg = new SortablePagedCommand();
    pg.setFirst(0);
    pg.setMax(30);
    pg.setSort("username");
    pg.setSortDir("desc");
    pc.getPrincipals(pg);
  }
}
