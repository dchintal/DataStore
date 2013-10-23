package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class GetConnection {
 
  public static void main(String[] argv) {
  new GetConnection();
  }
  GetConnection (){
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
		e.printStackTrace();
		return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try {
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/testdata","root", "");
 
		getData(connection, "dheesid");
		updateData(connection,"dheesid",60);
		
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
  }
  
  public int getData(Connection connection,String userName) {
	if (connection != null) {
		int balance =0;
		//Statement statement;
		try {
			//statement = connection.createStatement();
			//System.out.println("You made it, take control your database now!");
			//PreparedStatement psCheck=null;
			PreparedStatement psSta = connection.prepareStatement("select * from userinfo where userid = ?");
			 psSta.setString(1, userName);
			// statement.executeQuery();
			 ResultSet resultSet = psSta.executeQuery();

		    while (resultSet.next()) {  //retrieve data
		        balance = resultSet.getInt(2);
		               System.out.println(balance);   
		     }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return balance;	
	} else {
		System.out.println("Failed to make connection!");
		return 0;
	}
	
	
  }
  
  public boolean updateData(Connection connection,String userName,int unitValue) {
	  boolean data=false;
		if (connection != null) { 
			//Statement statement;
			try {
				//statement = connection.createStatement();
				//System.out.println("You made it, take control your database now!");
				//ResultSet resultSet = statement.executeQuery("select * from userinfo");
				PreparedStatement psSta = connection.prepareStatement("update userinfo set units= ? where userid = ?");
				psSta.setInt(1, unitValue); 
				psSta.setString(2, userName);
				// statement.executeQuery();
			int rowsUpdates  = psSta.executeUpdate();
			if (rowsUpdates == 1)
				 data = true;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		} else {
			System.out.println("Failed to make connection!");
		}
		return data;
	  }
}