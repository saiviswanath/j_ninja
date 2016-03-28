package com.prgguru.jersey;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prgguru.jersey.dao.UserAccountDAO;
import com.prgguru.jersey.dao.UserAccountDaoImpl;

//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
	private UserAccountDAO dao = new UserAccountDaoImpl();

	// HTTP Post Method
	@POST
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/doregister")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String doLogin(@FormParam("name") String name,
			@FormParam("username") String uname,
			@FormParam("password") String pwd) {
		String response = "";
		// System.out.println("Inside doLogin "+uname+"  "+pwd);
		int retCode = dao.registerUser(name, uname, pwd);
		if (retCode == 0) {
			response = Utitlity.constructJSON("register", true);
		} else if (retCode == 1) {
			response = Utitlity.constructJSON("register", false,
					"You are already registered");
		} else if (retCode == 2) {
			response = Utitlity
					.constructJSON("register", false,
							"Special Characters are not allowed in Username and Password");
		} else if (retCode == 3) {
			response = Utitlity.constructJSON("register", false,
					"Error occured");
		}
		return response;

	}

	public static void main(String... args) {
		UserAccountDAO dao = new UserAccountDaoImpl();
		dao.registerUser("shanti", "shanti", "shanti");
	}

}
