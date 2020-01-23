package ui;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {
	
	private String [] headers = new String [] {"ロット No.", "用途", "サイズ", "D1", "D2", "D3", "D4", "T1", "T2", "T3",
		"長さ", "内面ビード", "扁平　(A mm)", "曲り", "外観", "時刻"};

	private Report report;
	
	public ReportTableModel(Report report) {
		this.report = report;
	}

	@Override
	public int getRowCount() {
		return report.getNumEntries();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Entry e = report.getEntries().get(rowIndex);
		switch (columnIndex) {
		case 0: return e.lot; 
		case 1: return e.use; 
		case 2: return e.size1 + " x " + e.size2 + " x " + e.size3; 
		case 3: return e.d1; 
		case 4: return e.d2; 
		case 5: return e.d3; 
		case 6: return e.d4; 
		case 7: return e.t1; 
		case 8: return e.t2; 
		case 9: return e.t3; 
		case 10: return e.length;
		case 11: return e.bead; 
		case 12: return e.flat + "(" + e.flatSize + ")"; 
		case 13: return e.bend; 
		case 14: return e.appearance; 
		case 15: return e.time; 
		}
		return null;
	}
	
	
	
    @Override
    public String getColumnName(int index) {
        return headers[index];
    }

}
