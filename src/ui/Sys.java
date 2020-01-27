package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ConfigurationException;

import db.DBConn;
import io.BonxHeader;
import production.Configuration;

public class Sys {
	
	
	private List<Operator> operators = new ArrayList<Operator>();
	private Configuration config;
	public Configuration getConfiguration () {
		return this.config;
	}

	/**
	 * 
	 * @param operatorName
	 * @param bonxID
	 * @return True if the add operation was accepted, false otherwise. 
	 */
	public boolean addOperator(String operatorName, String bonxID) {
		long id;
		try {
			id = Long.parseLong(bonxID);
		} catch (Exception e) {
			return false;
		}
		return addOperator (operatorName, id);
	}
	
	public boolean addOperator (String name, long id) {
		if (!checkOperatorOK(name, id)) {
			return false;
		}
		operators.add(new Operator(name, id));
		return true;
	}
	
	public boolean checkOperatorOK (String newName, long id) {
		//For now, all I'm going to check is that the name is not duplicated.
		for (Operator o: operators) {
			if (o.getName().equals(newName)) {
				return false;
			}
		}
		return true;
	}
	
	public String[] getOperatorNames () {
		String [] names = new String [operators.size()];
		for (int i = 0; i < operators.size(); i++) {
			names[i] = operators.get(i).getName();
		}
		return names;
	}

	public Operator getOperatorByName(String operator) {
		for (Operator o: operators) {
			if (o.getName().equals(operator)) {
				return o;
			}
		}
		return null;
	}
	
	private Operator getOperatorById(int id) {
		for (Operator o: operators) {
			if (o.getBonxID() == id) {
				return o;
			}
		}
		return null;
	}
	
	public void addReport (Operator operator, String date) throws SQLException{
		operator.addReport(date);
	}
	
	public Object[][] getReportsFor(String operator) {
		List<Report> reports = getOperatorByName(operator).getReports();
		
		Object [][] result = new Object[reports.size()][];
		
		for (int i = 0; i < reports.size(); i++) {
			Report current = reports.get(i);
			result[i] = new Object[3];
			result[i][0] = current.getDate();
			result[i][1] = current.getNumEntries();
		}
		return result;
	}
	
	
	public void set(String key, String val, BonxHeader header) {
		Operator operator = getOperatorById(header.getId());
		//Maybe something like key = Parser.reduce(key) ???
		if (operator == null) {
			System.err.println("Operator was null in set operation.");
			return;
		}
		try {
			if (key.equals("lot")) {
				if (operator.setCurrentLot(val)) {
					System.out.println("Successful lot set to " + val);
				} else {
					System.out.println("Unsuccessful lot set: " + val);
				}
				
			} else {
				if (operator.getCurrentEntry() != null) {
					if (Entry.COLUMNS.contains(key)) {
						DBConn.update(operator, key, val);
						operator.update(key, val);
					}
				}
			}


		} catch (SQLException e) {
			System.err.println("SQLException when writing");
			e.printStackTrace();
		}

	}


	public Sys () {
		try {
			this.config = new Configuration ();
		} catch (ConfigurationException e1) {
			System.err.println("Couldn't load config.txt file!");
		}
		
		try {
			ResultSet operatorsRS = DBConn.getOperators();
			while (operatorsRS.next()) {
				String name = operatorsRS.getString("NAME");
				long id = operatorsRS.getLong("BONX");
				this.addOperator(name, id);
			}
			operatorsRS.close();
			
		} catch (SQLException e) {
			System.err.println("Couldn't load operators from database!");
		}
		
		for (Operator operator: operators) {
			try {
				ResultSet reportsRS = DBConn.getReports(operator.getName());
				while (reportsRS.next()) {
					String date = reportsRS.getString("DATE");
					this.addReport(operator, date);
				}
				operator.loadReports();
			} catch (SQLException e) {
				System.err.println("Couldn't load reports for " + operator.getName());
			}
		}

	}

	/////////////////////
	//Singleton methods
	/////////////////////
	private static Sys singleton = null;
	public static Sys getInstance () {
		if (singleton == null) {
			singleton = new Sys();
		}
		return singleton;
	}
}
