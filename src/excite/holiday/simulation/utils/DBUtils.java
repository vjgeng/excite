package excite.holiday.simulation.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {

	public static Connection getConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/exciteholiday";
		String username = "root";
		String password = "root";

		Class.forName(driver); // load Oracle driver
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

}
