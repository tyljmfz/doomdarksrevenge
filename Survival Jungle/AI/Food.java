package AI;
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Point;  
import java.util.Random;  
import java.awt.Color;  
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random; 
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Entity.Player;

//随机设置water出现的位置，并且在消失后进行刷新
public class Food extends JFrame {
	//借用其他类
	private GamePanel gamepanel;
	private Player player;
	//小球自身性质
	public Point location;
	private Random rand=new Random();
	public ArrayList<Food> ball =new ArrayList<Food>();	//建立放食物球的容器
	public Food(GamePanel gp, Player p) {
		gamepanel=gp;
		player=p;
//		x=rand.nextInt(800);
//		y=rand.nextInt(600);
		location=new Point(Math.abs(rand.nextInt(gp.getWidth())%gamepanel.getWidth()),Math.abs(rand.nextInt(gp.getHeight())%gamepanel.getHeight()));  
//		size=new Point(60,60);
	}
	
//	private Image food;
	public void drawFood(Graphics g) {
		g.setColor(Color.RED);  
		int d=30;
		g.fillOval(location.x, location.y, d, d);  

	}
	public void drawWater(Graphics g) {
		g.setColor(Color.BLUE);  
		int d=30;
		g.fillOval(location.x, location.y, d, d);  

	}
//		food=new ImageIcon().getImage();
	public void regenerate() {
		if((player.getX()-location.x)*(player.getX()-location.x)+(player.getY()-location.y)*(player.getY()-location.y)<(player.getDegree()*player.getDegree())){  
            //若贪吃蛇的蛇头与食物发生碰撞，则随机生成新的食物位置  
            location=new Point(Math.abs(rand.nextInt(gp.getWidth())%gamepanel.getWidth()),Math.abs(rand.nextInt(gp.getHeight())%gamepanel.getHeight()));  
            
        }               
	}
}