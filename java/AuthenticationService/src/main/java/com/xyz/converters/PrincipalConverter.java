package com.xyz.converters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.xyz.beans.Principal;
import com.xyz.dto.PrincipalDto;

@Component
public class PrincipalConverter implements Converter<PrincipalDto, Principal> {

  @Override
  public Principal convert(PrincipalDto source) {
    Principal principal = new Principal();
    principal.setUsername(source.getUsername());
    principal.setEmail(source.getEmail());
    if (source.getEnabled() == 1) {
      principal.setEnabled(true);
    } else {
      principal.setEnabled(false);
    }
    principal.setRoles(source.getRoles());
    return principal;
  }

}
