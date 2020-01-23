package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class ReportViewer extends JFrame {
	
	public ReportViewer (Operator operator, int reportNumber) {
		super();
		
		Report report = operator.getReport(reportNumber);
		
		ReportTableModel rtm = new ReportTableModel(report);
	
//		final Object [][] reportData = new Object[][] {
//			{5274309, "Scaffolding", "48.6 x 1.2 x 4000", 48.43, 48.35, 48.57, 0, 0, 0, 0, "4040 ± 4", "OK", "OK", "OK", "OK", "13:15"},
//			{5274310, "Scaffolding", "48.6 x 1.2 x 4000", 48.43, 48.35, 48.57, 0, 0, 0, 0, "4040 ± 4", "OK", "OK", "OK", "OK", "13:15"},
//		};
		
		JLabel titleLabel = new JLabel("Report by: " + operator.getName() + " on " + report.getDate(), JLabel.CENTER);
		
		JTable reportTable = new JTable (rtm);
		final JScrollPane reportScrollPane = new JScrollPane(reportTable);
		reportTable.setFillsViewportHeight(true);
		
		JTextArea notes = new JTextArea ("Notes: ");
		notes.setRows(10);

		/////////////
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.PAGE_START);
		this.add(reportScrollPane, BorderLayout.CENTER);
		this.add(notes, BorderLayout.PAGE_END);
		
		this.pack();
		this.setTitle("Report for " + operator.getName());
		this.setSize(new Dimension(1440, 720));
		this.setLocation(60,60);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
