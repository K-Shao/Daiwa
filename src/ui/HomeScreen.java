package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.DBConn;

public class HomeScreen extends JFrame {
	
	private String operator;
	private List<ReportViewer> reportViewers = new ArrayList<ReportViewer>();
	
	public HomeScreen () {
		super();
		this.refreshAll();
	}
	
	public void repaintAll() {
		this.repaint();
		for (ReportViewer rv: reportViewers) {
			rv.repaint();
		}
	}
	
	@SuppressWarnings("serial")
	public void refreshAll () {
		/////////////////////////////////
		//First, get all required data. 
		/////////////////////////////////
		final String [] operators = Sys.getInstance().getOperatorNames();
		final DefaultListModel<String> operatorsListModel = new DefaultListModel<String>();
		for (String s: operators) {
			operatorsListModel.addElement(s);
		}
		this.operator = operators[0];
		final ReportsTableModel reportsTableModel = new ReportsTableModel(this.operator);
		
		////////////////////////////////////////////
		//Now, create all the elements of our UI. 
		////////////////////////////////////////////
		final JLabel titleLabel = new JLabel("Cutoff Reports", JLabel.CENTER);
		
		///////////////////////////////////////////////
		//Here are the elements for the operator panel. 
		///////////////////////////////////////////////
		final JLabel operatorsLabel = new JLabel ("Operators: " + operators.length, JLabel.CENTER);
		
		final JList<String> operatorsList = new JList<String>(operatorsListModel);
		operatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		operatorsList.setLayoutOrientation(JList.VERTICAL);
		operatorsList.setVisibleRowCount(10);
		operatorsList.setSelectedIndex(0);
		
		final JButton addOperator = new JButton ("Add operator..."); 
		final JButton deleteOperator = new JButton ("Delete operator...");
		final AddOperatorPanel addOperatorPanel = new AddOperatorPanel();
		
		final JScrollPane operatorsListScroller = new JScrollPane(operatorsList);
		operatorsListScroller.setPreferredSize(new Dimension(250, 80));
		
		///////////////////////////////////////////////////
		//Here are all the elements for the report panel. 
		///////////////////////////////////////////////////
		final JLabel reportsTitle = new JLabel ("Daily Reports: " + operatorsList.getSelectedValue(), JLabel.CENTER);
		final JTable reportsTable = new JTable(reportsTableModel);

		final JScrollPane reportsScrollPane = new JScrollPane(reportsTable);
		reportsTable.setFillsViewportHeight(true);
		
		/////////////////////////////////////////////////
		//Now, add listeners to any elements that need it. 
		/////////////////////////////////////////////////
		operatorsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged (ListSelectionEvent e) {
				String operator = operatorsList.getSelectedValue();
				reportsTitle.setText("Daily Reports: " + operator);
				((ReportsTableModel) reportsTable.getModel()).setData(operator);
				reportsTable.repaint();
				HomeScreen.this.operator = operator;
			}
		});
		
		reportsTable.addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	Operator operator = Sys.getInstance().getOperatorByName(HomeScreen.this.operator);
		        	if (operator == null) {
		        		System.err.println("Attempting to show report,  but operator is null!");
		        	} else {
			        	reportViewers.add(new ReportViewer(operator, row));
		        	}
		        }
		    }
		});
		
		addOperator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(
					HomeScreen.this, addOperatorPanel, "Add an operator",JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String name = addOperatorPanel.getOperatorName();
					boolean addOperatorOK = Sys.getInstance().addOperator(name, addOperatorPanel.getBonxID());
					if (!addOperatorOK) {
						JOptionPane.showMessageDialog(HomeScreen.this, "Something went wrong adding this operator!");
					}
					operatorsListModel.addElement(name);
					try {
						DBConn.addOperator(name, Long.parseLong(addOperatorPanel.getBonxID()));
					} catch (SQLException ex) {
						System.err.println("Couldn't add operator to database!");
					}
				}
				operatorsLabel.setText("Operators: " + operatorsListModel.size());
			}
		});
		
		deleteOperator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteOperatorPanel1 dop1 = new DeleteOperatorPanel1();
				int result = JOptionPane.showConfirmDialog(HomeScreen.this, dop1, "Delete an operator", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String name = dop1.getOperatorName();
					Operator op = null;
					if ((op = Sys.getInstance().getOperatorByName(name)) != null) {
						if (op.getReports().size() == 0) { //Just delete if there are no reports. 
							try {
								DBConn.deleteOperator(name, op.getBonxID());
								operatorsListModel.removeElement(name);
								JOptionPane.showMessageDialog(HomeScreen.this, "Deletion successful!");
							} catch (SQLException ex) {
								JOptionPane.showMessageDialog(HomeScreen.this, "Deletion failed - unknown reason.");
							}
						} else { //Require BONX ID if there are reports. 
							DeleteOperatorPanel2 dop2 = new DeleteOperatorPanel2(name);
							int result2 = JOptionPane.showConfirmDialog(HomeScreen.this, dop2, "Enter BONX ID", JOptionPane.OK_CANCEL_OPTION);
							if (result2 == JOptionPane.OK_OPTION && op.getBonxID() == dop2.getID()) {
								try {
									DBConn.deleteOperator(name, op.getBonxID());
									operatorsListModel.removeElement(name);
								} catch (SQLException ex) {
									JOptionPane.showMessageDialog(HomeScreen.this, "Deletion failed - unknown reason.");
								}
							} else {
								JOptionPane.showMessageDialog(HomeScreen.this, "Deletion refused - no BONX ID");
							}
						}
					} else {
						JOptionPane.showMessageDialog(HomeScreen.this, "No operator by that name!");
					}
				}
			}
		});
		
		////////////////////////////////////////////////////
		//Next, add all the elements to their proper panels. 
		////////////////////////////////////////////////////
		
		JPanel operatorsPanel = new JPanel();
		operatorsPanel.setLayout(new BorderLayout());
		operatorsPanel.add(operatorsLabel, BorderLayout.PAGE_START);
		operatorsPanel.add(operatorsListScroller, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(addOperator);
		buttonsPanel.add(deleteOperator);
		operatorsPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		operatorsPanel.setBackground(Color.RED);
		
		JPanel reportsList = new JPanel();
		reportsList.setLayout(new BorderLayout());
		reportsList.add(reportsTitle, BorderLayout.PAGE_START);
		reportsList.add(reportsScrollPane, BorderLayout.CENTER);
		reportsList.setBackground(Color.GREEN);

		///////////////////////////////////////
		//Lastly, add the panels to the frame. 
		///////////////////////////////////////
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

	private class DeleteOperatorPanel1 extends JPanel{
		
		private JTextField nameInput;
		
		public DeleteOperatorPanel1() {
			super();

			JLabel titleLabel = new JLabel ("Delete Operator");
			JLabel nameLabel = new JLabel ("Name: ");
			
			nameInput = new JTextField (10);
			
			this.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx=0; c.gridy=0;
			c.gridwidth = 2;
			this.add(titleLabel,c);
			c.gridy++;
			c.gridwidth = 1;
			this.add(nameLabel,c);
			c.gridx++;
			this.add(nameInput,c);
		}
		
		public String getOperatorName() {
			return nameInput.getText();
		}
		
	}
	
	private class DeleteOperatorPanel2 extends JPanel{
		
		private JTextField idInput;
		private String name;
		
		public DeleteOperatorPanel2(String name) {
			super();

			this.name = name;
			JLabel titleLabel = new JLabel ("The operator you're trying to delete has reports. Please enter BONX ID to confirm deletion. ");
			JLabel idLabel = new JLabel (name + "'s ID: ");
			
			idInput = new JTextField (10);
			
			this.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx=0; c.gridy=0;
			c.gridwidth = 2;
			this.add(titleLabel,c);
			c.gridy++;
			c.gridwidth = 1;
			this.add(idLabel,c);
			c.gridx++;
			this.add(idInput,c);
		}
		
		public long getID() {
			try {
				return Long.parseLong(idInput.getText());
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		
	}
	
}
