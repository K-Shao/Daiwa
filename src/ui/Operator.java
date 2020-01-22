package ui;

import java.util.ArrayList;
import java.util.List;

public class Operator {
	
	private String name;
	private long bonxID;
	private boolean hidden = false;
	
	private List<Report> reports = new ArrayList<Report>();
	
	public Operator (String name, long bonxID) {
		this.name = name;
		this.bonxID = bonxID;
	}
	
	public String getName () {
		return name;
	}
	
	public long getBonxID () {
		return bonxID;
	}
	
	public Report getReport (int index) {
		return reports.get(index);
	}

	public void addReport () {
		reports.add(new Report());
	}
}
