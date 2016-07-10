package com.xyz.authenticationservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xyz.authenticationservice.converters.PrincipalConverter;
import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.authenticationservice.enums.SortColumn;
import com.xyz.authenticationservice.exceptions.AutheticationServiceException;
import com.xyz.authenticationservice.services.PrincipalService;
import com.xyz.authenticationservice.utilbeans.PageAndSortData;
import com.xyz.authenticationserviceclient.beans.Principal;
import com.xyz.authenticationserviceclient.enums.ExceptionCause;
import com.xyz.authenticationserviceclient.enums.SortDirection;
import com.xyz.authenticationserviceclient.response.PrincipalResponse;
import com.xyz.authenticationserviceclient.response.PrincipalsResponse;
import com.xyz.authenticationserviceclient.response.RestFulResponse;
import com.xyz.authenticationserviceclient.utilitybeans.Failure;
import com.xyz.authenticationserviceclient.utilitybeans.PagedResult;

@Controller
@RequestMapping(value = "/principal")
public class PrincipalController {
  private static final Integer ZERO = new Integer(0);
  private static final Integer MAX_VALUE = new Integer(Integer.MAX_VALUE);

  @Autowired
  private PrincipalService principalService;
  @Autowired
  private PrincipalConverter principalConverter;
  @Autowired
  private Validator validator;

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

  @RequestMapping(value = "/{username}")
  public ResponseEntity<PrincipalResponse> getPrincipal(@PathVariable String userName) {
    if (userName != null && !userName.isEmpty()) {
      userName = userName.toLowerCase();
    }
    PrincipalDto pDto = principalService.getPricipal(userName);
    Principal principal = principalConverter.convert(pDto);
    return new ResponseEntity<PrincipalResponse>(new PrincipalResponse(principal), HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PrincipalsResponse> getPrincipals(@RequestParam(value = "first",
  required = false) Integer first, @RequestParam(value = "max", required = false) Integer max,
  @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(
      value = "sortDirection", required = false) SortDirection sortDir) {
    List<Principal> principalList = null;
    try {
      if (first == null) {
        first = ZERO;
      }

      if (max == null) {
        max = MAX_VALUE;
      }

      if (sortDir == null) {
        sortDir = SortDirection.ASCENDING;
      }

      if (sortBy == null) {
        sortBy = SortColumn.USERNAME.getColName();
      }

      // Encapsulate the pagination and sort params into one object
      PageAndSortData pandsdata = new PageAndSortData();
      pandsdata.setFirst(first);
      pandsdata.setMax(max);
      pandsdata.setSort(sortBy);
      pandsdata.setSortDirection(sortDir);

      List<PrincipalDto> pDtoList = principalService.getPrincipals(pandsdata);
      if (pDtoList != null && pDtoList.size() > 0) {
        principalList = new ArrayList<>(pDtoList.size());
        for (PrincipalDto pDto : pDtoList) {
          principalList.add(principalConverter.convert(pDto));
        }
      } else {
        principalList = new ArrayList<>(0);
      }
    } catch (Exception e) {
      throw new AutheticationServiceException(ExceptionCause.UNEXPECTED,
          "Unable to fetch all pricipals from DB", e);
    }
    PagedResult<Principal> pagedPricipal = new PagedResult<>(principalList, principalList.size());
    return new ResponseEntity<>(new PrincipalsResponse(pagedPricipal), HttpStatus.OK);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<? extends RestFulResponse> createPrincipal(@RequestBody Principal principal) {
    Set<ConstraintViolation<Principal>> violations = validator.validate(principal, Default.class);
    if (!violations.isEmpty()) {
      return new ResponseEntity<RestFulResponse>(new RestFulResponse(new Failure(
          ExceptionCause.VALIDATION, "Invalid principal for create: " + violations)),
          HttpStatus.BAD_REQUEST);
    }
    principal.setPasswordRequiresReset(true);
    PrincipalDto principalDto = principalService.createPrincipal(principal);
    Principal convertedPrincipal = principalConverter.convert(principalDto);
    return new ResponseEntity<>(new PrincipalResponse(convertedPrincipal), HttpStatus.OK);
  }
}
