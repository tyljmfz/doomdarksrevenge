package Entity;

import javax.swing.ImageIcon;

public class Tiger extends Player{
	public Tiger() {
		super.health = 500;
		super.exp = 1800;
		super.attack = 50;
		super.img = "/Resource/animals/tiger.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img));
		super.image = ii.getImage();
		super.level =5;
		super.addUpExp = 50;
	}
}
