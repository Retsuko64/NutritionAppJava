import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NutritionistHomePage extends JFrame
implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -414079354066840839L;
    private JLabel welcome, onUser;
    private JButton diet, waterConsumed, foodConsumed, logout, chat, results, add, delete;
    private JPanel panel;
    private JComboBox<String> users;
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	g.drawRect(15, 80, 560, 300);
    }
    
	public NutritionistHomePage(String name) {
		panel = new JPanel();
		itemAdded(name);
		setLayout(null);
		setSize(600, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.PINK);
		setTitle("Nutritionist");
		setVisible(true);
	}
	
	private void itemAdded(String name) {
	  panel.setLayout(null);
	  
	    welcome = new JLabel();
		String fullName = PersonInfo.getFullName().get(name);
		welcome.setText("Welcome: " + fullName);
		welcome.setFont(new Font("Impact", Font.BOLD, 25));
		welcome.setBounds(10,10,200, 20);
		add(welcome);
		
		  diet = new JButton("Edit Diet");
		  diet.setBounds(80,60, 110 ,60);
		  diet.setFont(new Font("Impact", Font.PLAIN, 20));
		  diet.setFocusable(false);
		  diet.addActionListener(this);
		  panel.add(diet);
		  
		  waterConsumed = new JButton("Water Consumed");
		  waterConsumed.setBounds(350, 60, 170, 60);
		  waterConsumed.setFont(new Font("Impact", Font.PLAIN, 18));
		  waterConsumed.setFocusable(false);
		  waterConsumed.addActionListener(this);
		  panel.add(waterConsumed);
		  
		  foodConsumed = new JButton("Food Consumed");
		  foodConsumed.setBounds(350, 180, 170 , 60);
		  foodConsumed.setFont(new Font("Impact", Font.PLAIN, 18));
		  foodConsumed.setFocusable(false);
		  foodConsumed.addActionListener(this);
		  panel.add(foodConsumed);
		  
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
		  
		  onUser = new JLabel("Diet for: ");
		  onUser.setFont(new Font("Impact", Font.PLAIN, 20));
		  onUser.setBounds(5,270,150 , 25);
		  panel.add(onUser);
		  
		  add = new JButton("Add");
		  add.setFont(new Font("Impact", Font.PLAIN, 15));
		  add.setFocusable(false);
		  add.setBounds(175,320, 60,25);
		  add(add);
		  
		  delete = new JButton("Delete");
		  delete.setFocusable(false);
		  delete.setFont(new Font("Impact", Font.PLAIN, 15));
		  delete.setBounds(240,320, 80,25);
		  add(delete);
		  
		  
		  Iterator<String> i =UserAndPasswords.getUserInfo().keySet().iterator();
		  users = new JComboBox<>();
		  while (i.hasNext()) {
			  users.addItem(i.next());
		  }
	      users.setBounds(85,320,80,25);
	      add(users);
		  
		  add(panel);
		  panel.setBounds(10,50,560,300); 
		}
	
	
	/**
	 * check the getList method for more, basically it will return a string and we'll put it on a message dialog.
	 */
	public void displayResult() {
		JOptionPane.showMessageDialog(this, WaterServer.getList());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout) {
        	dispose();
        	new LoginPage();
        }
        else if (e.getSource() == results) {
        	String user =(String) users.getSelectedItem();
        	new ResultsPage(user);
        }
        else if (e.getSource() == chat) {
        	AdminChatPage a =new AdminChatPage();
        	Thread t = new Thread(a);
        	t.start();
        }
        else if (e.getSource() == foodConsumed || e.getSource() == diet) {
        	JOptionPane.showMessageDialog(this, Server.readUserFoodIntake());
        }
        else if (e.getSource() == waterConsumed) {
        	displayResult();
        }
	}
	
	
}
