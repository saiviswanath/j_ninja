package com.prgguru.jersey.dao;

import java.sql.SQLException;

public interface UserAccountDAO {
	boolean checkLogin(String uname, String pwd) throws Exception;
	boolean insertUser(String name, String uname, String pwd) throws SQLException, Exception;
}
