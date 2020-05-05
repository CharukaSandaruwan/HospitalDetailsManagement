package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminDetails {
	String username ="";
	String password ="";
	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}
	public String Adminloging(String Uname, String Password) {
		
		String output="test";
		
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for logging.";
			}
			
			String query = "SELECT  m_admin_uname,m_admin_password FROM m_admin WHERE m_admin_uname =? AND m_admin_password =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			preparedStmt.setString(1, Uname);
			preparedStmt.setString(2, Password);
			
			System.out.println("Uname"+Uname);
			//preparedStmt.execute();
			ResultSet rs = preparedStmt.executeQuery();
			while(rs.next()) {
				username = rs.getString("m_admin_uname");
				password = rs.getString("m_admin_password");
			}
			con.close();
			if(username.equalsIgnoreCase(Uname) && password.equalsIgnoreCase(Password)) {
				output = "success";
			}
		}catch(Exception e) {
			output = "Failed";
			System.err.println(e.getMessage());
		}
		return output;
		
		
	}		
	public String insertAdmin(String admin_id, String admin_uname, String admin_password,String admin_name) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into m_admin(`m_admin_id`,`m_admin_uname`,`m_admin_password`,`m_admin_name`) values (?, ?, ?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, admin_id);
			preparedStmt.setString(2, admin_uname);
			
			preparedStmt.setString(3, admin_password);
			preparedStmt.setString(4, admin_name);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	
	public String readAdmin() {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>admin_id</th><th>admin_uname</th><th>admin_password</th><th>admin_name</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from m_admin ";
			// where m_admin_uname=? AND m_admin_password=?
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			

			// iterate through the rows in the result set
			while (rs.next()) {
				//String itemID = Integer.toString(rs.getInt("itemID"));
				String admin_id = rs.getString("m_admin_id");
				String admin_uname = rs.getString("m_admin_uname");
				//String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String admin_password = rs.getString("m_admin_password");
				String admin_name = rs.getString("m_admin_name");

				// Add into the html table
				output += "<tr><td>" + admin_id + "</td>";
				output += "<td>" + admin_uname + "</td>";
				output += "<td>" + admin_password + "</td>";
				output += "<td>" + admin_name + "</td>";
				//output += "<td>" + itemDesc + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + admin_id + "\">" + "</form></td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Admin details.";
			System.err.println(e.getMessage());
		}

		return output;

}
	
	public String updateAdminDetails(String admin_id, String admin_uname, String admin_password,String admin_name) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for updating.";

			}

			// create a prepared statement
			String query = "UPDATE m_admin SET m_admin_uname=?,m_admin_password=?,m_admin_name=?      WHERE m_admin_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			
			preparedStmt.setString(1, admin_uname);
			//preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(2, admin_password);
			preparedStmt.setString(3, admin_name);
			preparedStmt.setString(4, admin_id);
			
			//preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the rooms.";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String deleteRooms(String admin_id) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for deleting.";

			}

			// create a prepared statement

			String query = "delete from m_admin where m_admin_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			//preparedStmt.setInt(1, Integer.parseInt(room_code));
			preparedStmt.setString(1, admin_id);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the admin.";
			System.err.println(e.getMessage());

		}

		return output;

	}
}