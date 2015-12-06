package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.directory.ldap.client.template.LdapConnectionTemplate;

import com.ldap.LDAPConnectionTemplateManager;
import com.main.Authorizer;
import com.model.RoleMap;
import com.xyz.db.DBConnector;

public class AuthorizationFilter implements Filter {
  private static LdapConnectionTemplate template;

  public static void main(String[] args) {}

  @Override
  public void destroy() {
    template = null;
    DBConnector.clean();
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException,
  ServletException {
    HttpServletRequest hReq = (HttpServletRequest) req;
    HttpServletResponse hRes = (HttpServletResponse) res;
    // Sample path, uid from open ldap ldif
    String path = hReq.getPathInfo();
    // Session per webapp for a user/browser combination.
    HttpSession session = hReq.getSession();
    Authorizer auth = new Authorizer();
    auth.setLDAPTemplate(template);
    RoleMap roleMap;
    String uid;
    if (session.isNew()) {
      // Gets the map
      uid = "jjones";
      synchronized (session) {
        session.setAttribute("user", uid);
        session.setMaxInactiveInterval(300);
      }
      roleMap = auth.buildMap(uid, path);
    } else {
      uid = (String) session.getAttribute("user");
      roleMap = auth.rebuildMap(uid, path, (RoleMap) session.getAttribute("rolemap"));
    }

    if (roleMap != null) {
      synchronized (session) {
        session.setAttribute("rolemap", roleMap);
      }
    }

    if (auth.isAuthorizedUser()) {
      fc.doFilter(hReq, hRes);
    } else {
      hRes.sendRedirect(hReq.getContextPath() + "/error1.jsp");
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    template = LDAPConnectionTemplateManager.getLDAPConnectionTemplate();
  }

}
