package src.glitch.GameObjects.PlayerObjects;
import src.glitch.Controllers.Game;
import src.glitch.GameObjects.GroundObject;
import src.glitch.Levels.Level;
import src.glitch.SpriteClasses.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;

public class GlitchObject extends GroundObject {

	int timer = 300;
	int colTimer = 50;
	private Level level;

	// Images
	private String imageFile = "GlitchSprite2.png";
	private SpriteSheet sprite;
	private int dir; // Decides which image to load

	public GlitchObject(int x, int y, int width, int height, Level level, int dir) {
		super(x, y, width, height);
		super.setCollidable(false);
		this.level = level;
		this.dir = dir;
		sprite = new SpriteSheet(imageFile);
	}

	public void render(Graphics g) {

		g.setColor(Color.green);
		//g.drawString("timer : " + timer, 50, 50);
		//g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.drawImage(sprite.getImage(dir), getX(),getY(), null);
	}

	public void tick() {
		if(Game.getShift() || Game.getEnter()) {
			delete();
		}
		
		
		timer -= 1;
		colTimer--;
		if (colTimer <= 0) {
			super.setCollidable(true);
		}
		if (timer <= 0) {
			delete();
		}
	}

	public void delete() {
		Player.glitchCounter = 1;
		level.removeObject(this);
	}

	/*
	 * GETTERS AND SETTERS
	 */

	
	
}
