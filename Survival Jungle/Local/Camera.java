package Local;

import java.awt.*;
/**
 * 
 * @author hxy719@student.bham.ac.uk
 * Camera
 *
 */
public class Camera {

	public double x;
	public double y;
	public double sX;
	public double sY;

	public Camera(double x, double y, double sX, double sY) {
		this.x = x;
		this.y = y;
		this.sX = sX;
		this.sY = sY;
	}

	public double map(double x, double min1, double max1, double min2, double max2) {
		return (x - min1) * (max2 - min2) / (max1 - min1) + min2;
	}

	public void set(Graphics bbg) {
		Graphics2D g2 = (Graphics2D) bbg;
		g2.scale(sX, sY);
		g2.translate(-x, -y);
	}

	public void unset(Graphics bbg) {
		Graphics2D g2 = (Graphics2D) bbg;
		g2.translate(x, y);
	}
	
	public void scale(double sx, double sy) {
		sX = sx;
		sY = sy;
	}

	/**
	 * 
	 * @param cell
	 * scale the camera if cell mass increase
	 */
	public void Update(Cell cell) {	
		double scaleFactor;

		if (cell.size < 1000) {
			scaleFactor = map(cell.size, 10, 1000, 1.2, 0.1);
		} else {
			scaleFactor = 0.1;
		}

		scale(scaleFactor, scaleFactor);
		x = ((cell.x + cell.size * 0.5) - 800 / sX * 0.5);
		y = ((cell.y + cell.size * 0.5) - 600 / sY * 0.5);
	}
}