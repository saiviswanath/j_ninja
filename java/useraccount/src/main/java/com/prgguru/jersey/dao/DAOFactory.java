package com.prgguru.jersey.dao;

import com.prgguru.jersey.DBEnum;

/**
 * Ref: http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 * @author viswa
 *
 */
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
