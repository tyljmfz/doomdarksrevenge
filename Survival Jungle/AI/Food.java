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

//�������water���ֵ�λ�ã���������ʧ�����ˢ��
public class Food extends JFrame {
	//����������
	private GamePanel gamepanel;
	private Player player;
	//С����������
	public Point location;
	private Random rand=new Random();
	public ArrayList<Food> ball =new ArrayList<Food>();	//������ʳ���������
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
            //��̰���ߵ���ͷ��ʳ�﷢����ײ������������µ�ʳ��λ��  
            location=new Point(Math.abs(rand.nextInt(gp.getWidth())%gamepanel.getWidth()),Math.abs(rand.nextInt(gp.getHeight())%gamepanel.getHeight()));  
            
        }               
	}
}