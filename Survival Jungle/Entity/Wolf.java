package Entity;

import javax.swing.ImageIcon;

public class Wolf extends Player{
	public Wolf() {
		super.health = 200;
		super.exp = 800;
		super.attack = 20;
		super.img = "/Resource/animals/wolf.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img));
		super.image = ii.getImage();
		super.level =3;
		super.addUpExp = 20;
	}
}
