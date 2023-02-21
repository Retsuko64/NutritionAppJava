import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 * This class is used to store person's info in properties file, so when we start the application again we retrieve
 * our old data.
 * i called them load methods which take the properties data into hashMap and the save methods will save the hashMap values
 * into the properties file.
 * 
 * there are 4 save methods and 4 load methods, they are similar so understanding one of them will help in getting the 
 * idea of the rest.
 * 
 * @author Hassan Fakih
 *
 */
public class PersonInfo implements Runnable{
	
private static HashMap<String, String> userFullName = new HashMap<>();
private static HashMap<String, Integer> userAge = new HashMap<>();
private static HashMap<String, Double> userWeight = new HashMap<>();
private static HashMap<String, Double> userHeight= new HashMap<>();
//private static HashMap<String, ArrayList<Double>> waterHistory= new HashMap<>(); // for later


/**
 * we will load person information such as weight height etc...
 */
public PersonInfo() {
	
}

/**
 * after a user is registered, all his info will be saved using the saver methods below.
 * 
 * @param fullName
 * @param userName
 * @param age
 * @param weight
 * @param height
 */
public PersonInfo(String fullName,String userName, int age, double weight, double height) { 
    saveAge(userName , age);
    saveWeight(userName, weight);   
    saveHeight(userName, height);
    saveFullName(userName, fullName);
}

// --------------------------- Getter Methods ------------------------------------------------
protected static HashMap<String, Integer> getAge() {
	return userAge;
}
protected static HashMap<String, Double> getWeight() {
	return userWeight;
}
protected static HashMap<String, Double> getHeight() {
	return userHeight;
}
protected static HashMap<String, String> getFullName() {
	return userFullName;
}
//-------------------------------------------------------------------------------------------


//--------------------------- Loader Methods ------------------------------------------------
/**
 * the method will load the ages from the properties file and save it in a hashMap.
 */
private void loadAge() {
	Properties p = new Properties();
	InputStream is = null;
	try {
		is = new FileInputStream("age.properties");
		p.load(is);
		
		 Iterator<Object> i = p.keySet().iterator();
		 while(i.hasNext()) {
			 String userName = (String) i.next();
			 int age = Integer.parseInt((String)p.get(userName));
			 userAge.put(userName, age);
		 }
		 is.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " load Age method");
	}
}

/**
 * the method will load the weight from the properties file and save it in a hashMap.
 */
private void loadWeight() {
	Properties p = new Properties();
	InputStream is = null;
	try {
		is = new FileInputStream("weight.properties");
		p.load(is);
		
		//iterate through the properties file
		 Iterator<Object> i = p.keySet().iterator();
		 while(i.hasNext()) {
			 String userName = (String) i.next();
			 double weight = Double.parseDouble((String)p.get(userName));
			 userWeight.put(userName, weight);
		 }
		 is.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " load Age method");
	}
}

/**
 * the method will load the height from the properties file and save it in a hashMap.
 */

private void loadHeight() {
	Properties p = new Properties();
	InputStream is = null;
	try {
		is = new FileInputStream("height.properties");
		p.load(is);
		
		//iterate through the properties file
		 Iterator<Object> i = p.keySet().iterator();
		 while(i.hasNext()) {
			 String userName = (String) i.next();
			 double height = Double.parseDouble((String)p.get(userName));
			 userHeight.put(userName, height);
		 }
		 is.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " load Age method");
	}
}

/**
 * the method will load the fullNames from the properties file and save it in a hashMap.
 */

private void loadFullName() {
	Properties p = new Properties();
	InputStream is = null;
	try {
		is = new FileInputStream("fullNames.properties");
		p.load(is);
		
		//iterate through the properties file
		 Iterator<Object> i = p.keySet().iterator();
		 while(i.hasNext()) {
			 String userName = (String) i.next();
			 String fullName = (String) p.get(userName);
			 userFullName.put(userName, fullName);
		 }
		 is.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " load Age method");
	}
}


//-------------------------------------------------------------------------------------------

//----------------Saver methods--------------------------------------------------------------
/**
 * this method will save a new user's age in a properties file
 * @param userName
 * @param age
 */
private void saveAge(String userName, int age) {
	Properties p = new Properties();
	OutputStream os = null;
	userAge.put(userName, age);
	try {
		os = new FileOutputStream("age.properties");
		
		//iterate through the hashMap
		Iterator<String> i = userAge.keySet().iterator();
		while(i.hasNext()) {
			String getName = i.next();
			String getAge = Integer.toString(userAge.get(getName));
			p.put(getName, getAge);
		}
		p.store(os, "User age information");
		os.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " (Save Age method)");
	}
}
/**
 * this method will save a new user's weight in a properties file
 * 
 * @param userName
 * @param weight
 */
private void saveWeight(String userName, double weight) {
	Properties p = new Properties();
	OutputStream os = null;
	userWeight.put(userName, weight);
	try {
		os = new FileOutputStream("weight.properties");
		
		//iterate through the hashMap
		Iterator<String> i = userWeight.keySet().iterator();
		while(i.hasNext()) {
			String getName = i.next();
			String getWeight = Double.toString(userWeight.get(getName));
			p.put(getName, getWeight);
		}
		p.store(os, "User Weight information");
		os.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " (Save weight method)");
	}
}

/**
 * this method will save a new user's height in a properties file
 * @param userName
 * @param height
 */

private void saveHeight(String userName, double height) {
	Properties p = new Properties();
	OutputStream os = null;
	userHeight.put(userName, height);
	try {
		os = new FileOutputStream("height.properties");
		
		//iterate through the hashMap
		Iterator<String> i = userHeight.keySet().iterator();
		while(i.hasNext()) {
			String getName = i.next();
			String getHeight = Double.toString(userHeight.get(getName));
			p.put(getName, getHeight);
		}
		p.store(os, "User Height information");
		os.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " (Save height method)");
	}
}

/**
 * this method will save a new user's full name in a properties file
 * @param userName
 * @param fullName
 */

protected static void saveFullName(String userName, String fullName) {
	Properties p = new Properties();
	OutputStream os = null;
	userFullName.put(userName, fullName);
	try {
		os = new FileOutputStream("fullNames.properties");
		
		//iterate through the hashMap
		Iterator<String> i = userFullName.keySet().iterator();
		while(i.hasNext()) {
			String getName = i.next();
			String getFullName = userFullName.get(getName);
			p.put(getName, getFullName);
		}
		p.store(os, "User Height information");
		os.close();
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage() + " (Save name method)");
	}
}

@Override
public  void run() {
	try {
	Thread.sleep(2000);
	loadAge();
	loadWeight();
	loadHeight();
	loadFullName();
}
	catch(Exception e) {
		
	}
}


}
