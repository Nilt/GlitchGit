package src.glitch.GameObjects;
import src.glitch.GameObjects.PlayerObjects.Player;
import src.glitch.Levels.Level;
import src.glitch.SpriteClasses.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;


public class FinishZone extends GroundObject {

	private Level level;
	private Player player;
	
	// Image
		private String imageFile = "Assets/Images/LevelObjects/finish3_2.png";
		private SpriteSheet sprite;
		private int index; // Decides which image to load
		private int speed;
	
	public FinishZone(int x, int y, int width, int height, Level level, Player player) {
		super(x, y, width, height);
		this.level = level;
		this.player = player;
		this.setCollidable(false);
		
		sprite = new SpriteSheet(imageFile);
		index = 1;
		speed = 0;
	}
	
	public void tick() {
		speed++;
		if(speed % 5 == 0) {
		index++;
		if(index >= 12) {
			index = 0;
		}
		}
		
		if(super.getBounds().intersects(player.getBounds())) {
			level.setFinished();
		}
	}
	
	
	
	public void render(Graphics g) {
		//g.setColor(Color.MAGENTA);
		//g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
		g.drawImage(sprite.getImage(index),getX(), getY(), null);
	}

}
