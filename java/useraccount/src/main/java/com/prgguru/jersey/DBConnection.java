package com.prgguru.jersey;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	/**
	 * Method to create DB Connection
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(Constants.dbClass);
			con = DriverManager.getConnection(Constants.dbUrl,
					Constants.dbUser, Constants.dbPwd);
			System.out.println("Connected to DB");
		} catch (Exception e) {
			throw e;
		} finally {
			return con;
		}
	}
}
