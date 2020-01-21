package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class HomeScreen extends JFrame {
	
	private String operator;
	
	public HomeScreen () {
		super();
		//First, get all required data. 
		final String [] operators = new String[] {"Operator 1", "Operator 2", "Operator 3"};
		this.operator = operators[0];
		final String [] reportsHeaders = new String[] {"Date", "Entries", "Time"};
		final Object [][] reportsInfo = new Object [][] {
			{"1/20/2020", 7, "8:03-11:27"}, 
			{"1/21/2020", 4, "13:20-14:00"},
			{"1/22/2020", 10, "5:23-9:31"}
		};		
		
		//Now, create all the elements of our UI. 
		final JLabel titleLabel = new JLabel("Cutoff Reports", JLabel.CENTER);
		
		//Here are the elements for the operator panel. 
		final JLabel operatorsLabel = new JLabel ("Operators: " + operators.length, JLabel.CENTER);
		
		final JList<String> operatorsList = new JList<String>(operators);
		operatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		operatorsList.setLayoutOrientation(JList.VERTICAL);
		operatorsList.setVisibleRowCount(10);
		operatorsList.setSelectedIndex(0);
		
		final JButton addOperator = new JButton ("Add operator...");
		
		final JScrollPane operatorsListScroller = new JScrollPane(operatorsList);
		operatorsListScroller.setPreferredSize(new Dimension(250, 80));
		
		//Here are all the elements for the report panel. 
		final JLabel reportsTitle = new JLabel ("Daily Reports: " + operatorsList.getSelectedValue(), JLabel.CENTER);
		
		final JTable reportsTable = new JTable(reportsInfo, reportsHeaders);
		DefaultTableModel uneditableTableModel = new DefaultTableModel(reportsInfo, reportsHeaders) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		reportsTable.setModel(uneditableTableModel);

		
		final JScrollPane reportsScrollPane = new JScrollPane(reportsTable);
		reportsTable.setFillsViewportHeight(true);
		
		//Now, add listeners to any elements that need it. 
		operatorsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged (ListSelectionEvent e) {
				String operator = operatorsList.getSelectedValue();
				reportsTitle.setText("Daily Reports: " + operator);
				HomeScreen.this.operator = operator;
			}
		});
		
		reportsTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		            new ReportViewer(HomeScreen.this.operator, row);
		        }
		    }
		});
		
		
		//////////////////////////////// Next, add all the elements to their proper panels. 
		////////////////////////////////
		////////////////////////////////
		
		JPanel operatorsPanel = new JPanel();
		operatorsPanel.setLayout(new BorderLayout());
		operatorsPanel.add(operatorsLabel, BorderLayout.PAGE_START);
		operatorsPanel.add(operatorsListScroller, BorderLayout.CENTER);
		operatorsPanel.add(addOperator, BorderLayout.PAGE_END);
		operatorsPanel.setBackground(Color.RED);
		
		JPanel reportsList = new JPanel();
		reportsList.setLayout(new BorderLayout());
		reportsList.add(reportsTitle, BorderLayout.PAGE_START);
		reportsList.add(reportsScrollPane, BorderLayout.CENTER);
		reportsList.setBackground(Color.GREEN);

		//Lastly, add the panels to the frame. 
		this.setLayout(new BorderLayout());
//		this.add(titleLabel, BorderLayout.PAGE_START);
		this.add(operatorsPanel, BorderLayout.LINE_START);
		this.add(reportsList, BorderLayout.CENTER);

		//And configure some settings
		this.pack();
		this.setTitle("Cutoff Reports");
		this.setLocation(50,40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	
	
}
