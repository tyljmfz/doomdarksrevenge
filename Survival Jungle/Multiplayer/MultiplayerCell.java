package Multiplayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Audio.Audio_player;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiplayerCell {

	public static ArrayList<MultiplayerCell> cells = new ArrayList<MultiplayerCell>();
	public int id;
	public static ArrayList<Player> level = new ArrayList<Player>();
	public static int cellCount;

	public Player currentLv; // Type: Player --> get the current Level animal.
	public int levelNum = 0; // Type : int --> change while level up / down.
	public String name; // player name
	double radius = 50; // the radius of the animals
	public int currentExp; // instant experience
	public int currentHp; // instant health value
	int size = 100;
	int speed = 1;
	public boolean isPlayer = false;
	int centre_x;
	int centre_y;

	public int index=0;

	boolean isTarget = false;
	String targetType = "p"; // to determine it is food or not.

	public double x; // each cell x-coordinate
	public double y; // each cell y-coordinate

	public double goalX, goalY; // target x-coordinate.
	boolean goalReached = true;

	double colX, colY;
	boolean colRached = false;
	int colCount = 0;
	MultiplayerCell colCell;
	
	MultiplayerGameState MultiplayerGameState;

	private HashMap<String, Audio_player> sfx;

	
	public MultiplayerCell(int id, String name, double x, double y, boolean isPlayer) {
		initLevel();
		this.id = id;
		this.name = name;
		this.x = x;
		this.goalX = x;
		this.goalY = y;
		this.y = y;
		this.isPlayer = isPlayer;
		// this.randomColor();
		this.currentExp = 0;
		this.currentLv = level.get(levelNum);
		this.currentHp = currentLv.getHealth();
		cellCount++;
		 
		//add audio
		sfx = new HashMap<String, Audio_player>();
		sfx.put("exdown",new Audio_player("/Audio/exdown.mp3"));
		sfx.put("exup",new Audio_player("/Audio/exup.mp3"));

	}
	
	public MultiplayerCell(int id, String name, double x, double y, boolean isPlayer, int currentHP , int currentExp) {
		initLevel();
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.goalX = x;
		this.goalY = y;
		this.isPlayer = isPlayer;
		// this.randomColor();
		this.currentExp = currentExp;
		this.currentLv = level.get(levelNum);
		this.currentHp = currentHP;
		cellCount++;
		 
		//add audio
		sfx = new HashMap<String, Audio_player>();
		sfx.put("exdown",new Audio_player("/Audio/exdown.mp3"));
		sfx.put("exup",new Audio_player("/Audio/exup.mp3"));

	}

	// set the level array list.
	public void initLevel() {
		level.add(new Mouse());
		level.add(new Cat());
		level.add(new Dog());
		level.add(new Wolf());
		level.add(new Leopard());
		level.add(new Tiger());
		level.add(new Lion());
		level.add(new Elephant());
	}

	public void attack(int currentHp) {
		currentHp = currentHp - this.currentLv.getAttack(); // get attacked.
		// check level up: if true: reset exp && hp;
	}

	public void addExp(int exp, MultiplayerCell cell) {
		if (cell.levelNum == 7 && currentExp >= cell.currentLv.getExp()) {

		} else {
			cell.currentExp += exp;
			if (cell.currentExp == cell.currentLv.getExp()) {
				levelUp(cell);
				//play audio
				sfx.get("exup").play();
			}
		}
	}
	
	public void setExp(int exp) {
		this.currentExp = exp;
	}
	
//	Test!!!!!!!!!!!!!!!!!!!!!!!
	public void reduceHp(double hp, MultiplayerCell cell) {
		
//		if(cell.currentHp>0) {
			cell.currentHp-=hp;
			if(cell.currentHp<=30) {
				cell.currentHp=30;
			}
			if(cell.currentHp==0) {
				die(cell,this);
			}
//		}
	}
	
	public void setLevel(int levelnum) {
		this.levelNum = levelnum;
		if (this.currentLv.getLevel() > levelnum) {
			sfx.get("exdown").play();
			this.currentLv = level.get(levelnum);
			this.currentExp = 0;
			this.currentHp = this.currentLv.getHealth();
		} else if (this.currentLv.getLevel() < levelnum){
			sfx.get("exup").play();
			this.currentLv = level.get(levelnum);
			this.currentExp = 0;
			this.currentHp = this.currentLv.getHealth();
		} else if (this.currentLv.getLevel() == levelnum){
			
		}
	}
	
	public void updateCell(double x, double y, int levelnum, int hp, int exp){
		this.goalX = x;
		this.goalY = y;
		this.levelNum = levelnum;
		setLevel(levelnum);
		this.currentHp = hp;
		this.currentExp = exp;
		// GAMESTATE:ID:X:Y:LEVEL:HP:EXP
	}
	
	public void levelUp(MultiplayerCell cell) {
		if (cell.levelNum == 7) {
			cell.currentLv = level.get(7);
		} else {
			cell.currentLv = level.get(++levelNum);
			cell.currentExp = 0;
			cell.currentHp = cell.currentLv.getHealth();
			
			//play audio
			sfx.get("exup").play();
		}
	}

	public void levelUp_m_eat_e(MultiplayerCell cell) {
		cell.levelNum = 3;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentExp = 0;
		cell.currentHp = cell.currentLv.getHealth();
	}

	public void die(MultiplayerCell cell, MultiplayerCell winner) {
		int expAdd = cell.currentExp + cell.currentLv.getAddUpExp();
		addExp(expAdd, winner);
		cell.levelNum = 0;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentHp = cell.currentLv.getHealth();
		cell.currentExp = 0;
		respawn(cell);
	}
	
	public void downgrade(MultiplayerCell cell) {
		cell.levelNum = cell.levelNum - 1;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentHp = cell.currentLv.getHealth();
		cell.currentExp = 0;
		respawn(cell);
		//play audio
		sfx.get("exdown").play();
	}

	public void Update(MultiplayerGameState MultiplayerGameState) {
		this.MultiplayerGameState = MultiplayerGameState;
		if (this.currentExp >= this.currentLv.getExp()) {
			levelUp(this);
		}
		// check whether elephant or mouse..<-- special case
		for (MultiplayerCell cell : cells) {
			if (this.checkCollide(cell)) {
				this.colRached = true;
				this.colCount = 0;
				boundsOut(cell);
				colCell = cell;

				if (this.levelNum > cell.levelNum) {
					if (this.levelNum == 7 && cell.levelNum == 0) {
						this.currentHp -= cell.currentLv.getAttack();
						if (this.currentHp <= 0) {
							downgrade(this);
							levelUp_m_eat_e(cell);
						}
					}
					cell.currentHp -= this.currentLv.getAttack();

					if (cell.currentHp <= 0) { // HP <= 0 die
						if (cell.levelNum == 0) {
							die(cell, this);
						} else {
							downgrade(cell);
						}
					}
				}
			}
		}
		
		if (colRached) {
			collision();
		} else {
			double dx = (goalX - this.x);
			double dy = (goalY - this.y);
			this.x += (dx) * 1 / 100;
			this.y += (dy) * 1 / 100;
		}
		
	}

	public void getMouseX(int mx) {
		goalX = mx;
	}

	public void getMouseY(int my) {
		goalY = my;
	}
	

	public void boundsOut(MultiplayerCell cell) {
		int distance = 150;
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
		String message = "MOVE:" + this.id + ":" + this.x + ":" + this.y;
		MultiplayerGameState.sendMessage(message);
		message = "MOVE:" + cell.id + ":" + cell.x + ":" + cell.y;
		MultiplayerGameState.sendMessage(message);

	}
	
	public void collision() { 
		double dx = (this.colX - this.x);
		double dy = (this.colY - this.y);
		this.x += (dx) * 1 / 100;
		this.y += (dy) * 1 / 100;
		if (colCount > 30) {
			colCount = 0;
			colRached = false;
		}
		colCount++;
	}


	// the HP value is reached.
	public void respawn(MultiplayerCell cell) {
		cell.x = (int) Math.floor(Math.random() * 10001);
		cell.y = (int) Math.floor(Math.random() * 10001);
		// this.currentLv = level.get(0);
		String message = "MOVE:" + this.id + ":" + this.x + ":" + this.y;
		MultiplayerGameState.sendMessage(message);

	}

	// collision

	public boolean checkCollide(MultiplayerCell cell) {
		if (this.name.equals(cell.name)) {
			return false;
		}
		// Math.sqrt((x2 鈭� x1)^2 + (y2 鈭� y1)^2)
		double centre_x1 = this.x + 50;
		double centre_y1 = this.y + 50;
		double centre_x2 = cell.x + 50;
		double centre_y2 = cell.y + 50;
		double distance = Math.sqrt(Math.pow((centre_x1 - centre_x2), 2) + Math.pow((centre_y1 - centre_y2), 2));
		return distance < 85;

	}

	public void Draw(Graphics bbg, JPanel jpanel) {
		Player player = currentLv;
		bbg.drawImage(player.getImage(), (int) x, (int) y, (int) size, (int) size, jpanel);
		bbg.setColor(Color.BLACK);
		bbg.drawString(name, ((int) x + (int) size / 2 - name.length() * 3),
				((int) y + (int) size / 2 + name.length())-60);
		// draw the hp bar but n
		// bbg.drawString(currentHp + "/ " + player.getHealth(), (int) x, (int) y - 20);
		bbg.drawRect((int) x, (int) y + 100, 100, 10);
		bbg.setColor(Color.RED);
		bbg.fillRect((int) x, (int) y + 100, (int) (((float) currentHp / player.getHealth()) * 100), 10);
		bbg.setColor(Color.BLACK);
		bbg.drawString("HP:", ((int) x - 30), (int) y + 110);

		// draw the exp bar but without scaled.
		bbg.drawRect((int) x, (int) y + 120, 100, 10);
		bbg.setColor(Color.YELLOW);
		bbg.fillRect((int) x, (int) y + 120, (int) (((float) currentExp / player.getExp()) * 100), 10);
		bbg.setColor(Color.BLACK);
		bbg.drawString("Exp:", ((int) x - 30), (int) y + 130);
	}

}
