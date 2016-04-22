import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
	private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:";
	private static final Properties PROPERTIES = PropertiesLoad.getProperties();

	private DBConnector() {}

	public static Connection getConnection(String userName, String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL + 
					PROPERTIES.getProperty(PropertyConstants.RESTDB_DBSCHEMA), 
					userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
