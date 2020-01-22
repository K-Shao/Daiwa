package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddOperatorPanel extends JPanel {
	
	private JTextField nameInput, idInput;
	
	public AddOperatorPanel() {
		super();

		JLabel titleLabel = new JLabel ("Add Operator");
		JLabel nameLabel = new JLabel ("Name: ");
		JLabel idLabel = new JLabel ("BONX ID: ");
		
		nameInput = new JTextField (10);
		idInput = new JTextField (10);
		
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
		c.gridy++;
		c.gridx = 0;
		this.add(idLabel, c);
		c.gridx++;
		this.add(idInput, c);
	}
	
	public String getOperatorName() {
		return nameInput.getText();
	}
	
	public String getBonxID () {
		return idInput.getText();
	}

}
