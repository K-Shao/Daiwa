package ui;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.BonxHeader;
import io.DBConn;

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
	public boolean setCurrentLot (String lot, BonxHeader header) { //New lots is untested (both new report and not and sweeping)
		long number;
		try {
			number = Long.parseLong(lot);
		} catch (NumberFormatException e) {
			return false;
		}
		
		for (Report r: reports) {
			for (Entry e: r.getEntries()) {
				if (e.lot == number) {
					this.currentEntry = e;
					this.currentLot = lot;
					this.currentReport = r;
					this.currentTime = e.time;
					return true;
				}
			}
		}
		if (number < 10000) {
			return false; //Probably not a real number, if it's four digits or less. 
		}
		
		this.currentReport = null;
		String currentDate = new SimpleDateFormat("dd-M-yyyy").format(header.getTimeMillis());
		String currentTime = new SimpleDateFormat("HH:ss").format(header.getTimeMillis());
		for (Report r: reports) {
			if (r.getDate().equals(currentDate)) {
				this.currentReport = r;
			}
		}

		if (currentReport == null) {			
			Report r = new Report(currentDate, new ArrayList<Entry>());
			this.currentReport = r;
			this.reports.add(r);
			System.out.println("Adding report");
		} else {
			for (Entry e: this.currentReport.getEntries()) { //Delete any empties. 
				if (e.isEmpty()) {
					this.currentReport.getEntries().remove(e);
					try {
						DBConn.removeEntry(name, e.lot);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
		Entry entry = new Entry (currentDate, false);
		entry.lot = number;
		entry.time = currentTime;
		currentReport.getEntries().add(entry);
		this.currentEntry = entry;
		this.currentLot = lot;
		this.currentTime = currentTime;
		
		try {
			DBConn.addEntry(name, number, currentDate, currentTime);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return true;
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
