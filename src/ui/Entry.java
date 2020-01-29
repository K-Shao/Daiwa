package ui;

import java.util.HashSet;
import java.util.Set;

public class Entry {
	
	private String date;
	
	public long lot;
	public int bead, flat, bend, appearance;
	public String use, size1, size2, size3, d1, d2, d3, d4, t1, t2, t3, length, flatSize, time;
	
	private boolean isEmpty = true;
	
	public static Set<String> COLUMNS = new HashSet<String>();
	static {
		COLUMNS.add("USE");
		COLUMNS.add("SIZE1");
		COLUMNS.add("SIZE2");
		COLUMNS.add("SIZE3");
		COLUMNS.add("D1");
		COLUMNS.add("D2");
		COLUMNS.add("D3");
		COLUMNS.add("D4");
		COLUMNS.add("T1");
		COLUMNS.add("T2");
		COLUMNS.add("T3");
		COLUMNS.add("LENGTH");
		COLUMNS.add("FLAT_SIZE");
		COLUMNS.add("TIME");
		COLUMNS.add("BEAD");
		COLUMNS.add("FLAT");
		COLUMNS.add("BEND");
		COLUMNS.add("APPEARANCE");
	}
	
	public String getDate () {
		return date;
	}

	public Entry (String date, boolean isPreload) {
		this.date = date;
		isEmpty = !isPreload;
	}
	public void set(String key, String val) {
		isEmpty = false;
		if (key.equals("USE")) {
			use = val;
		}
		if (key.equals("FLAT")) {
			flat = val.equals("1")?1:0;
		}
		if (key.equals("BEAD")) {
			bead = val.equals("1")?1:0;
		}
		if (key.equals("BEND")) {
			bend = val.equals("1")?1:0;
		}
		if (key.equals("APPEARANCE")) {
			appearance = val.equals("1")?1:0;
		}
		if (key.equals("SIZE1")) {
			size1=val;
		}
		if (key.equals("D1")) {
			d1 = val;
		}
		if (key.equals("D2")) {
			d2 = val;
		}
		if (key.equals("D3")) {
			d3 = val;
		}
		if (key.equals("D4")) {
			d4 = val;
		}
		if (key.equals("T1")) {
			t1 = val;
		}
		if (key.equals("T2")) {
			t2 = val;
		}
		if (key.equals("T3")) {
			t3 = val;
		}
		if (key.equals("LENGTH")) {
			length = val;
		}
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

}
