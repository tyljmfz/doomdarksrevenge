package Local;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;


public class LeaderboardTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLeaderboard() {
		Leaderboard l=new Leaderboard();
	}


	@Test
	public void testDraw() {
		BufferedImage backBuffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics bbg= backBuffer.getGraphics();
		bbg.setColor(Color.RED);
		bbg.drawRect(10, 20, 125, 30);
		bbg.fillRect(15, 47, 125, 30);
		
	}

}
