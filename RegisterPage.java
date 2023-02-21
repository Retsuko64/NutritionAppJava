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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPage extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7062208216255140280L;
	private JLabel instructions, userNameLabel, passwordLabel;
	private JTextField userName;
	private JPasswordField passwordField;
	private JButton registerButton;
	private JMenuBar menuBar;
	private JMenu help;
	private JMenuItem instructionDialog;
	
	public RegisterPage() {
		itemsAdded();
		frameProperties();
	}
	
	private void frameProperties() {
		   setSize(300, 250);
		   setTitle("Register");
		   setDefaultCloseOperation(EXIT_ON_CLOSE);
		   setResizable(false);
		   setLayout(null);
		   getContentPane().setBackground(Color.PINK);
		   setVisible(true);
	   }
	
	private void itemsAdded(){
		
		instructions = new JLabel("Input a username and password");
		instructions.setBounds(50, 5, 200 ,50);
		instructions.setFont(new Font(null, Font.PLAIN, 13));
		add(instructions);
		
		userNameLabel = new JLabel("User Name: ");
		userNameLabel.setBounds(20, 50,70,20);
		userNameLabel.setFont(new Font("Impact", Font.PLAIN, 13));
		add(userNameLabel);
		
		userName = new JTextField();
		userName.setBounds(150, 45, 120 ,30);
		add(userName);
		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(30, 100,65,20);
		passwordLabel.setFont(new Font("Impact", Font.PLAIN, 13));
		add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(150, 95, 120 ,30);
		add(passwordField);
		
		registerButton = new JButton("Register");
		registerButton.setBounds(100, 160, 90 , 30);
		registerButton.addActionListener(this);
		registerButton.setFont(new Font(null, Font.PLAIN, 13));
		registerButton.setFocusable(false);
		add(registerButton);
		
		menuBar = new JMenuBar();
		help = new JMenu("Help");
		instructionDialog = new JMenuItem("Instruction");
		menuBar.setBounds(0,0,300,18);
		menuBar.add(help);
		help.add(instructionDialog);
		add(menuBar);
		
		instructionDialog.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == instructionDialog) {
			JOptionPane.showMessageDialog(this, "Usernames should min: 6 max:16 \nPasswords should min: 6 max: 20");
		}
		else {
		int isNutritionist = JOptionPane.showConfirmDialog(this, "Are you a nutrtionist?", "" , 
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(e.getSource() == registerButton && isNutritionist == JOptionPane.NO_OPTION)
		registerInfo();
		else 
			registerNutritionist();
		}
	}
	/**
	 * this method will register the written info to the UserAndPassword class, on its
	 * HashMap after checking if its a valid info
	 * 
	 */
	private void registerInfo() {
		String name = userName.getText().trim();
		String password = String.valueOf(passwordField.getPassword()).trim();
		// checking if user name and password are valid
		
		if(isUserValid(name, password)) {
		UserAndPasswords.registerUser(name, password);
		JOptionPane.showMessageDialog(this, "Welcome!");
		dispose();
		new PersonalInfo(name); // we will now show a new frame to ask the user for some extra information
		}
	}
	/**
	 * this method will register the written info to the UserAndPassword class, on its
	 * HashMap after checking if its a valid info
	 * 
	 */
	
	private void registerNutritionist() {
		String name = userName.getText().trim();
		String password = String.valueOf(passwordField.getPassword()).trim();
		// checking if user name and password are valid
		
		if(isUserValid(name,password)) {
		UserAndPasswords.registerNutritionist(name, password);
		JOptionPane.showMessageDialog(this, "Welcome!");
	String fullName = JOptionPane.showInputDialog(this, "What is your full name?");
	PersonInfo.saveFullName(name, fullName);
		dispose();
		new LoginPage();
		}
		}
	
	/**
	 * this method checks the validity of user name and password input during the registration
	 * @param name
	 * @param password
	 * @return boolean
	 */
	
	private boolean isUserValid(String name, String password) {
		if (UserAndPasswords.getUserInfo().containsKey(name) || UserAndPasswords.getNutritionistInfo().containsKey(name)) {
			JOptionPane.showMessageDialog(this, "User name already exist", "Invalid Input" , JOptionPane.ERROR_MESSAGE);
			return false;}
		else if (name.length()>16 || name.length()<5 ) {
	         JOptionPane.showMessageDialog(this, "Large username, maximum size allowed is 16, minimum is 6" , 
	        		 "Invalid Input" , JOptionPane.ERROR_MESSAGE);
		return false;}
		else if (password.length()>20 || password.length()<6) {
			JOptionPane.showMessageDialog(this, "Weak or Large password, Max=20 Min=8" , 
					"Invalid Input" , JOptionPane.ERROR_MESSAGE);
		return false;}
		
		return true;	
	}
	
	
	public static void main(String[] args) {
		new RegisterPage();
	}

}
