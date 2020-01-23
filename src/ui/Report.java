package ui;

import java.util.ArrayList;
import java.util.List;

public class Report {
	
	private String date;
	private List<Entry> entries;
	
	public String getDate () {
		return date;
	}
	
	public int getNumEntries () {
		return entries.size();
	}
	
	public List<Entry> getEntries () {
		return entries;
	}
	
	
	public Report (String date, List<Entry> entries) {
		this.date = date;
		this.entries = entries;
		
	}
	

}
