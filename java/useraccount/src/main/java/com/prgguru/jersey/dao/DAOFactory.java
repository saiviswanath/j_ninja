package com.prgguru.jersey.dao;

import com.prgguru.jersey.DBEnum;

public abstract class DAOFactory {

	public abstract UserAccountDAO getUserAccountDAO();

	public static DAOFactory getDAOFactory(DBEnum db) {
		switch (db) {
		case MYSQL:
			return new MYSQLDAOFactory();
		default:
			return null;
		}
	}
}
