package com.xyz.authenticationservice.converters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.authenticationserviceclient.beans.Principal;

@Component
public class PrincipalConverter implements Converter<PrincipalDto, Principal> {

  @Override
  public Principal convert(PrincipalDto source) {
    Principal principal = new Principal();
    principal.setUsername(source.getUsername());
    principal.setFirstName(source.getFirstName());
    principal.setLastName(source.getLastName());
    principal.setEmail(source.getEmailAddress());
    principal.setPreferredTimeZoneId(source.getPreferredTimeZoneId());
    principal.setPasswordRequiresReset(source.isPasswordRequiresReset());
    principal.setDisabled(source.isArchived());
    principal.setLocked(source.isLocked());
    principal.setRoles(source.getRoles());
    return principal;
  }

}
