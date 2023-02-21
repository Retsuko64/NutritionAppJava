import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserHomePage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2439664997841062738L;
	private JLabel welcome;
	private JButton chat, diet, results, waterIntake, foodIntake, logout , BMICal;
	private JPanel panel;
	private static String username;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(15, 80, 560, 300);
	}
	
	/*
	 * in the following constructor we will set up a frame and inside a frame a panel which will contain all most of buttons.
	 */
  public UserHomePage(String user, double weight, double height, String fullName) {
    username = user;
	panel = new JPanel();
	setLayout(null);
	itemsAdded(username, fullName);
	setTitle("User");
	setResizable(false);
	setSize(600,400);
	getContentPane().setBackground(Color.CYAN);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
}
  
  /**
   * we want to show that our program works by showing that the information put is saved.
   * @param user
   * @param age
   * @param weight
   * @param height
   * @param fullName
   */
  
  private void itemsAdded(String user, String fullName) {
	  panel.setLayout(null);
	
	  welcome = new JLabel();
	  welcome.setText("Welcome: " + fullName);
	  welcome.setFont(new Font("Impact" , Font.BOLD, 25));
	  welcome.setBounds(5,10, 400 , 20);
	  add(welcome);
	  
	  diet = new JButton("My Diet");
	  diet.setBounds(80,60, 110 ,60);
	  diet.setFont(new Font("Impact", Font.PLAIN, 20));
	  diet.setFocusable(false);
	  diet.addActionListener(this);
	  panel.add(diet);
	  
	  waterIntake = new JButton("Water Intake");
	  waterIntake.setBounds(380, 60, 140, 60);
	  waterIntake.setFont(new Font("Impact", Font.PLAIN, 18));
	  waterIntake.setFocusable(false);
	  waterIntake.addActionListener(this);
	  panel.add(waterIntake);
	  
	  foodIntake = new JButton("Food Intake");
	  foodIntake.setBounds(380, 180, 140 , 60);
	  foodIntake.setFont(new Font("Impact", Font.PLAIN, 18));
	  foodIntake.setFocusable(false);
	  foodIntake.addActionListener(this);
	  panel.add(foodIntake);
	  
	  chat = new JButton("Chat");
	  chat.setFont(new Font("Impact", Font.PLAIN, 20));
	  chat.setBounds(80, 180, 110 ,60);
	  chat.setFocusable(false);
	  chat.addActionListener(this);
	  panel.add(chat);
	  
	  logout = new JButton("Logout");
	  logout.setFont(new Font("Impact", Font.PLAIN, 20));
	  logout.setBounds(430,260, 110 , 30);
	  logout.setFocusable(false);
	  logout.addActionListener(this);
	  panel.add(logout);
	  
	  results = new JButton("Results");
	  results.setFont(new Font("Impact", Font.PLAIN, 20));
	  results.setBounds(450, 7, 100, 30);
	  results.setFocusable(false);
	  results.addActionListener(this);
	  add(results);
	  
	  BMICal = new JButton("BMI calculator");
	  BMICal.setFont(new Font("Impact", Font.PLAIN, 20));
	  BMICal.setFocusable(false);
	  BMICal.addActionListener(this);
	  BMICal.setBounds(10 , 260 , 155 , 30);
	  panel.add(BMICal);
	  
	  add(panel);
	  panel.setBounds(10,50,560,300); 
  }
  
  /*   TESTING
  public static void main(String[] args) {
	new UserHomePage("Rets", 10, 50.20, 170, "Hassan");
} */

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getSource() == logout) {
		dispose();
		new LoginPage();
	}
	else if (e.getSource() == results) {
		new ResultsPage(username);
	}
	else if (e.getSource() == chat) {
		new UserChatPage(username);
	}
	else if (e.getSource() == waterIntake) {
		setWaterIntake();
	}
	else if (e.getSource() == BMICal) {
		new BMICalculator();
	}
	else if (e.getSource() == foodIntake || e.getSource() == diet) { // they are similar page but its for prototype
		new Food(username);
	}
}

/**
 * method in where the user will state how much he drank in a day, then we will use such value to make some sort of graph
 * to represent how much he drank as a function of time (per month)
 * 
 * the recommended goal here is for the user to get at least 2 liters of water.
 */

private void setWaterIntake() {
	do {
	try {
	String input = JOptionPane.showInputDialog(this, "How much water you drank today?" , JOptionPane.QUESTION_MESSAGE);
	if (input == null) { break; }  // in case user presses on cancel
	// in case left empty after OK
	else if (input.equals("")) { JOptionPane.showMessageDialog(this, "Fill to continue"); continue;} 
	// in case the value is valid check if its less then the average otherwise save and continue.
	else {
		double liters = Double.parseDouble(input);
		if (liters < 4) {int toContinue = JOptionPane.showConfirmDialog(this, "you sure you want to save this result?" , 
				"Confirm", JOptionPane.YES_NO_OPTION);
		if(toContinue == JOptionPane.YES_OPTION) {
			sendWaterResult(liters);
			break;
		}
		}
		else 
			break;
	}
	}
	// in case spaces or letters in the input
	catch(Exception e) {
		JOptionPane.showMessageDialog(this, "Invalid input (remove spaces or letters)");
	}
	}while(true);	
}

public void sendWaterResult(double amount) {
	try {
	Socket client = new Socket("127.0.0.1" , 6070);
	DataOutputStream dis = new DataOutputStream(client.getOutputStream());
	dis.writeDouble(amount);
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
}



}
