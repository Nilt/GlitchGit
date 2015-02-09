package src.glitch.Levels;

import src.glitch.Controllers.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class RainParticle {

	private double x,y;
	private Random r;
	private double speed;
	private int length;
	
	public RainParticle() {
		r = new Random();
		x = (double)r.nextInt(Game.WIDTH);
		y = 0 - r.nextInt(300);
		length = r.nextInt(5);
		speed = r.nextDouble() +3;
	}
	
	public void tick() {
		y += speed;
		x -= speed / 3;
		if(y > Game.HEIGHT) {
			y = -10;
			length = r.nextInt(5);
			speed = r.nextDouble() +3;
		}
		if(x < 0) {
			x = Game.WIDTH+ 10;
			length = r.nextInt(5);
			speed = r.nextDouble() +5;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(255,255,255, 80));
		g.drawLine((int)x, (int)y, (int)(x-length * speed *0.7), (int)(y+length * 2 * speed));
	}
	
}
