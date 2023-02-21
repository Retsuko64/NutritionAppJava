import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Food extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -958434615707423538L;
	private JTextField morning, noon,snack, night;
	private JButton send;
	private String username;
	
	public Food(String username) {
		this.username = username;
		itemsAdded();
		frameProperties();
	}
	
	private void frameProperties() {
		setSize(400,200);
		setLayout(new GridLayout(0,1));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
	}
	
	private void itemsAdded() {
		
		morning = new JTextField("Breakfast");
		noon = new JTextField("Lunch");
		snack = new JTextField("Snack");
		night = new JTextField("Dinner");
		send = new JButton("Done");
		
		send.addActionListener(this);
		
		add(morning);
		add(noon);
		add(snack);
		add(night);
		add(send);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
	        sendMessage();
		}
		
	}

/**
*the following method will take the user's input to be sent to server
*/
	
	public void sendMessage() {
		String breakfast, lunch, dinner, snacks;
		
		breakfast = morning.getText();
		lunch = noon.getText();
		snacks = snack.getText();
		dinner = night.getText();
	
		sendFoodIntake(breakfast, lunch, snacks, dinner);
	}


/**
* in this method we start a connect to server and send the message the user placed in the textFields
*/
	
	public void sendFoodIntake( String breakfast, String lunch , String snack, String dinner) {
		try {
		Socket client = new Socket("127.0.0.1" , 6060);
		String message = "Breakfast: " + breakfast + "\nLunch: " + lunch + "\nSnack: " + snack + "\nDinner: " + dinner;
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


}
