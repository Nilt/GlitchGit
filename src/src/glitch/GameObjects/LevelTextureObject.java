package src.glitch.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LevelTextureObject {
	
	private int x,y;
	private BufferedImage img;

	public LevelTextureObject(int x, int y, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		//System.out.println(this.img);
		//System.out.println("x : " + x + " y:"+y);
	}
	
	public void render(Graphics g) {
		g.drawImage(img,x,y,null);
		//g.setColor(Color.red);
		//g.fillRect(x, y, 16, 16);
	}
	
}
