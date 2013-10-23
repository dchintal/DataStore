package dataProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbConnection.generalConnections;

/**
 * Servlet implementation class GetData
 */
@WebServlet("/GetData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			getRequestDetails(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

public void getRequestDetails(HttpServletRequest request, 
			HttpServletResponse response) throws IOException, SQLException{

generalConnections dbc = new generalConnections();
Connection conn = dbc.dbLogin();
if (conn != null)
{
//PreparedStatement ps5 = null; 

String gender = request.getParameter("gId");
String setGen=null;
System.out.println("++++++++++");
if(gender.equals("Male")) {
	setGen="M";
}
else if(gender.equals("Female")) {
	setGen="F";
}
System.out.println(gender);
System.out.println("++++++++++");
String minAge = request.getParameter("c_minAge");

String maxAge = request.getParameter("c_maxAge");

String interest = request.getParameter("interestId");
int year = Calendar.getInstance().get(Calendar.YEAR);
//System.out.println(year);
int maxYear =0,minYear =year;
if(minAge !=null && !minAge.isEmpty()) {
int buffminAge = Integer.parseInt(minAge);	
minYear = year-buffminAge;
}
if(maxAge !=null && !minAge.isEmpty()) {
	int buffmaxAge = Integer.parseInt(maxAge);	
	maxYear = year-buffmaxAge;
}
System.out.println("MinYear" +minYear);
System.out.println("MaxYear" +maxYear);

//int maxYear = year-buffminAge;
//System.out.println(minYear);
PreparedStatement psCheck=null;

if(!interest.equals("Any") && !gender.equals("Any")) {
psCheck = conn.prepareStatement("select * from userinfo p2 where p2.personId in (select p1.personid from " +
		 			 "PERSON p1,PERSONINTERESTS pi1 where pi1.PERSONID = p1.PERSONID" +
		 			 " and p1.gender = ? and EXTRACT(YEAR FROM p1.BIRTHDATE) <= ?" +
		 			 " and EXTRACT(YEAR FROM p1.BIRTHDATE) >= ? and pi1.interest = ? and p1.personid = p2.personid)");
psCheck.setString(1, setGen);
psCheck.setInt(2, minYear);
psCheck.setInt(3, maxYear);
psCheck.setString(4, interest);
} else if(gender.equals("Any") && !interest.equals("Any")) {
psCheck = conn.prepareStatement("select * from userinfo p2 where p2.personId in (select p1.personid from " +
			 "PERSON p1,PERSONINTERESTS pi1 where EXTRACT(YEAR FROM p1.BIRTHDATE) <= ?" +
				" and pi1.PERSONID = p1.PERSONID and EXTRACT(YEAR FROM p1.BIRTHDATE) >= ?" +
				" and pi1.interest = ? and p1.personid = p2.personid)");
psCheck.setInt(1, minYear);
psCheck.setInt(2, maxYear);
psCheck.setString(3, interest);
}else if(interest.equals("Any") && !gender.equals("Any")){
	psCheck = conn.prepareStatement("select * from userinfo p2 where p2.gender=? and p2.personId in (select p1.personid from " +
			 "PERSON p1,PERSONINTERESTS pi1 where p1.gender=? and EXTRACT(YEAR FROM p1.BIRTHDATE) <= ?" +
				" and pi1.PERSONID = p1.PERSONID and EXTRACT(YEAR FROM p1.BIRTHDATE) >= ? and p1.personid = p2.personid)");
	String gen = "M";
	if(setGen.equals("F")) {
		gen = "W";
	} else {
		gen = setGen;
	}
psCheck.setString(1, gen);
psCheck.setString(2, setGen);
psCheck.setInt(3, minYear);
psCheck.setInt(4, maxYear);
}else {
	psCheck = conn.prepareStatement("select * from userinfo p2 where p2.personId in (select p1.personid from " +
			 "PERSON p1,PERSONINTERESTS pi1 where EXTRACT(YEAR FROM p1.BIRTHDATE) <= ?" +
				" and pi1.PERSONID = p1.PERSONID and EXTRACT(YEAR FROM p1.BIRTHDATE) >= ? and p1.personid = p2.personid)");
psCheck.setInt(1, minYear);
psCheck.setInt(2, maxYear);	
}
SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
//System.out.println(rset.toString());
java.io.PrintWriter pfout = response.getWriter();
pfout.println("<html><head>");
pfout.println("<title>Product Details</title></head><body>");
pfout.println("<table border = '1'>");
pfout.println("<div><tr><th width='150'>Full Name</th><th width='150'>Gender</th>" +
		"<th width='150'>Person Id</th><th width='150'>Birth Date</th><th width='150'>Image Link</th></tr>");

try {
	ResultSet rset = psCheck.executeQuery();
	System.out.println(rset.isAfterLast());
	if(rset != null && rset.next()) {
do {	System.out.println("++++++++++++++");
pfout.println("<tr>");
pfout.println("<td>"+ rset.getString(1) +"</td>");
    System.out.println( rset.getString(1));
pfout.println("<td>"+ rset.getString(2) +"</td>");
pfout.println("<td>"+ rset.getInt(4) +"</td>");
pfout.println("<td>"+ sdf.format(rset.getDate(3)) +"</td>");
pfout.println("<td><a href="+rset.getString(5)+">Click Here</a></td>");
pfout.println("</tr>");
}while(rset.next());	
pfout.println("</table>");
	} else {
		pfout.println("</table>");
		pfout.println("<p align='center'>");
		pfout.println("No Matching Data Found");
		pfout.println("</p>");
		
	}
}catch (SQLException e ) {
    System.out.println(e);
    pfout.println("</table>");
	pfout.println("<p align='center'>");
	pfout.println("Record Fetching Failed");
	pfout.println("</p>");
}

pfout.println("</body></html>");
System.out.println("Row Inserted");
psCheck.close(); 		    
//loggedDetails(conn,response,userID);
		   
conn.close();
}else
response.sendRedirect("ErrorPage.jsp");
}
}
