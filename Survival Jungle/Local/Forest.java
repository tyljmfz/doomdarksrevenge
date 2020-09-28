package Local;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Entity.Player;
/**
 * 
 * @author hxy719@student.bham.ac.uk
 * Forest class----include show the Forest on the map, and the function of Forest:
 * Forest is a habitat. As Cats, tigers and leopards can climb trees; 
 * So that they can hide from their predator in forest for a short time.
 */
public class Forest {
	public static ArrayList<Forest> forests = new ArrayList<Forest>();
	public static int forestCount;
	protected String img; 
	protected Image image; 
	private int x;
	private int y;
	private int d=270;
	int colCount = 0;
	double colX, colY;
	public boolean couldHide=false;	//default----->no special events

//	private Color color = new Color((int) Math.floor(Math.random() * 256), (int) Math.floor(Math.random() * 256),
//			(int) Math.floor(Math.random() * 256));
	/**
	 * 
	 * @param x: the x position of forest
	 * @param y: the y position of forest
	 * @param d: the radis of forest
	 */
	public Forest(int x,int y, int d) {
		forestCount++;
		this.x=x;
		this.y=y;
		this.d=d;
		this.img= "/Resource/objects/forest.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img)); 
		this.image = ii.getImage();

	}
	/**
	 * 
	 * @param level of cell
	 * @return true/false-------couldHide or not
	 */
	public boolean checkHide(int level) {
		if(level==1 || level==4|| level==5) {
			couldHide=true;
		}else {
			couldHide=false;
		}
		return couldHide;
	}
	/**
	 * Update the states of forests
	 * and have different responses to different animals
	 */
	public void Update() {
		for (Cell cell : Cell.cells) {
			couldHide=false;
			this.colCount = 0;
			if(cell.currentLv == cell.level.get(1) || cell.currentLv == cell.level.get(4) || 
					cell.currentLv == cell.level.get(5)) {
				couldHide=true;
			}else {
				couldHide=false;
				if(checkCollide(cell.x,cell.y)) {
//					boundsOut(cell);
					double dx = (this.colX - this.x);
					double dy = (this.colY - this.y);
					cell.x += (dx) * 1 / 100;
					cell.y += (dy) * 1 / 100;
//					double dx = (this.x-cell.colX);
//					double dy = (this.y-cell.colY);
//					double dx = (cell.x - this.x);
//					double dy = (cell.y - this.y);
//					double dx = (cell.colX - cell.x);
//					double dy = (cell.colY - cell.y);
//					cell.x += (dx) * 1 / 100;
//					cell.y += (dy) * 1 / 100;
//					double dx = (cell.colX - this.x);
//					double dy = (cell.colY - this.y);
//					cell.x += (dx) * 1 / 100;
//					cell.y += (dy) * 1 / 100;
////				
				}
			}
		}
	}
	/**
	 * 
	 * @param cell
	 * let the cell have different movements when collide (rebound) 
	 */
	public void boundsOut(Cell cell) {
		int distance = 100;
		if (this.x < cell.x) {
			this.colX = this.x - distance;
			cell.colX = cell.x + distance;
		} else if (this.x > cell.x){
			this.colX = this.x + distance;
			cell.colX = cell.x - distance;
		}
		if (this.y < cell.y) {
			this.colY = this.y - distance;
			this.colY = cell.y + distance;
		} else if (this.y > cell.y){
			this.colY = this.y + distance;
			cell.colY = cell.y - distance;
		}
	}
	/**
	 * 
	 * @param x: the x position of forests
	 * @param y: the y position of forests
	 * @return  true/false
	 */
	private boolean checkCollide(double x, double y) {
		double centre_x1 = x-75;
		double centre_y1 = y-70 ;
		//this.x & this.y is particle coordinate.
		double distance = Math.sqrt(Math.pow((centre_x1 - this.x), 2) + Math.pow((centre_y1 - this.y), 2));
		return distance < 180;
	}
//	private boolean checkCollide(double x, double y) {
//		double centre_x1 = x-150;
//		double centre_y1 = y-150 ;
//		//this.x & this.y is particle coordinate.
//		double distance = Math.sqrt(Math.pow((centre_x1 - this.x), 2) + Math.pow((centre_y1 - this.y), 2));
//		return distance < 230;
//	}
//	public void draw(Graphics bbg) {
//		bbg.setColor(color);
//		bbg.fillOval(x, y, d, d);
//		bbg.drawOval(x, y, d, d);
//	}
//	public Forest(int x, int y, int d) {
//		this.img= "/Resource/objects/forest.png";
//		ImageIcon ii = new ImageIcon(getClass().getResource(img));
//		this.image = ii.getImage();
//	}
	public void draw(Graphics bbg, JPanel jpanel) {
		Forest f= new Forest(x,y,d);
		bbg.drawImage(f.getImage(),(int) x, (int) y, (int) d, (int) d, jpanel);
	}
	
//	private boolean checkCollide(double x, double y, double mass) {
//		return x < this.x + 10 && x + mass > this.x && y < this.y + 10 && y + mass > this.y;
//	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getD() {
		return d;
	}
	
	public void setD(int d) {
		this.d=d;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Image getImage() {
		return image;
	}
}
