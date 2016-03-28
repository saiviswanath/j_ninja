package com.prgguru.jersey.dao;

public abstract class DAOFactory {
	public static final int MYSQL = 1;

	public abstract UserAccountDAO getUserAccountDAO();

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case MYSQL:
			return new MYSQLDAOFactory();
		default:
			return null;
		}
	}
}
