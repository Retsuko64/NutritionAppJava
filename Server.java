import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Server{
	
	private ServerSocket server;
	private boolean acceptClients; // the server is going to keep accepting clients
	
	public Server() {
		acceptClients = true;
		try {
          ServerSocket server = new ServerSocket(6060);
		while(acceptClients) {	
		Socket client = server.accept();
		System.out.println("Got client");
		getUserFoodIntake(client);
		}
		} catch(Exception e) {
			shutdown();
		}
	}
	
	/**
	 * the following method will get the client's data and store them in a file using PrintWriter
	 * 
	 * @param client
	 */
	
	public void getUserFoodIntake(Socket client) {
		try {
		DataInputStream dis = new DataInputStream(client.getInputStream());
		System.out.println("getting message");
		String message = dis.readUTF();
		PrintWriter w = new PrintWriter(new FileOutputStream("FoodIntake.txt")); 
		w.write(message);
		w.flush();
		w.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 *  this method returns a string for the Admin to see from a file
	 *  
	 * @return string message of user's daily FoodIntake.
	 */
	
	public static String readUserFoodIntake() {
		Scanner s = null;
		String message = "";
		try {
			s = new Scanner(new FileInputStream("FoodIntake.txt"));
			while(s.hasNextLine()) {
				message += s.nextLine() + "\n";
                    
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}
	

	/**
	 * in case of an error within the server we will shut it down and stop accepting clients.
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
	
	public static void main(String[] args) {
		Server s = new Server();
	}
}
