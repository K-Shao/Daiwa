package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class ReportViewer extends JFrame {
	
	public ReportViewer (Operator operator, int reportNumber) {
		super();
		
		final Report report = operator.getReport(reportNumber);
		
		ReportTableModel rtm = new ReportTableModel(report);
	
		JLabel titleLabel = new JLabel("Report by: " + operator.getName() + " on: " + report.getDate(), JLabel.CENTER);
		JButton exportButton = new JButton ("Export report to PNG");
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				try {
					ImageGenerator.generateImage(report);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(ReportViewer.this, "Couldn't generate report image!");
					ex.printStackTrace();
				}
			}
		});
		
		JTable reportTable = new JTable (rtm);
		final JScrollPane reportScrollPane = new JScrollPane(reportTable);
		reportTable.setFillsViewportHeight(true);
		
		JTextArea notes = new JTextArea ("Notes: ");
		notes.setRows(10);

		/////////////
		this.setLayout(new BorderLayout());
		JPanel topPane = new JPanel();
		topPane.add(titleLabel);
		topPane.add(exportButton);
		this.add(topPane, BorderLayout.PAGE_START);
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
