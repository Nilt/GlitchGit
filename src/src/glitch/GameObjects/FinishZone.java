package src.glitch.GameObjects;
import src.glitch.GameObjects.PlayerObjects.Player;
import src.glitch.Levels.Level;

import java.awt.Color;
import java.awt.Graphics;


public class FinishZone extends GroundObject {

	private Level level;
	private Player player;
	
	public FinishZone(int x, int y, int width, int height, Level level, Player player) {
		super(x, y, width, height);
		this.level = level;
		this.player = player;
		this.setCollidable(false);
	}
	
	public void tick() {
		if(super.getBounds().intersects(player.getBounds())) {
			level.setFinished();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}

}
