package src.glitch.GameObjects.Doors;
import src.glitch.GameObjects.GroundObject;
import src.glitch.Levels.Level;
import src.glitch.SpriteClasses.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;


public class Door extends GroundObject{
	
	public static boolean button1, button2, button3;
	private Level level;
	
	// Image
	//private String imageFile = "laserDoor.png";
	private String imageFile = "Assets/Images/LevelObjects/laser10.png";
	
	private SpriteSheet sprite;
	private int index; // Decides which image to load
	private int speed;

	public Door(int x, int y, int width, int height, Level level) {
		super(x, y, width, height);
		this.level = level;
		super.setCollidable(true);
		
		sprite = new SpriteSheet(imageFile);
		index = 1;
		speed = 0;
	}

	public void tick() {
		speed++;
		if(speed % 5 == 0) {
		index++;
		if(index >= 3) {
			index = 0;
		}
		}

		int buttonsActive = 0;
		for(int i = 0; i < level.levelButtons.size(); i++) {
			buttonsActive += level.levelButtons.get(i).getActive();
		}
		if(level.getAmountOfButtons() == buttonsActive) {
			level.removeObject(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);

		g.setColor(Color.pink);

		g.drawImage(sprite.getImage(index),getX(), getY(), null);
	}
	
}
