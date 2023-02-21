import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BMICalculator extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3873801482481700206L;
	private JTextField heightField, weightField;
	private JLabel heightLabel, weightLabel ,resultLabel;  // weight in kilograms and height in meters
	private Thread t;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	// BMI formula : weight(Kg)/height^2(m)
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRoundRect(20, 200, 260, 100 , 10, 10);
	}
	
	/**
	 * the thread is gonna keep trying to calculate, when it gets an exception 
	 * case nothing will happen, but when the values are correct it'll display the BMI (check run method)
	 */
	public BMICalculator() {
		itemsAdded();
		frameProperties();
		t = new Thread(this);
		t.start();
	}
	
	private void frameProperties() {
		setTitle("Calculator");
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setSize(300,320);
		getContentPane().setBackground(Color.ORANGE);
	}
	
	private void itemsAdded() {
		heightLabel = new JLabel("Height(cm):");
		heightLabel.setBounds(30 , 20, 100 ,50 );
		add(heightLabel);
		
		heightField = new JTextField();
		heightField.setBounds(160 , 28, 100 ,40);
		add(heightField);
		
		weightLabel = new JLabel("Weight(kg):");
		weightLabel.setBounds(30, 100 , 100 , 50);
		add(weightLabel);
		
		weightField = new JTextField();
		weightField.setBounds(160, 108, 100, 40);
		add(weightField);
		
		resultLabel = new JLabel("Results");
		resultLabel.setBounds(100, 200 , 100 ,40);
		add(resultLabel);
		
	}
	
	
	public static void main(String[] args) {
		new BMICalculator();
	}
	
	
	@Override
	public void run() {	
		while(true) {
			try {
				double weight = Double.parseDouble(weightField.getText());
				double height = Double.parseDouble(heightField.getText())/100;  // to get meters
				double result = (weight / (height*height));
				resultLabel.setText(df.format(result) + "");
			}
			catch(Exception e) { resultLabel.setText("Input both values"); }
		}
	}

}
