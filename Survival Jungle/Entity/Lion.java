package Entity;

import javax.swing.ImageIcon;

public class Lion extends Player{
	public Lion() {
		super.health = 750;
		super.exp = 2400;
		super.attack = 75;
		super.img = "/Resource/animals/lion.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img));
		super.image = ii.getImage();
		super.level =6;
		super.addUpExp = 70;
	}
}
