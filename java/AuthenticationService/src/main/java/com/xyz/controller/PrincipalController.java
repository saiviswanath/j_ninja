package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xyz.beans.Principal;
import com.xyz.converters.PrincipalConverter;
import com.xyz.dto.PrincipalDto;
import com.xyz.response.PrincipalResponse;
import com.xyz.services.PrincipalService;

@Controller
@RequestMapping(value = "/principal")
public class PrincipalController {
  @Autowired
  private PrincipalService principalService;
  @Autowired
  private PrincipalConverter principalConverter;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PrincipalResponse> authenticatePrincipal(@RequestBody Principal authRequest) {
    String username = authRequest.getUsername();
    if (username != null && !username.isEmpty()) {
      username = username.toLowerCase();
    }
    PrincipalDto pDto = principalService.authenticatePrincipal(username, authRequest.getPassword());
    Principal convertedPrincipal = principalConverter.convert(pDto);
    return new ResponseEntity<>(new PrincipalResponse(convertedPrincipal), HttpStatus.OK);
  }
}
