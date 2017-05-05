package compass.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class DBQuery {

	Connection conn = null;

	int id;
	String objName;
	String surmmary;
	Long lastUpdated;
	Boolean update = false;

	public DBQuery(String table, String name) {
		EstablishDBConnection(table, name);

		// Is the information up to date and need updating
		if (UpdateTimeFrame.updateTimeFrame(table.toLowerCase(), lastUpdated) == true) {
			update = true;
		}
	}

	/** Establish connection to Database
	 * @param table the table to query
	 * @param name the value to look for within the table
	 */
	public void EstablishDBConnection(String table, String name) {

		try {
			// create our mysql database connection
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://csmysql.cs.cf.ac.uk/group9_2016";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "group9.2016", "g4QFghm5Kxm");

			// our SQL SELECT query.
			String query = "SELECT * FROM " + table + " WHERE name = '" + name + "'";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java result set
			ResultSet rs = st.executeQuery(query);

			// iterate through the java result set
			while (rs.next()) {
				id = rs.getInt("id");
				objName = rs.getString("Name");
				surmmary = rs.getString("Summary");
				lastUpdated = rs.getLong("LastUpdated");
			}
			st.close();
		} catch (Exception e) {
			System.err.println("Error establishing connection! ");
			System.err.println(e.getMessage());
		}
	}

	/** Update the specified table with the specified information
	 * @param table the table to update
	 * @param contents the value of the key being updated/added
	 * @param name the key to update/add within the table
	 */
	public void updateTable(String table, String contents, String name) {
		try {
			// create a java mysql database connection
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://csmysql.cs.cf.ac.uk/group9_2016";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "group9.2016", "g4QFghm5Kxm");

			if (lastUpdated == null) {
				String query = " insert into " + table + " (Name, Summary, LastUpdated)" + " values (?, ?, ?)";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				Date lastUpdateTime = new Date();

				preparedStmt.setString(1, name);
				preparedStmt.setString(2, contents);
				preparedStmt.setLong(3, lastUpdateTime.getTime());

				// execute the java preparedstatement
				preparedStmt.executeUpdate();
			} else {
				String query = "UPDATE " + table + " SET Name = ?, Summary = ?, lastUpdated = ? WHERE id = ?";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				Date lastUpdateTime = new Date();

				preparedStmt.setString(1, name);
				preparedStmt.setString(2, contents);
				preparedStmt.setLong(3, lastUpdateTime.getTime());
				preparedStmt.setInt(4, id);

				// execute the java preparedstatement
				preparedStmt.executeUpdate();
			}

			conn.close();
		} catch (Exception e) {
			System.err.println("Got an update exception! ");
			System.err.println(e.getMessage());
		}
	}

	public Boolean needUpdating() {
		return update;
	}

	public String getSummary() {
		return surmmary;
	}
}
