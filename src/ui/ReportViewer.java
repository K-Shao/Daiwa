package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReportViewer extends JFrame {
	
	public ReportViewer (Operator operator, int reportNumber) {
		super();
		
		Report report = operator.getReport(reportNumber);
		
		
		final String [] headers = new String [] {"ロット No.", "用途", "サイズ", "D1", "D2", "D3", "D4", "T1", "T2", "T3",
				"長さ", "内面ビード", "扁平　(A mm)", "曲り", "外観", "時刻"};
		
		final Object [][] reportData = new Object[][] {
			{5274309, "Scaffolding", "48.6 x 1.2 x 4000", 48.43, 48.35, 48.57, 0, 0, 0, 0, "4040 ± 4", "OK", "OK", "OK", "OK", "13:15"},
			{5274310, "Scaffolding", "48.6 x 1.2 x 4000", 48.43, 48.35, 48.57, 0, 0, 0, 0, "4040 ± 4", "OK", "OK", "OK", "OK", "13:15"},
		};
		
		JLabel titleLabel = new JLabel("Report by: " + operator.getName() + " on 1/23/2020", JLabel.CENTER);
		
		JTable reportTable = new JTable (reportData, headers);
		final JScrollPane reportScrollPane = new JScrollPane(reportTable);
		reportTable.setFillsViewportHeight(true);

		/////////////
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.PAGE_START);
		this.add(reportScrollPane, BorderLayout.CENTER);
		
		this.pack();
		this.setTitle("Report for " + operator.getName());
		this.setLocation(60,60);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
