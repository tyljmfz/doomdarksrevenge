package Entity;

import javax.swing.ImageIcon;

public class Elephant extends Player {
	public Elephant() {
		super.health = 1000;
		super.exp = 3000;
		super.attack = 100;
		super.img = "/Resource/animals/elephant.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img));
		super.image = ii.getImage();
		super.level =7;
		super.addUpExp = 400;
	}
}
