import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


public class LoginPage extends JFrame implements ActionListener, Runnable {
   /**
	 * 
	 */
   private static final long serialVersionUID = -7179853257616290288L;
   private JLabel userLabel, passLabel, welcomeTitle;
   private JTextField userField;
   private JPasswordField passField;
   private JButton loginButton, registerButton;
   private JFrame loadingFrame;
   private JProgressBar progressBar; // the loading screen we'll get
   private static HashMap<String, String> userAndPassCopy;
   private static HashMap<String, String> nutritionAndPassCopy;
   private Thread tLoadUserPass , t2LoadInfo;
  
   
   @Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRoundRect(20, 70, 265, 90,15 ,15);
	}
   
  /**
    * In the following constructor, it will set up a frame to input user name and password
    * 2 labels, 2 fields , login and register button.
    * 
    */  
public LoginPage() {
	   tLoadUserPass = new Thread(this); //  a thread that'll load the users from a file
	   tLoadUserPass.start();
	   t2LoadInfo = new Thread(new PersonInfo()); // a thread that load the information from a file
	   t2LoadInfo.start();
	   
	   itemsAdded();
	   frameProperties();
	   repaint();
	   
	   startLoading();
	   userAndPassCopy = UserAndPasswords.getUserInfo();
	   nutritionAndPassCopy = UserAndPasswords.getNutritionistInfo();
}
    /**
     * This method sets up the basics for a frame using JFrame.
     */
   private void frameProperties() {
	   setSize(300, 300);
	   setTitle("Nutrition");
	   setDefaultCloseOperation(EXIT_ON_CLOSE);
	   setResizable(false);
	   setLayout(null); 
	   setVisible(true);
	   getContentPane().setBackground(Color.PINK);
   }
   
   /**
    * in this method we will set up a frame that will carry a progress bar using JProgressBar.
    */
   
   public void startLoading() {
	   loadingFrame = new JFrame("Loading");
	   loadingFrame.setSize(250,100);
	   loadingFrame.setResizable(false);
	   loadingFrame.setVisible(true);
	   loadingFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	   loadingFrame.setLayout(new FlowLayout());
	   loadingFrame.setLocationRelativeTo(this);
	   
	   progressBar = new JProgressBar();
       progressBar.setBorderPainted(true);
       progressBar.setStringPainted(true);
       progressBar.setBackground(Color.WHITE);
       progressBar.setForeground(Color.BLACK);
       progressBar.setValue(0);
       loadingFrame.add(progressBar);
       
       runProgressBar(); // start loading
   }
   
   
   /**
    * this method will add all the necessary items for the login Page.
    */
   
   private void itemsAdded(){
	   userLabel = new JLabel("User Name: ");
	   userLabel.setBounds(20, 50,80,20);
	   userLabel.setFont(new Font("Impact" , Font.PLAIN, 15));
	   add(userLabel);
	  
	   passLabel = new JLabel("Password: ");
	   passLabel.setBounds(30, 100,80,20);
	   passLabel.setFont(new Font("Impact" , Font.PLAIN, 15));
	   add(passLabel);
	   
	   welcomeTitle = new JLabel("Welcome !");
	   welcomeTitle.setBounds(90,10,100,20);
	   welcomeTitle.setFont(new Font("Impact", Font.BOLD, 20));
	   add(welcomeTitle);
	   
	   userField = new JTextField();
	   userField.setEditable(false);
	   userField.setBounds(150, 45, 120 ,30);
	   add(userField); 
	  
	   passField = new JPasswordField();
	   passField.setEditable(false);
	   passField.setBounds(150, 95, 120 ,30);
	   add(passField);
	   
	   loginButton = new JButton("Login");
	   loginButton.setBounds(50, 140, 90 ,40);
	   loginButton.setFocusable(false);
	   add(loginButton);
	   loginButton.addActionListener(this);
	   
	   registerButton = new JButton("Register");
	   registerButton.setBounds(150, 140, 90, 40);
	   registerButton.setFocusable(false); 
	   add(registerButton);
	   registerButton.addActionListener(this);
   }
   
@Override
public void actionPerformed(ActionEvent e) {
	if (e.getSource() == loginButton) {
		checkCredentials();
	}
	else if (e.getSource() == registerButton) {
		dispose();
		new RegisterPage();
	}
}

/**
 * the following method will check if the user exists within the database else
 * obviously it would send him an error leading him to register
 * 
 */
private void checkCredentials() {
	String name = userField.getText();
	String password = String.valueOf(passField.getPassword());
	// check if normal user
	if (userAndPassCopy.containsKey(name)) {
		if(userAndPassCopy.get(name).equals(password)) {
			JOptionPane.showMessageDialog(this, "Welcome " + name);
			openUser();
		}
		else {
			JOptionPane.showMessageDialog(this, "Incorrect Password" , "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	//check if nutritionist
	else if(nutritionAndPassCopy.containsKey(name)) {
		if(nutritionAndPassCopy.get(name).equals(password)) {
			JOptionPane.showMessageDialog(this, "Welcome " + name);
			openNutritionist(name);
		}
		else {
			JOptionPane.showMessageDialog(this, "Incorrect Password" , "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//if both cases fail
	else 
		JOptionPane.showMessageDialog(this, "User not Found" , "Error", JOptionPane.WARNING_MESSAGE);
}

/**
 * this method will open the user HomePage after checking that the credentials placed is correct.
 */
private void openUser() {
	dispose();
	String fullName = PersonInfo.getFullName().get(userField.getText());
	//int age = PersonInfo.getAge().get(userField.getText());
	double weight = PersonInfo.getWeight().get(userField.getText());
	double height = PersonInfo.getHeight().get(userField.getText());
	new UserHomePage(userField.getText(),weight,height,fullName);
}

/**
 * this method will open the nutritionist HomePage after checking that the credentials placed is correct.
 */

private void openNutritionist(String name) {
	dispose();
	new NutritionistHomePage(name);
}

@Override
public void run() {
	try {
		Thread.sleep(1000);
   new UserAndPasswords(); // called this constructor to load the previous made users from a Properties file.
	}
	catch(Exception e) {
		
	}
}


/**
 * method that will add up the progress bar. so when the threads are done and join the main thread. it will load up.
 */
private void runProgressBar() {
	int i = 0;	
	while(true) {
		try {
		progressBar.setValue(i);
		Thread.sleep(400);
		i+=50;
		if(i == 100) { loadingFrame.dispose(); break;}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	  userField.setEditable(true);
	 passField.setEditable(true);
} 

}


