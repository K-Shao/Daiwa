package ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;

public class Operator {
	
	private String name;
	private long bonxID;
	private boolean hidden = false;
	
	private String currentLot = "";
	private String currentTime = "";
	private Report currentReport;
	private Entry currentEntry;
	
	private List<Report> reports = new ArrayList<Report>();
	private List<String> reportDates = new ArrayList<String>();
	
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

	public void addReport (String date) throws SQLException{
		reportDates.add(date);
	}
	
	public List<Report> getReports () {
		return reports;
	}
	
	public void loadReports () throws SQLException {
		for (String date: reportDates) {
			List<Entry> entries = DBConn.getEntries(name, date);
			reports.add(new Report(date, entries));
		}
	}
	
	/**
	 * Sets current lot, returning true if the lot is valid. 
	 * @param lot
	 * @return
	 */
	public boolean setCurrentLot (String lot) {
		for (Report r: reports) {
			for (Entry e: r.getEntries()) {
				if (Integer.toString(e.lot).equals(lot)) {
					this.currentEntry = e;
					this.currentLot = lot;
					this.currentReport = r;
					this.currentTime = e.time;
					return true;
				}
			}
		}
		return false;
	}
	
	public String getCurrentLot () {
		return this.currentLot;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public void update(String key, String val) {
		this.currentEntry.set(key, val);
	}
	
	public Entry getCurrentEntry () {
		return currentEntry;
	}
	
	
}
