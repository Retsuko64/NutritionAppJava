import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.FontMetrics;

public class BarGraph {
	
	protected BarGraphModel model;
	protected static final Font BAR_TITLE_FONT = new Font("Calibri", Font.PLAIN, 12);

	public BarGraph(ArrayList<Double> listToPrint) {
	 System.out.println(listToPrint);
		this.model = new BarGraphModel();
		
		model.setLocation(20,50);
		model.setSize(400,300);
		
		int i = 1; // to print month
		for (double value : listToPrint) {
			BarGraphModel.BarItem item = new BarGraphModel.BarItem("Month " + i);
			item.percentage = (value * 100) / 30;
			model.addItem(item);
			i++;
		}
		
		
	/*	 TESTING
	BarGraphModel.BarItem item = new BarGraphModel.BarItem("Month 1");
		item.percentage = 70;
		model.addItem(item);
		
	item = new BarGraphModel.BarItem("Month 2");
       item.percentage = 60;
        model.addItem(item);
        */
	}
	
	public BarGraphModel getModel() {
		return model;
	}
	
	public void setModel(BarGraphModel model) {
        this.model = model;
    }
	
	/**
	 * this method will call two methods : drawItems() and drawBorder(). check their implementations for more
	 * @param g
	 */
	public void draw(Graphics g) {
		drawItems(g);
        drawBorder(g);
	}
	
	/**
	 * the method will draw each bar in the array list.
	 * it will iterate through it and get the necessary values, like its x and y positions, and its size according the the location of the barGraph,
	 * and then we will use the fillRectangle(..) method and draw them.
	 * for the the title, we will center in each following bar, using the FontMetrics to get the width of the string. then using drawString(..) to write it.
	 * 
	 * @param g
	 */
	
	private void drawItems(Graphics g) {
		int i = 0;
		g.setFont(BAR_TITLE_FONT);
		FontMetrics fm = g.getFontMetrics(BAR_TITLE_FONT);
		
		for(BarGraphModel.BarItem item: model.items) {
			
			int percentHeight = ((int) ((double)item.percentage/100 * model.getSize().height));
			
			int x = model.getX() + i * model.getHorizantalGap();
			int y = model.getY() + model.getSize().height - percentHeight;
			int w = item.width;
			int h = percentHeight;
			g.fillRect(x, y, w, h);
			i++;
			
			//draw title
			 int sw = fm.stringWidth(item.title);
	            if (sw < item.width){
	                x = x + ((item.width - sw)/2);
	            } else {
	                x = x - ((sw - item.width)/2);
	            }
	            
	            y = model.getY() + model.getSize().height + BAR_TITLE_FONT.getSize();
	            g.drawString(item.title, x, y);			
		}
	}
	
	/**
	 * method that will draw a border surrounding our bar graph
	 * @param g
	 */
	 private void drawBorder(Graphics g){
	        g.setColor(Color.cyan);
	        g.drawRoundRect(model.getX(), model.getY(), model.getSize().width, model.getSize().height, 15, 15);
	    }
}
