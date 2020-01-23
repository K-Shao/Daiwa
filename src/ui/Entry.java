package ui;

public class Entry {
	
	private String date;
	
	public int lot, bead, flat, bend, appearance;
	public String use, size1, size2, size3, d1, d2, d3, d4, t1, t2, t3, length, flatSize, time;
	
	public String getDate () {
		return date;
	}
	
	
	
	
	public Entry (String date) {
		this.date = date;
	}

}
