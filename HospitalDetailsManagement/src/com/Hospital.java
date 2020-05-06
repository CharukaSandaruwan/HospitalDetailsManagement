package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class Hospital {
	
	private Connection connect() {

		
		Connection con = null;

		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return con;

	
	}
	
	public String insertHospitalDetails(String hospital_id, String hospital_name, String hospital_location, int hospital_availableRooms,String hospital_labs) {
	
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into m_hospital (`m_hospital_id`,`m_hospital_name`,`m_hospital_location`,`m_hospital_availableRooms`,`m_hospital_labs`) values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, hospital_id);
			preparedStmt.setString(2, hospital_name);
			preparedStmt.setString(3, hospital_location);
			preparedStmt.setInt(4,hospital_availableRooms );
			preparedStmt.setString(5, hospital_labs);
			

			// execute the statement
			
			preparedStmt.execute();
			con.close();
			
			String newHospital = readHospitalDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readHospitalDetails() {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>hospital_id</th>"
					+ "<th>hospital_name</th>"
					+ "<th>hospital_location</th>"
					+ "<th>hospital_availableRooms</th>"
					+ "<th>hospital_labs</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";

			String query = "select * from m_hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				//String code = Integer.toString(rs.getInt("itemID"));
				String hospital_id = rs.getString("m_hospital_id");
				String hospital_name = rs.getString("m_hospital_name");
				String hospital_location =rs.getString("m_hospital_location");
				String hospital_availableRooms = Integer.toString(rs.getInt("m_hospital_availableRooms"));
				String hospital_labs= rs.getString("m_hospital_labs");

			/*	// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate' name='hidHospitalIDUpdate' type='hidden' value='" + hospital_id +"'>" + hospital_name +  "</td>";
			
				//output += "<td>" + hospital_id + "</td>";
				//output += "<td>" + hospital_name + "</td>";
				output += "<td>" + hospital_location + "</td>";
				output += "<td>" + hospital_availableRooms + "</td>";
				output += "<td>" + hospital_labs + "</td>";
			 */
				// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate'name='hidHospitalIDUpdate' type='hidden' value='" + hospital_id+ "'>"+ hospital_id + "</td>";
				output += "<td>" + hospital_name + "</td>";
				output += "<td>" + hospital_location + "</td>";
				output += "<td>" + hospital_availableRooms + "</td>";
				output += "<td>" + hospital_labs + "</td>";
				
				// buttons
			/*	output += "<td><input name='btnUpdate' type='button'
						 value='Update'
						 class='btnUpdate btn btn-secondary'></td>
						 <td><input name='btnRemove' type='button'
						 value='Remove'
						 class='btnRemove btn btn-danger' data-itemid='"
						 + itemID + "'>" + "</td></tr>"; 
						
				
				/*output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ hospital_id + "'>" + "</td></tr>";
*/
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ hospital_id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}

		return output;

	}
	
	
	
	public String updateHospitalDetails(String hospital_id, String hospital_name, String hospital_location, int hospital_availableRooms, String hospital_labs) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for updating.";

			}

			// create a prepared statement
			String query = "UPDATE m_hospital SET m_hospital_name=?,m_hospital_location=?,m_hospital_availableRooms=?,m_hospital_labs=?      WHERE m_hospital_id=?";
			//m_hospital_id=?
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			
			preparedStmt.setString(1, hospital_name);
			preparedStmt.setString(2, hospital_location);
			preparedStmt.setInt(3, hospital_availableRooms);
			preparedStmt.setString(4, hospital_labs);
			preparedStmt.setString(5, hospital_id);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newHospital = readHospitalDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";;

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the hospital details.\"}";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String deleteHospitalDetails(String hospital_id) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for deleting.";

			}

			// create a prepared statement

			String query = "delete from m_hospital where m_hospital_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, hospital_id);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newHospital = readHospitalDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the hospital.\"}";
			System.err.println(e.getMessage());

		}

		return output;

	}
	

	

	
	

}
