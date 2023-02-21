import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PersonalInfo extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1416089957063461601L;
	private String userName;
	private JTextField fullName, weight, height, age;
	private JLabel instructions,nameLabel, weightLabel, heightLabel, ageLabel;
	private JButton doneBtn;
	private JMenuBar menuBar;
	private JMenu help;
	private JMenuItem instructionDialog;
	
	/**
	 * in here the user will put some extra information for the program to work (height, weight...) we will use the userName as a key in our HashMap
	 * (Check PersonInfo class) and the information given as values.
	 * @param userName
	 */
	
	public PersonalInfo(String userName) {
		this.userName = userName;
		itemsAdded();
		frame();
	} 
	
	public static void main(String[] args) {
		new PersonalInfo("he");
	}
	
	private void frame() {
		setLayout(null);
		setTitle("Input more Information");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,350);
		getContentPane().setBackground(Color.PINK);
		setVisible(true);
	}
	
	private void itemsAdded() {
		instructions = new JLabel("Input the necessary information");
		instructions.setBounds(100, 20, 180 ,20);
		instructions.setFont(new Font(null, Font.PLAIN, 13));
		add(instructions);
		
		nameLabel = new JLabel("Input full name: ");
		nameLabel.setBounds(40,50,150,20);
		nameLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		add(nameLabel);
		
		fullName = new JTextField();
		fullName.setBounds(220,45,130,30);
		add(fullName);
		
		weightLabel = new JLabel("Weight: (Kg)");
		weightLabel.setBounds(40,100,75, 30);
		weightLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		add(weightLabel); 
		
		weight = new JTextField();
		weight.setBounds(220, 100, 70,30);
		add(weight);
		
		heightLabel = new JLabel("Height: (cm)");
		heightLabel.setBounds(40, 160 , 75 ,30);
		heightLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		add(heightLabel);
		
		height = new JTextField();
		height.setBounds(220, 160, 70, 30);
		add(height);
		
		ageLabel = new JLabel("Age: ");
		ageLabel.setBounds(40, 220 , 50, 30);
		ageLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		add(ageLabel);
		
		age = new JTextField();
		age.setBounds(220, 220 , 70 , 30);
		add(age);
		
		doneBtn = new JButton("Done");
		doneBtn.setBounds(290 , 265 , 75 , 30);
		doneBtn.setFont(new Font(null, Font.PLAIN, 14));
		doneBtn.setFocusable(false);
		add(doneBtn);
		doneBtn.addActionListener(this);
		
		menuBar = new JMenuBar();
		help = new JMenu("Help");
		instructionDialog = new JMenuItem("Instruction");
		menuBar.setBounds(0,0,400,18);
		menuBar.add(help);
		help.add(instructionDialog);
		add(menuBar);
		
		instructionDialog.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == doneBtn) {
	   if(checkInfoValidity()) {
		   String getName = fullName.getText();
		   double getWeight = Double.parseDouble((weight.getText()));
		   double getHeight = Double.parseDouble((height.getText()));
		   int getAge = Integer.parseInt(age.getText());
		   new PersonInfo(getName, userName, getAge , getWeight, getHeight);
		   dispose();
		   new LoginPage();
	   }
	   }
		else if (e.getSource() == instructionDialog) {
			JOptionPane.showMessageDialog(this, "Weight min: 30\nheight min: 50 max: 300\nage min: 10 max: 80\navoid letters "
					+ "and spaces unless its a full name");
		}
	}
	/**
	 * this method shall check if the info given are suitable to run the program, avoid letters in ages for example.
	 * 
	 * @return boolean
	 */
	
	private boolean checkInfoValidity() {
		//check name
		String getName = fullName.getText();
		if (getName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input your name");
		return false;
		}
		
	//check weight
	try {
		double getWeight = Double.parseDouble((weight.getText()));
		if (getWeight < 30) {
			JOptionPane.showMessageDialog(this, "Invalid weight");
			return false;
		}
	}
	catch (Exception a) {
		JOptionPane.showMessageDialog(this, "Invalid weight");
		return false;
	}
	
	//check height
	 try {
		 double getHeight = Double.parseDouble((height.getText()));
		 if (getHeight <50 || getHeight>300) {
				JOptionPane.showMessageDialog(this, "Invalid height");
				return false;
			}
	 }
	 catch(Exception a) {
		 JOptionPane.showMessageDialog(this, "Invalid height");
			return false;
	 }
	 
	 //check age
	 try {
		 int getAge = Integer.parseInt(age.getText());
		 if (getAge < 10 || getAge > 80) {
			 JOptionPane.showMessageDialog(this, "Invalid Age");
				return false;
		 }
	 }
	 catch(Exception a) {
		 JOptionPane.showMessageDialog(this, "Invalid age");
			return false;
	 }
	
	return true;  // if all is good then its valid
	}
	
}
