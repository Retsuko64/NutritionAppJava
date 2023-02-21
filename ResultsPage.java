import java.awt.Color; 
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ResultsPage extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4251249698023731278L;
	protected static HashMap<String, ArrayList<Double>> BMIHistory = new HashMap<>();
	private JFrame frame;
	private BarGraph graph;
//	private BarGraphModel model;
	
	public ResultsPage(String userName) {
		loadBMI();
		ArrayList<Double> getBMI = BMIHistory.get(userName);
		
		this.graph = new BarGraph(getBMI);
	//	this.model = graph.getModel();
		
		frame = new JFrame("Results");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(460, 420);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        setBackground(Color.PINK);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graph.draw(g);
	}

/**
 * the method will load the user's BMI history and put them in a HashMap. it's originally stored in a properties file
 * so basically we will iterate through that properties file.
 */

private static void loadBMI() {
	Properties p = new Properties();
	InputStream is = null;
	try {
		is = new FileInputStream("BMIHistory.properties");
		p.load(is);
		
		 Iterator<Object> i = p.keySet().iterator();
		 while(i.hasNext()) {
			 String userName = (String) i.next();
			 String BMIString = (String) p.get(userName);
			 ArrayList<Double> BMI = getBMIData(BMIString);// converting the string to array (read below)
			 BMIHistory.put(userName,BMI); 
		 }
		 is.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " load BMI method");
	}
}

/**
 * In a properties file, the key and value pairs are stored as strings, so this method will read through each key's value
 * by splitting them into an array of integers and saving it as an array List. (i intended to use array list so it could
 * support me with the bar graph.) so for example "23 25 32" would give me {23,25,32}
 * 
 * @param dataString - BMI data to be converted into an Array
 * @return dataArrayList - the data the got converted to a String
 */

private static ArrayList<Double> getBMIData(String dataString){
	String[] dataArray = dataString.split("@", -1);
	ArrayList<Double> dataArrayList = new ArrayList<>();
	
	//store them in an ArrayList after splitting them
	for(String value: dataArray) {
		dataArrayList.add(Double.parseDouble(value));
	}
		return dataArrayList;
}

/**
 * A method that saves BMI to a file, by iterating through the hashMap and everything will put to a properties file.
 * @param userName
 * @param BMI
 */	
		public static void saveBMI(String userName, double BMI) {
				Properties p = new Properties();
				OutputStream os = null;
				ArrayList<Double> newBMI = BMIHistory.get(userName);
				newBMI.add(BMI);
				BMIHistory.put(userName, newBMI);
				try {
					os = new FileOutputStream("BMIHistory.properties");
					
					//iterate through the hashMap
					Iterator<String> i = BMIHistory.keySet().iterator();
					while(i.hasNext()) {
						String getName = i.next();
						ArrayList<Double> getBMI = BMIHistory.get(getName);
						String getBMIString = convertToString(getBMI);
						p.put(getName, getBMIString);
					}
					p.store(os, "User BMI History information");
					os.close();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage() + " (Save BMI method)");
				}
		}
		
		/**
		 * method that'll convert the array list to a String which will be used to store in the properties file.
		 * @param getBMI
		 * @return
		 */
		private static String convertToString(ArrayList<Double> getBMI) {
			String data = "";
			for (double value : getBMI) {
				data += value + "@";
			}
			return data;
		}
}
