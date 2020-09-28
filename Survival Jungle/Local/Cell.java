package Local;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Audio.Audio_player;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author 
 *
 */
public class Cell {

	public static ArrayList<Cell> cells = new ArrayList<Cell>();
	public static ArrayList<Player> level = new ArrayList<Player>();
	public static int cellCount;

	public Player currentLv; // Type: Player --> get the current Level animal.
	public int levelNum = 0; // Type : int --> change while level up / down.
	public String name; // player name
	double radius = 50; // the radius of the animals
	int currentExp; // instant experience
	public int currentHp; // instant health value
	int size = 100;
	int speed = 1;
	boolean isPlayer = false;
	int centre_x;
	int centre_y;
	int totalExp;
	public int index=0;
	Cell target; // AI use..
	Particle pTarget; // AI use..

	boolean isTarget = false;
	String targetType = "p"; // to determine it is food or not.

	public double x; // each cell x-coordinate
	public double y; // each cell y-coordinate

	double goalX, goalY; // target x-coordinate.
	boolean goalReached = true;

	double colX, colY;
	boolean colRached = false;
	int colCount = 0;
	Cell colCell;

	private HashMap<String, Audio_player> sfx;

	
	public Cell(String name, int x, int y, boolean isPlayer) {
		initLevel();
		this.name = name;
		this.x = x;
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

	public void addExp(int exp, Cell cell) {
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
//	Test!!!!!!!!!!!!!!!!!!!!!!!
	public void reduceHp(double hp, Cell cell) {
		int index=cell.currentLv.getHealth();
//		cell.currentHp--;
//		if(cell.currentHp>0) {
		cell.currentHp-=hp;
		
			if(cell.currentHp<=index-30) {
				cell.currentHp=index-30;
			}
			if(cell.currentHp==0) {
				die(cell,this);
			}
//		}
	}
	public void levelUp(Cell cell) {
		if (cell.levelNum == 7) {
			cell.currentLv = level.get(7); // the highest level is 7, prevent out bound.
		} else {
			cell.currentLv = level.get(++levelNum); 
			cell.currentExp = 0;
			cell.currentHp = cell.currentLv.getHealth();
			//play audio
			sfx.get("exup").play();
		}
	}

	public void levelUp_m_eat_e(Cell cell) {
		cell.levelNum = 3;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentExp = 0;
		cell.currentHp = cell.currentLv.getHealth();
	}
	
	public int calTotalExp() {
		if(this.levelNum==0) {
			totalExp = this.currentExp;
		}else {
			int periousExp = level.get(levelNum-1).getExp();
			totalExp = this.currentExp + periousExp;
		}
		return totalExp;
	}
	
	public void die(Cell cell, Cell winner) {
		int expAdd = cell.currentExp + cell.currentLv.getAddUpExp();
		addExp(expAdd, winner);
		cell.levelNum = 0;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentHp = cell.currentLv.getHealth();
		cell.currentExp = 0;
		respawn(cell);
	}
	
	public void downgrade(Cell cell) {
		cell.levelNum = cell.levelNum - 1;
		cell.currentLv = level.get(cell.levelNum);
		cell.currentHp = cell.currentLv.getHealth();
		cell.currentExp = 0;
		respawn(cell);
		//play audio
		sfx.get("exdown").play();
	}

	public void Update() {
		if (this.currentExp >= this.currentLv.getExp()) {
			levelUp(this);
		}
		// check whether elephant or mouse..<-- special case
		for (Cell cell : cells) {
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

		if (!isPlayer) {
			if (colRached) {
				goalReached = false;
				collision();
			} else {
				if (goalReached) {
					if (returnNearestCell() > -1) {
						if (!isTarget) {
							target = cells.get(returnNearestCell());
							isTarget = true;
							targetType = "c";
						} else if (isTarget && targetType.equals("c")) {
							targetType = "n";
							isTarget = false;
						}
					} else if (returnNearestCell() == -1) {
						if (!isTarget) {
							pTarget = Particle.particles.get(returnNearestP());
							isTarget = true;
							targetType = "p";
						} else if (isTarget) {
							targetType = "n";
							isTarget = false;
						}
					}
					goalReached = false;
				} else {
					double dx = 0;
					double dy = 0;
					if (targetType.equals("c")) {
						if (returnNearestCell() > -1) {
							target = cells.get(returnNearestCell());
							dx = (target.x - this.x);
							dy = (target.y - this.y);
						} else {
							goalReached = true;
						}
					} else if (targetType.equals("p")) {
						pTarget = Particle.particles.get(returnNearestP());
						dx = (pTarget.x - this.x);
						dy = (pTarget.y - this.y);
					} else {
						goalReached = true;
					}
					double distance = Math.sqrt((dx) * (dx) + (dy) * (dy));
					if (distance > 1) {
						x += (dx) / distance * speed;
						y += (dy) / distance * speed;
					} else if (distance <= 1) {
						goalReached = true;
					}

				}
			}
		} else {
			if (colRached) {
				collision();
			} else {
				double dx = (goalX - this.x);
				double dy = (goalY - this.y);
				this.x += (dx) * 1 / 100;
				this.y += (dy) * 1 / 100;
			}
		}
	}

	public void getMouseX(int mx) {
		goalX = mx;
	}

	public void getMouseY(int my) {
		goalY = my;
	}
	

	public void boundsOut(Cell cell) {
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

	public int returnNearestCell() {

		int x = 0;
		int distance = 9999999;
		int min = distance;
		int max = 200;
		for (Cell cell : cells) {
			if (this != cell) {
				distance = (int) Math
						.sqrt((this.x - cell.x) * (this.x - cell.x) + (cell.y - this.y) * (cell.y - this.y));
				if (distance < min && distance <= max && this.levelNum > cell.levelNum) {
					x = cells.indexOf(cell);
				} else if (distance < min && this.levelNum < cell.levelNum) {
					x = -1;
				}
			}
		}
		return x;
	}

	public int returnNearestP() {

		int x = 0;
		int distance = 99999999;
		int min = distance;

		for (Particle p : Particle.particles) {
			distance = (int) Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
			if (distance < min) { // particle level
				min = distance;
				x = Particle.particles.indexOf(p);
			}
		}

		return x;
	}

	// the HP value is reached.
	public void respawn(Cell cell) {
		cell.x = (int) Math.floor(Math.random() * 10001);
		cell.y = (int) Math.floor(Math.random() * 10001);
		System.out.println("Respawn: " + cell.name);
		// this.currentLv = level.get(0);
	}

	// collision

	public boolean checkCollide(Cell cell) {
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
