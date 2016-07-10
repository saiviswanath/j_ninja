package com.xyz.authenticationserviceclient.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xyz.authenticationserviceclient.beans.Principal;

public class PrincipalValidator implements Validator {
  public static final String USERNAME_LOWER = "[a-z][a-z0-9_.@]*[a-z0-9]";
  public static final String USERNAME = "[^/|\"\\\\+ ]+";
  public static final String NAME = "[^/|\"\\\\+]+";

  @Override
  public boolean supports(Class<?> clazz) {
    return Principal.class.isAssignableFrom(clazz);

  }

  @Override
  public void validate(Object target, Errors errors) {
    Principal principal = (Principal) target;
    if (principal.getPassword() != null && !validatePassword(principal.getPassword())) {
      errors.rejectValue("principal.password", "Invalid Password");
    }
  }

  /**
   * Validates that the password meets length and complexity requirements: Length >= 8 Length <= 64
   * Complexity: Must contain 2 or more of the following types of characters: - lower case letter -
   * upper case letter - numeric digit - symbol character including: !"#$%&'()*+,-./
   * :;<=>?@[\]^_`{|}~ [ASCII 33-47, 58-60, 123-126]
   * 
   * @param password
   * @return
   */
  public static boolean validatePassword(String password) {
    boolean valid = true;
    if (password == null || password.trim().length() < 8 || password.trim().length() > 64) {
      valid = false;
    } else {
      boolean lower = false;
      boolean upper = false;
      boolean number = false;
      boolean symbol = false;
      for (int i = 0; i < password.length(); i++) {
        switch (Character.getType(password.charAt(i))) {

          case java.lang.Character.LOWERCASE_LETTER:
            lower = true;
            break;

          case java.lang.Character.UPPERCASE_LETTER:
            upper = true;
            break;

          case java.lang.Character.DECIMAL_DIGIT_NUMBER:
            number = true;
            break;

          case java.lang.Character.NON_SPACING_MARK: // Falls Thru
          case java.lang.Character.ENCLOSING_MARK: // Falls Thru
          case java.lang.Character.COMBINING_SPACING_MARK: // Falls Thru
          case java.lang.Character.DASH_PUNCTUATION: // Falls Thru
          case java.lang.Character.START_PUNCTUATION: // Falls Thru
          case java.lang.Character.END_PUNCTUATION: // Falls Thru
          case java.lang.Character.CONNECTOR_PUNCTUATION: // Falls Thru
          case java.lang.Character.OTHER_PUNCTUATION: // Falls Thru
          case java.lang.Character.MATH_SYMBOL: // Falls Thru
          case java.lang.Character.CURRENCY_SYMBOL: // Falls Thru
          case java.lang.Character.MODIFIER_SYMBOL: // Falls Thru
          case java.lang.Character.OTHER_SYMBOL: // Falls Thru
          case java.lang.Character.INITIAL_QUOTE_PUNCTUATION: // Falls Thru
          case java.lang.Character.FINAL_QUOTE_PUNCTUATION:
            symbol = true;
            break;
        }
      }

      int variants = 0;
      if (lower) {
        variants++;
      }
      if (upper) {
        variants++;
      }
      if (number) {
        variants++;
      }
      if (symbol) {
        variants++;
      }

      if (variants < 2) {
        valid = false;
      }
    }

    return valid;
  }
}
