package Entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.security.PrivilegedActionException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class foodonMap {
	private int x;
	private int y;
	private int exp;
	private static String cheese = "/Resource/objects/cheese.png";
	private String water = "/Resource/objects/water.png";
	private static String steak = "/Resource/objects/steak.png";
	private static String bread = "/Resource/objects/bread.png";
	private String reborn = "/Resource/objects/reborn.png";
	protected String img;// location of img
	protected Image image;
	private boolean eaten = false; // on the map? or not now

	public static String getCheese() {
		return cheese;
	}

	public String getWater() {
		return water;
	}

	public static String getSteak() {
		return steak;
	}

	public static String getBread() {
		return bread;
	}

	public String getReborn() {
		return reborn;
	}

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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public foodonMap() {
		// TODO Auto-generated constructor stub
		setX(100);
		setY(200);
		setExp(20);
		ImageIcon ii = new ImageIcon(getClass().getResource(water));
		image = ii.getImage();

	}

	public Graphics2D draw(Graphics2D g, JPanel jp) {
		// Graphics2D g2d = (Graphics2D) g;
		// g.rotate(Math.toRadians(30),getX(),getY());
		g.drawImage(getImage(), getX(), getY(), 100, 100, jp);
		// Toolkit.getDefaultToolkit().sync();
		return g;
	}

}
