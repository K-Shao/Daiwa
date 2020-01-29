package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ui.Entry;
import ui.Operator;

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
		
		query = "CREATE TABLE IF NOT EXISTS ENTRIES ("
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
				+ "TIME TEXT, "
				+ "DATE TEXT);";
		statement.execute(query);
		
		query = "CREATE TABLE IF NOT EXISTS REPORTS ("
				+ "OPERATOR TEXT NOT NULL, DATE TEXT NOT NULL);";
		statement.execute(query);
		
		query = "CREATE TABLE IF NOT EXISTS TRANSLATIONS ("
				+ "SOURCE TEXT PRIMARY KEY NOT NULL,"
				+ "DEST TEXT NOT NULL);";
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
	
	public static ResultSet getReports(String name) throws SQLException {
		String query = "SELECT DISTINCT DATE FROM ENTRIES WHERE OPERATOR = '" + name + "';";
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}

	public static List<Entry> getEntries(String name, String date) throws SQLException {
		String query = "SELECT * FROM ENTRIES WHERE OPERATOR = '" + name + "' and DATE = '" + date + "';";
		ResultSet rs = statement.executeQuery(query);
		List<Entry> result = new ArrayList<Entry>();
		
		while (rs.next()) {
			Entry entry = new Entry (date, true);
			
			entry.lot = rs.getInt("LOT");
			entry.appearance = rs.getInt("APPEARANCE");
			entry.bead = rs.getInt("BEAD");
			entry.bend = rs.getInt("BEND");
			entry.flat = rs.getInt("FLAT");
			entry.d1 = rs.getString("D1");
			entry.d2 = rs.getString("D2");
			entry.d3 = rs.getString("D3");
			entry.d4 = rs.getString("D4");
			entry.t1 = rs.getString("T1");
			entry.t2 = rs.getString("T2");
			entry.t3 = rs.getString("T3");
			entry.length = rs.getString("LENGTH");
			entry.size1 = rs.getString("SIZE1");
			entry.size2 = rs.getString("SIZE2");
			entry.size3 = rs.getString("SIZE3");
			entry.use = rs.getString("USE");
			entry.flatSize = rs.getString("FLAT_SIZE");
			entry.time = rs.getString("TIME");
			
			result.add(entry);
		}
		
		return result;
	}

	public static void deleteOperator(String name, long bonxID) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("DELETE FROM OPERATORS WHERE NAME = ? AND BONX = ?;");
		ps.setString(1, name);
		ps.setLong(2, bonxID);
		ps.executeUpdate();
	}

	public static void update(Operator operator, String key, String val) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE ENTRIES SET "+key+" = ? WHERE OPERATOR = ? AND LOT = ?;");
		ps.setString(1, val);
		ps.setString(2, operator.getName());
		ps.setLong(3, Long.parseLong(operator.getCurrentLot()));
		ps.executeUpdate();
	}

	public static void removeEntry(String operatorName, long lot) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM ENTRIES WHERE OPERATOR = ? AND LOT = ?");
		ps.setString(1, operatorName);
		ps.setLong(2, lot);
		ps.executeUpdate();
		
		
	}

	public static void addEntry(String name, long number, String currentDate, String currentTime) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO ENTRIES (OPERATOR, LOT, DATE, TIME) VALUES (?,?,?,?);");
		ps.setString(1, name);
		ps.setLong(2, number);
		ps.setString(3, currentDate);
		ps.setString(4, currentTime);
		ps.executeUpdate();
	}
	
	

}
