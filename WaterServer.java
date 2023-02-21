import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// make a getter for admin

public class WaterServer {
	
	private static ArrayList<Double> list;
	
	public WaterServer() {
		list = new ArrayList<>();
		load();  // loading my previous results from a file
		try {
		ServerSocket s = new ServerSocket(6070); //start server
		while (true) {
		Socket client = s.accept();  
		getResult(client);
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * the following method will store the information user gave in a text file by iterating through the ArrayList
	 */
	
	public void store() {
		PrintWriter w = null;
		try {
			FileOutputStream out = new FileOutputStream("waterdrank.txt");
			w = new PrintWriter(out);
			for (double value : list)
				w.println(value);
			
			out.flush();
			w.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
	
	/**
	 * the method will get the input sent from the client which will then add it the array list then it will call the store() method
	 * @param client
	 */
	public void getResult(Socket client) {
		try {
			DataInputStream dis = new DataInputStream(client.getInputStream());
			double result = dis.readDouble();
			System.out.println(result);
			list.add(result);
			store();
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
		}
	}
	
	/**
	 * when we reRun the code the array list will be empty, i want to load the previously input data to the array list by using scanner 
	 * on the text file. (else without doing this in my store() method i will loop through an empty list therefore having an empty text 
	 * and all data is lost).
	 */
	public void load() {
		Scanner s  = null;
		try {
			FileInputStream input = new FileInputStream("waterdrank.txt");
			s = new Scanner(input);
			while(s.hasNext()) {
				list.add(s.nextDouble());
			}
			s.close();
		} catch (Exception  e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * the following method will be for the admin, when he/she wants to check what the user's data we will check the file.
	 * @return
	 */
	public static String getList() {
	String message = "";
	Scanner s = null;
	int i = 1;
	try {
		FileInputStream input = new FileInputStream("waterdrank.txt");
		 s = new Scanner(input);
		 while(s.hasNext()) {
		 message += "Day " + i + ": " + s.nextDouble() + "\n";
           i++;
		 }
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	return message;
	}

	
	//start running to initiate server
	public static void main(String[] args) {
		new WaterServer();
	}
}
