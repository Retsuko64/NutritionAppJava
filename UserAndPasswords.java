import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import javax.swing.JOptionPane;

public class UserAndPasswords {
	
	private static HashMap<String, String> normalUser = new HashMap<>();
	private static HashMap<String, String> nutritionist = new HashMap<>();
	
	
	/**
	 * in this constructor, we will load the previous made user names and passwords using Properties file.
	 * and it will be saved in a hashMap.
	 */
	public UserAndPasswords() {
		loadUsers();	
		loadNutritionists();
	}
	
	/**
	 * In this  method, we depended on using Properties class. which store the users and passwords in key/value pairs.
	 * what will happen here is that we load the properties file the contains the previously made users, and it will save them in
	 * a hashMap (normalUser HashMap being this class's instance variable).
	 */
	
	private static void loadUsers() {
		Properties p = new Properties();
		InputStream input = null;
		//loading users
		try {
			input = new FileInputStream("saved_users.properties");
			p.load(input);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
		// place them in a HashMap.
		Iterator<Object> i1 = p.keySet().iterator();
		while(i1.hasNext()) {
			String userName = (String) i1.next();
			String userPassword = p.getProperty(userName);
			normalUser.put(userName, userPassword);
		}
	}
	
	/**
	 * In this  method, we depended on using Properties class. which store the users and passwords in key/value pairs.
	 * what will happen here is that we load the properties file the contains the previously made users, and it will save them in
	 * a hashMap (nutritionist HashMap being this class's instance variable).
	 */
	private static void loadNutritionists() {
		Properties p = new Properties();
		InputStream input = null;
		//loading users
		try {
			input = new FileInputStream("saved_nutritionists.properties");
			p.load(input);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
		// place them in a HashMap.
		Iterator<Object> i1 = p.keySet().iterator();
		while(i1.hasNext()) {
			String getNutritionist = (String) i1.next();
			String getPassword = p.getProperty(getNutritionist);
			nutritionist.put(getNutritionist, getPassword);
		}
	}
	
	/**
	 * a method that will add a new user (user name and password) and put it in a HashMap as well as save it to the properties file.
	 * 
	 * @param String - newUser
	 * @param String - newPassword
	 */
	
	protected static void registerUser(String newUser, String newPassword) {
	 normalUser.put(newUser, newPassword);
	 Properties p = new Properties();
	 OutputStream os = null;  
	 try {
		 os = new FileOutputStream("saved_users.properties");
		 
		 for(Entry<String, String> set: normalUser.entrySet()) {
			 p.put(set.getKey() , set.getValue());
		 }
		 p.store(os,"User login information" );
		
	 }
	 catch(Exception e) {
		 JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
	 }
		}
	
	/**
	 * a method that will add a new nutritionists (user name and password) and put it in a HashMap as well as save it to the properties file.
	 * 
	 * 
	 * !! will likely remove cause for the project i found it convinient to just have one Admin.
	 * 
	 * @param String - newUser
	 * @param String - newPassword
	 */
	protected static void registerNutritionist(String newUser, String newPassword) {
		 nutritionist.put(newUser, newPassword);
		 Properties p = new Properties();
		 OutputStream os = null;  
		 try {
			 os = new FileOutputStream("saved_nutritionists.properties");
			 for(Entry<String, String> set: nutritionist.entrySet()) {
				 p.put(set.getKey() , set.getValue());
			 }
			 p.store(os,"User login information" );
			
		 }
		 catch(Exception e) {
			 JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		 }
			}
	

	/**
	 * a method that would return a HashMap of user names and passwords
	 * 
	 * @return HashMap<String,String> - User names and id's
	 */
	
	protected static HashMap<String, String> getUserInfo() {
		return normalUser;
	}
	/**
	 * a method that would return a HashMap of nutritionist names and passwords 
	 * 
	 * @return HashMap<String,String> - nutritionist names and id's
	 */
	
	protected static HashMap<String, String> getNutritionistInfo() {
		return nutritionist;
	}

}
