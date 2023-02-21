import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AdminChatPage extends JFrame implements ActionListener ,KeyListener, Runnable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740163665630597891L;
    private JPanel chatPanel;
	private JButton sendBtn;
	private JTextField message;
	private Box vertical; // its similar to panel
	private ServerSocket server;
	private static Socket client;
	private boolean acceptClients;
	
	
	public AdminChatPage() {
		acceptClients = true;
		itemsAdded();
		frameProperties();
	}
	
	private void frameProperties() {
		setTitle("Chat Box");
		setSize(620,400);
		setDefaultCloseOperation(HIDE_ON_CLOSE); 
		setLayout(null);
		setResizable(false);
		setVisible(true);
	    getContentPane().setBackground(Color.PINK);
	}
	
	private void itemsAdded() {
		
		vertical = Box.createVerticalBox();
		
		chatPanel = new JPanel();
		chatPanel.setBounds(5,30,595,290);
		chatPanel.setLayout(new BorderLayout());
		add(chatPanel);
		
		message = new JTextField("Type Here");
		message.setBounds(5,325,520,30);
		message.addKeyListener(this);
		add(message);
		
		sendBtn = new JButton("Send");
		sendBtn.setBounds(528,325,75,30);
		sendBtn.addActionListener(this);
		add(sendBtn);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendBtn) {
			sendMessage();
		}	
	}
	
	/**
	 * when the user sends a message, we will display the message on his/her own screen by laying them out using a BoxLayout which will 
	 * lay them out along the y-axis. generally we create a panel with such layout and add a label message to that panel.
	 * 
	 * @param message that the user sent.
	 * @return panel that contains the message sent
	 */

	public JPanel formatLabel(String message) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 100px\">" + message + "</p></html>");
	    output.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    output.setBorder(new EmptyBorder(5, 5, 15, 20));
		
	    panel.add(output);
		
		return panel;
	}
	
	/**
	 * When user clicks on the send button we will first check if the textField is empty else we go for the formatLabel() method which will return a panel carrying the 
	 * message he/she sent, then we will add it to our vertical box to the right side of the screen,and then after all the formatting, we will send the message to the server.
	 */
	public void sendMessage(){
	String toSend = message.getText(); 
	
	if(toSend.trim().equals("")) JOptionPane.showMessageDialog(null, "Empty message");
	else {
		JPanel userMessagePanel = formatLabel(toSend);
		
		JPanel right = new JPanel(); // the sender side for messages (Left side)
		right.setLayout(new BorderLayout());
		
        right.add(userMessagePanel, BorderLayout.LINE_START);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(5));
        
        chatPanel.add(vertical, BorderLayout.PAGE_START);
        
        message.setText("");
        
       sendMessageToStream("(Admin) " + toSend);
       
       repaint();
       invalidate();
       validate();
	}
	}
	
	/**
	 * the following method will send a message to the server
	 * 
	 * @param message
	 */
	public void sendMessageToStream(String message) {
		try {
			OutputStream os = client.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF("Jane: " + message);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + " (Sending error)");
		}
	}
		
	@Override
	public void run() {
		try {
			server = new ServerSocket(6001);
		while(true) {
			client = server.accept();	
	       	  DataInputStream dis = new DataInputStream(client.getInputStream());
				while (true) {
					String message = dis.readUTF(); // get message from server
					JPanel panel = formatLabel(message); 
					
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    
                    vertical.add(left);// put it on our screen
                    chatPanel.add(vertical,BorderLayout.PAGE_START);
                    validate();
				}
			
		
		} 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * in case of an error occurred within the server we will shut it down and stop accepting clients.
	 */
	public void shutdown() {
		acceptClients = false; // the server wont accept anymore clients
		try {
		if(!server.isClosed()) 
			server.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server is offline");
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(message.getText().equals("Type Here"))
			message.setText("");
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			sendMessage();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	

}
