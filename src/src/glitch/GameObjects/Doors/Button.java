package src.glitch.GameObjects.Doors;
import src.glitch.Controllers.Physics;
import src.glitch.GameObjects.GroundObject;
import src.glitch.GameObjects.PlayerObjects.Player;
import src.glitch.Levels.Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Button extends GroundObject{
	
	private Level level;
	private int doorActive;


	public Button(int x, int y, int width, int height, Level level) {
		super(x, y, width, height);
		super.setCollidable(false);
		this.level = level;
		doorActive = 0;

		// TODO Auto-generated constructor stub

	}
	
	public void tick() {

		
		Rectangle[] bounds = level.getGlitchBounds();
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i] != null) {
				if (super.getBounds().intersects(bounds[i])) {
					doorActive = 1;
					break;
				}else if (!super.getBounds().intersects(bounds[i])) {
					doorActive = 0;
				}
			}
		}
				
	}

	
	public Rectangle getFootBox() {
		return new Rectangle(getX() + 40, getY() + getHeight(), 20, 20);
	}
	

	public void render(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());

	}
	
	public int getActive() {
		return doorActive;
	}
	
}