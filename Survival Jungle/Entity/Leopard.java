package Entity;

import javax.swing.ImageIcon;

public class Leopard extends Player{
	public Leopard() {
		super.health = 300;
		super.exp = 1200;
		super.attack = 30;
		super.img = "/Resource/animals/leopard.png";
		ImageIcon ii = new ImageIcon(getClass().getResource(img));
		super.image = ii.getImage();
		super.level =4;
		super.addUpExp = 30;
		
	}
}
