package com.prgguru.jersey.dao;

public interface UserAccountDAO {
	boolean checkCredentials(String uname, String pwd);
	int registerUser(String name, String uname, String pwd);
}
