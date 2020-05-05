package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HosRooms {
	
private Connection connect() {

		
		Connection con = null;

		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return con;

	
	}
	
public String insertRooms(String room_code, String room_hospital_name, String room_doctor_name, String room_date,String room_time) {

	String output = "";

	try {
		Connection con = connect();

		if (con == null) {
			return "Error while connecting to the database for inserting.";
		}

		// create a prepared statement
		String query = " insert into t_room (`t_room_code`,`t_room_hospital_name`,`t_room_doctor_name`,`t_room_date`,`t_room_time`) values (?, ?, ?, ?, ?)";

		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values
		//preparedStmt.setInt(1, 0);
		preparedStmt.setString(1, room_code);
		preparedStmt.setString(2, room_hospital_name);
		//preparedStmt.setDouble(4, Double.parseDouble(price));
		preparedStmt.setString(3, room_doctor_name);
		preparedStmt.setString(4, room_date);
		preparedStmt.setString(5,room_time);

		// execute the statement
		preparedStmt.execute();
		con.close();

		output = "Inserted successfully";
	} catch (Exception e) {
		output = "Error while inserting the rooms.";
		System.err.println(e.getMessage());
	}

	return output;
}

public String readRoomDetails() {

	String output = "";

	try {

		Connection con = connect();

		if (con == null) {

			return "Error while connecting to the database for reading.";
		}

		// Prepare the html table to be displayed
		output = "<table border=\"1\"><tr><th>room_code</th><th>room_hospital_name</th><th>room_doctor_name</th><th>room_date</th><th>room_time</th><th>Update</th><th>Remove</th></tr>";

		String query = "select * from t_room";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		// iterate through the rows in the result set
		while (rs.next()) {
			//String itemID = Integer.toString(rs.getInt("itemID"));
			String room_code = rs.getString("t_room_code");
			String room_hospital_name = rs.getString("t_room_hospital_name");
			//String itemPrice = Double.toString(rs.getDouble("itemPrice"));
			String room_doctor_name = rs.getString("t_room_doctor_name");
			String room_date = rs.getString("t_room_date");
			String room_time = rs.getString("t_room_time");

			// Add into the html table
			output += "<tr><td>" + room_code + "</td>";
			output += "<td>" + room_hospital_name + "</td>";
			output += "<td>" + room_doctor_name + "</td>";
			output += "<td>" + room_date + "</td>";
			output += "<td>" + room_time + "</td>";

			// buttons
			output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
					+ "<td><form method=\"post\" action=\"items.jsp\">"
					+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
					+ "<input name=\"itemID\" type=\"hidden\" value=\"" + room_code + "\">" + "</form></td></tr>";

		}

		con.close();

		// Complete the html table
		output += "</table>";
	} catch (Exception e) {
		output = "Error while reading the room details.";
		System.err.println(e.getMessage());
	}

	return output;

}

public String updateRoomDetails(String room_code, String room_hospital_name, String room_doctor_name, String room_date,String room_time) {

	String output = "";

	try {

		Connection con = connect();

		if (con == null) {

			return "Error while connecting to the database for updating.";

		}

		// create a prepared statement
		String query = "UPDATE t_room SET t_room_hospital_name=?,t_room_doctor_name=?,t_room_date=?,t_room_time=?      WHERE t_room_code=?";

		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values
		
		preparedStmt.setString(1, room_hospital_name);
		//preparedStmt.setDouble(3, Double.parseDouble(price));
		preparedStmt.setString(2, room_doctor_name);
		preparedStmt.setString(3, room_date);
		preparedStmt.setString(4, room_time);
		preparedStmt.setString(5, room_code);
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

public String deleteRooms(String room_code) {

	String output = "";

	try {

		Connection con = connect();

		if (con == null) {

			return "Error while connecting to the database for deleting.";

		}

		// create a prepared statement

		String query = "delete from t_room where t_room_code=?";

		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values
		//preparedStmt.setInt(1, Integer.parseInt(room_code));
		preparedStmt.setString(1, room_code);

		// execute the statement
		preparedStmt.execute();
		con.close();

		output = "Deleted successfully";

	} catch (Exception e) {
		output = "Error while deleting the room.";
		System.err.println(e.getMessage());

	}

	return output;

}

//	@Override
//	public String toString() {
//		return "Item [itemID=" + itemID + ", itemCode=" + itemCode + ", itemName=" + itemName + ", itemPrice="
//			+ itemPrice + ", itemDesc=" + itemDesc + "]";
//}


}
