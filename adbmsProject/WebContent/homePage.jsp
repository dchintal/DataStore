<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="dbConnection.*" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
		generalConnections dbc = new generalConnections();
		Connection conn = dbc.dbLogin();				
		if (conn != null) {		
			Statement stmt = conn.createStatement ();
			PreparedStatement psCheck = conn.prepareStatement("select distinct interest from PERSONINTERESTS ");			
			ResultSet rset = psCheck.executeQuery ();							    
			while(rset.next())
			{				
			    System.out.println( rset.getString(1));
				
			}										
		rset.close();
		stmt.close();
		conn.close();
	}else 
		response.sendRedirect("ErrorPage.jsp");
		
	%>
<html>
<head>
<link href="css/vps.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	
	</head>
<body>

<div id="content-container" align="center">
<div class="main" align="center">
<form name = "usermap" method="post" action="GetData" >
<table>
<tr><td> Select the Type</td>
	<td> <select id="genderId" name="gId">
	<option value="Any">Any</option>	
  <option value="Male">Male</option>
  <option value="Female">Female</option>
</select></td> </tr>
<tr><td> Minimum Age</td>
	<td> <input type="text" id="c_minAge" name ="c_minAge"></td> </tr>
<tr><td> Maximum Age</td>
	<td> <input type="text" id="c_maxAge" name ="c_maxAge"></td> </tr>
<tr><td> Select the Interest</td>
	<td> <select id="userInterest" name="interestId">
  <option value="Any">Any</option>
  <option value="Coins">Coins</option>
  <option value="Cycling">Cycling</option>
  <option value="Dancing">Dancing</option>
  <option value="Kites">Kites</option>
  <option value="Movies">Movies</option>
  <option value="Music">Music</option>
   <option value="Politics">Politics</option>
  <option value="Running">Running</option>
  <option value="skiing">skiing</option>
  <option value="Stamps">Stamps</option>
  <option value="Swimming">Swimming</option>
  <option value="Toy_Soilders">Toy_Soilders</option>
  <option value="Weapons">Weapons</option>
</select></td> </tr>
</table>
<input type="Submit" id="Submit" value="Submit"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" id="reset">

</form>
</div> </div>
</body>
</html>