import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BarGraphModel {
	
	public static final int DEFAULT_ITEM_WIDTH = 40;
	
	/*
	 * the basic attributes of each bar, each bar will have an index,
	 * as well as percentage, width, and a Title to differentiate each bar.
	 * it is a static class meaning we cannot instantiate it with new.
	 * rather you write BarGraphModel.BarItem = new BarGraphModel.BarItem(String title);
	 */
	public static class BarItem {
		public int index;
        public double percentage;
        public int width;
        public String title;
        public Color background;
        
        public BarItem(String title) {
        	this.title = title;
        }
	}
	
	protected List<BarItem> items = new ArrayList<>(); // the data structure that'll help us construct the bar graph                         
	protected Point location = new Point();
	protected Dimension size = new Dimension();
	protected int horizontalGap = DEFAULT_ITEM_WIDTH * 2; // as in gap between each bar.
	
	/*
	 * setters and getters
	 */
	
	public void setHorizantalGap(int horizantalGap) {
		this.horizontalGap = horizantalGap;
	}
	
	public int getHorizantalGap() {
		return horizontalGap;
	}
	
	
	public Dimension getSize() {
		return size;
	}
	public void setSize(int width, int height) {
		size.width = width;
		size.height = height;
	}
	
	
	
	public void setLocation(int x ,int y) {
		this.location.x = x;
		this.location.y = y;
	}
	public Point getLocation() {
		return location;
	}
	
	
	public int getHorizantalCenterBarPosition() {
		return getX() + (getSize().width - (DEFAULT_ITEM_WIDTH + horizontalGap));
	}
	
	public int getX() {
		return location.x;
	}
	public int getY() {
		return location.y;
	}
	
	
	/**
	 * method that'll add a bar to the ArrayList. first we check if the width of the bar is zero, cause if it is we wont be able to see it
	 * so if it is zero, we set to the default width which is 40, otherwise leave it as is.
	 * 
	 * @param item
	 */
	public void addItem(BarItem item) {
		item.width = item.width == 0 ? DEFAULT_ITEM_WIDTH: item.width;
		items.add(item);
	}
	
	/**
	 * method theat'll return a bar at a certain index of the ArrayList.
	 * @param index
	 * @return BarItem
	 */
	public BarItem itemAt(int index) {
		return items.get(index);
	}
	
	/**
	 * method that'll remove a bar from the ArrayList at a certain index.
	 * 
	 * @param index
	 * @return BarItem
	 */
	public BarItem removeItemAt(int index) {
		return items.remove(index);
	}
}
