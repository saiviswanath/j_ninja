package com.xyz.util;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.xyz.form.beans.ForGotPasswordBean;

/**
 * Ref: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html
 * 
 * @author viswa
 *
 */
public class UserEmailUtil {
  private JavaMailSender mailSender;
  private VelocityEngine velocityEngine;
  private String fromAddress;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public JavaMailSender getMailSender() {
    return mailSender;
  }

  public VelocityEngine getVelocityEngine() {
    return velocityEngine;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void sendPasswordResetEmail(final ForGotPasswordBean fPwdBean) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {

      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setTo(fPwdBean.getEmail());
        message.setFrom(getFromAddress());
        message.setSubject("Password Reset");
        Map<String, Object> model = new HashMap<>();
        model.put("forGotPasswordBean", fPwdBean);
        String text =
            VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                "com/xyz/emailtemplates/password-reset.vm", "UTF-8", model);
        message.setText(text, true);
      }
    };
    getMailSender().send(preparator);
  }

}
