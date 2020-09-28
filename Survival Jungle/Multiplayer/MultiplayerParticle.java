package Multiplayer;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Local.Particle;
import Multiplayer.MultiplayerCell;
import Multiplayer.MultiplayerGameState;

import java.awt.*;

public class MultiplayerParticle {

	public static ArrayList<MultiplayerParticle> particles = new ArrayList<MultiplayerParticle>();

	public static int particleCount;

	public int x, y, r, g, b, mass;
	public double speed, angle, dx, dy;
	private double goalX, goalY;

	private boolean cellParticle = false;
	private boolean die = false;
	public boolean isShot;
	
	public String img; 
	protected Image image; 
	public String imgBread="/Resource/objects/bread.png";
	public String imgCheese="/Resource/objects/cheese.png";
	public String imgSteak="/Resource/objects/steak.png";
	
	public int id;

	private Color color = new Color((int) Math.floor(Math.random() * 256), (int) Math.floor(Math.random() * 256),
			(int) Math.floor(Math.random() * 256));
	
	public MultiplayerParticle(int id, int x, int y, int mass, boolean p, String img){
		particleCount++;
		this.id=id;
		this.x = x;
		this.goalX = x;
		this.goalY = y;
		this.y = y;
		this.mass = 60;
		cellParticle = p;
		this.img=img;
		ImageIcon ii = new ImageIcon(getClass().getResource(img)); 
		this.image = ii.getImage();
	}

	public void Update(MultiplayerGameState MultiplayerGameState) {
		for (MultiplayerCell cell : MultiplayerCell.cells) {
			if (this.checkCollide(cell.x, cell.y) && !cellParticle) {
				if(img==imgBread) {
					cell.addExp(5,cell); 
				}else if(img==imgCheese){
					cell.addExp(10,cell); 
				}else if(img==imgSteak) {
					cell.addExp(15,cell); 
				}
				
				this.x = (int) Math.floor(Math.random() * 10001);
				this.y = (int) Math.floor(Math.random() * 10001);
				
				String message = "FOODMOVE:" + this.id + ":" + this.x + ":" + this.y + ":";
				MultiplayerGameState.sendMessage(message);
				
			} 
				else if (this.checkCollide(cell.x, cell.y) && cellParticle && !cell.isPlayer) {
//				cell.addExp(5,cell); //add exp
				this.die = true;
			}
		}
		if (isShot) {
			dx = (speed) * Math.cos(angle);
			dy = (speed) * Math.sin(angle);
			x += dx;
			y += dy;
			speed -= 0.1;
			if (speed <= 0) {
				isShot = false;
				speed = 0;
			}
		}
	}
	
	

	private boolean checkCollide(double x, double y) {
		double centre_x1 = x + 50;
		double centre_y1 = y + 50;
		//this.x & this.y is particle coordinate.
		double distance = Math.sqrt(Math.pow((centre_x1 - this.x), 2) + Math.pow((centre_y1 - this.y), 2));
		return distance < 50;
	//	return x < this.x + 10 && x + mass > this.x && y < this.y + 10 && y + mass > this.y;
	}

	public void Draw(Graphics bbg, JPanel jpanel) {
		Particle p=new Particle(x,y,mass,cellParticle,img);
		bbg.drawImage(p.getImage(),(int) x, (int) y, (int) mass, (int) mass, jpanel);
	}

	public boolean getHealth() {
		return die;
	}

}