package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	
	private static final String PATH = "jdbc:sqlite:db/cutoff.db";
	
	private static Connection connection;
	private static Statement statement;
	
	public static void init () throws SQLException {
		connection = DriverManager.getConnection(PATH);
		statement = connection.createStatement();
		
		String query = "CREATE TABLE IF NOT EXISTS OPERATORS ("
				+ "NAME TEXT PRIMARY KEY NOT NULL, "
				+ "BONX INT NOT NULL);";
		statement.execute(query);
		
		query = "CREATE TABLE IF NOT EXISTS REPORTS ("
				+ "OPERATOR TEXT NOT NULL, "
				+ "LOT INT, "
				+ "USE TEXT, "
				+ "SIZE1 TEXT, SIZE2 TEXT, SIZE3 TEXT, "
				+ "D1 TEXT, D2 TEXT, D3 TEXT, D4 TEXT, "
				+ "T1 TEXT, T2 TEXT, T3 TEXT, "
				+ "LENGTH TEXT, "
				+ "BEAD INT, "
				+ "FLAT INT, "
				+ "FLAT_SIZE TEXT, "
				+ "BEND INT, "
				+ "APPEARANCE INT, "
				+ "TIME TEXT);";
		statement.execute(query);
	}
	
	public static ResultSet getOperators() throws SQLException {
		ResultSet rs = statement.executeQuery("SELECT * FROM OPERATORS");
		return rs;
	}

	public static void addOperator(String name, long bonxID) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO OPERATORS VALUES (?,?)");
		ps.setString(1, name);
		ps.setLong(2, bonxID);
		ps.executeUpdate();
	}
	
	

}
