package com.prgguru.jersey.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import com.prgguru.jersey.Constants;

public class MYSQLDAOFactory extends DAOFactory {

	@Override
	public UserAccountDAO getUserAccountDAO() {
		return new UserAccountDaoImpl();
	}

	/**
	 * Method to create DB Connection
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static Connection createConnection() {
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
