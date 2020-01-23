package ui;

import javax.swing.table.AbstractTableModel;

public class ReportsTableModel extends AbstractTableModel {
	
	private String [] headers;
	private Object [][] data;
	
	public ReportsTableModel (String operator) {
		data = Sys.getInstance().getReportsFor(operator);
		headers = new String[] {"Date", "Entries"};
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

    @Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
    @Override
    public String getColumnName(int index) {
        return headers[index];
    }

	public void setData(String operator) {
		data = Sys.getInstance().getReportsFor(operator);
	}
}
