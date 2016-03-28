package com.prgguru.jersey.dao;

import java.sql.SQLException;

import com.prgguru.jersey.DBConnection;
import com.prgguru.jersey.Utitlity;

public class UserAccountDaoImpl implements UserAccountDAO {

	@Override
	public boolean checkCredentials(String uname, String pwd) {
		System.out.println("Inside checkCredentials");
		boolean result = false;
		if (Utitlity.isNotNull(uname) && Utitlity.isNotNull(pwd)) {
			try {
				result = DBConnection.checkLogin(uname, pwd);
				// System.out.println("Inside checkCredentials try "+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// System.out.println("Inside checkCredentials catch");
				result = false;
			}
		} else {
			// System.out.println("Inside checkCredentials else");
			result = false;
		}

		return result;
	}

	@Override
	public int registerUser(String name, String uname, String pwd) {
		System.out.println("Inside checkCredentials");
		int result = 3;
		if (Utitlity.isNotNull(uname) && Utitlity.isNotNull(pwd)) {
			try {
				if (DBConnection.insertUser(name, uname, pwd)) {
					System.out.println("RegisterUSer if");
					result = 0;
				}
			} catch (SQLException sqle) {
				System.out.println("RegisterUSer catch sqle");
				// When Primary key violation occurs that means user is already
				// registered
				if (sqle.getErrorCode() == 1062) {
					result = 1;
				}
				// When special characters are used in name,username or password
				else if (sqle.getErrorCode() == 1064) {
					System.out.println(sqle.getErrorCode());
					result = 2;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Inside checkCredentials catch e ");
				result = 3;
			}
		} else {
			System.out.println("Inside checkCredentials else");
			result = 3;
		}

		return result;
	}

}
