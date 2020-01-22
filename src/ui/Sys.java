package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;

public class Sys {
	
	
	private List<Operator> operators = new ArrayList<Operator>();

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
	
	
	
	
	
	
	
	
	public Sys () {
		try {
			ResultSet operatorsRS = DBConn.getOperators();
			while (operatorsRS.next()) {
				String name = operatorsRS.getString("NAME");
				long id = operatorsRS.getLong("BONX");
				this.addOperator(name, id);
			}
		} catch (SQLException e) {
			System.err.println("Couldn't load operators from database!");
		}

	
		
		operators.get(0).addReport();
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
