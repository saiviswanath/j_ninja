package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.RoleMap;

public class LDAPTest extends HttpServlet {
  /**
   *    
   */
  private static final long serialVersionUID = 1L;

  /* (non-Javadoc)

   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
  IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    HttpSession session = req.getSession();
    pw.println("<html><body><h1>" + (String) session.getAttribute("user")+ "</h1><br/><h1>" + ((RoleMap) session.getAttribute("rolemap")).toString() + "</h1><br/><h1>"+ req.getPathInfo()+"</h1>" +"</body></html>");
    pw.close();
  }

  /* (non-Javadoc)
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public static void main(String[] args) {

  }

}
