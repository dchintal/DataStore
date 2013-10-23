package dbConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class generalConnections {

	public Connection dbLogin() throws SQLException
	{
		Driver myDriver = new oracle.jdbc.driver.OracleDriver();
		DriverManager.registerDriver(myDriver);
		String url = "jdbc:oracle:thin:@prophet.njit.edu:1521:course";
		//String url ="jdbc:oracle:thin:@" +
				//"(DESCRIPTION=(SDU=11280)(ADDRESS=(HOST=prophet.njit.edu)" +
				//"(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=course))";
		System.out.println("1");
		try {
		      String url1 = System.getProperty("JDBC_URL");
		
		      if (url1 != null)
		    	  url = url1;
		      System.out.println("1");
		    } catch (Exception 
		    		e) {
		      // If there is any security exception, ignore it
		      // and use the default
		    }
		Connection conn =
			      DriverManager.getConnection (url, "DC259", "tibKOW7J");
		System.out.println("1" +conn.toString());
		return conn;
	}
	
}
