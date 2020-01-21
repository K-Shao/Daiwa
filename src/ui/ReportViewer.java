package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReportViewer extends JFrame {
	
	public ReportViewer (String operator, int reportNumber) {
		super();
		
		final String [] headers = new String [] {"ロット No.", "サイズ", "D1", "D2", "D3"};
		final Object [][] reportData = new Object[][] {
			{5274309, "48.6 x 1.2 x 4000", 48.43, 48.35, 48.57},
			{5274310, "48.6 x 1.2 x 4000", 48.40, 48.45, 48.51}
		};
		
		
		JLabel titleLabel = new JLabel("Report by: " + operator + " on 1/23/2020", JLabel.CENTER);
		
		JTable reportTable = new JTable (reportData, headers);
		final JScrollPane reportScrollPane = new JScrollPane(reportTable);
		reportTable.setFillsViewportHeight(true);

		/////////////
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.PAGE_START);
		this.add(reportScrollPane, BorderLayout.CENTER);
		
		this.pack();
		this.setTitle("Report for " + operator);
		this.setLocation(60,60);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
