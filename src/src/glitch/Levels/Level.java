package src.glitch.Levels;

import src.glitch.Controllers.Game;
import src.glitch.Controllers.GameController;
import src.glitch.Controllers.Physics;
import src.glitch.GameObjects.FinishZone;
import src.glitch.GameObjects.GroundObject;
import src.glitch.GameObjects.LevelTextureObject;
import src.glitch.GameObjects.Doors.Button;
import src.glitch.GameObjects.PlayerObjects.GlitchObject;
import src.glitch.GameObjects.PlayerObjects.GlitchShadow;
import src.glitch.GameObjects.PlayerObjects.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Level {

	private GameController gc;
	private LevelGenerator LG;
	private String level;
	private Player player;
	private Physics ph;

	// Level State
	private boolean stopMovement;
	private boolean finished;

	// List will contain all the floor and collision elements in level
	ArrayList<GroundObject> levelFloor;
	ArrayList<LevelTextureObject> levelTexture;
	public static ArrayList<Button> levelButtons;

	private boolean isRendered = true;
	private int amountOfButtons = 0; // Will get increased but buttons, need to
										// check if sum of buttons equals to
										// unlock the doors.
	int buttonsActive = 0;
	int finishCoverAlpha = 0;
	Color finishCover = new Color(0,0,0,finishCoverAlpha);
	boolean fadeFinish = false;

	// Background image
	private static int bgIndex;
	private Background bg;

	public Level(GameController gc, String level) {
		this.gc = gc;
		this.level = level;
		levelFloor = new ArrayList<GroundObject>();
		levelTexture = new ArrayList<LevelTextureObject>();
		levelButtons = new ArrayList<Button>();

		LG = new LevelGenerator(this, this.level);
		bgIndex = LG.getBGIndex();
		bg = new Background("Assets/BG/BG" + (bgIndex) + ".png", LG.getWillRain());
		//bg = new Background("BG3.png");
		addComponents();
		stopMovement = false;
		finished = false;
		
		
	}

	public void addComponents() {
		player = new Player(LG.getPX(), LG.getPY(), gc);
		levelFloor.add(new FinishZone(LG.getFX(), LG.getFY(), 16, 16, this,
				player));
		ph = new Physics(player, levelFloor);
	}

	public void tick() {
		if(fadeFinish) {
			if(finishCoverAlpha < 254) {
				finishCoverAlpha += 2;
				finishCover = new Color(0,0,0,finishCoverAlpha);
			}
			if(finishCoverAlpha >= 253) {
				finished = true;
			}
		}
		if (!stopMovement) {
			for (int i = 0; i < levelFloor.size(); i++) {
				levelFloor.get(i).tick();
			}
			if (player != null) {
				player.tick();
				ph.tick();
			}
		}
		bg.tick();
		buttonsActive = 0;
		for (int i = 0; i < levelButtons.size(); i++) {
			buttonsActive += levelButtons.get(i).getActive();
		}
	}

	public void render(Graphics g) {
		bg.render(g);
		renderGround(g);

		if (player != null)
			player.render(g);

		renderTexture(g);
		renderFinishFade(g);
		
		bg.renderRain(g);
		// g.drawString("Finished : " + finished, 20, 25);
		//g.drawString("LevelButtsons Size : " + levelButtons.size(), 20, 80);
		//g.drawString("buttonsActive : " + buttonsActive, 20, 20);
		//g.drawString("Amount of Textures: " + levelTexture.size(), 20, 50);
		g.setColor(Color.white);
		//g.drawString("finished: " + finished, 20,20);
		//g.drawString("alpha : " + finishCoverAlpha, 20, 40);
	}

	/*
	 * Render the floor
	 */

	public void renderGround(Graphics g) {
		for (int i = 0; i < levelFloor.size(); i++) {
			if (levelFloor.get(i) != null)
				levelFloor.get(i).render(g);
		}
	}
	
	public void renderTexture(Graphics g) {
		for (int i = 0; i < levelTexture.size(); i++) {
			if (levelTexture.get(i) != null)
				levelTexture.get(i).render(g);
		}
	}
	
	public void renderFinishFade(Graphics g) {
		g.setColor(finishCover);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}

	public ArrayList<GroundObject> getGroundList() {
		return levelFloor;
	}

	public void generateGlitch(int x, int y, int dir) {
		for(int i =  0; i < levelFloor.size(); i++) {
			if(levelFloor.get(i) instanceof GlitchObject) {
				levelFloor.remove(i);
			}
		}
		levelFloor.add(new GlitchObject(x, y, 16, 16, this, dir));
	}

	public void generateGlitchShadow(ArrayList<Point> pointList,
			ArrayList<Integer> imageIndexList) {
		for(int i =  0; i < levelFloor.size(); i++) {
			if(levelFloor.get(i) instanceof GlitchShadow) {
				levelFloor.remove(i);
			}
		}
		levelFloor.add(new GlitchShadow(pointList.get(0).getX(), pointList.get(
				0).getY(), 16, 16, pointList, imageIndexList, this));
	}

	public void removeObject(GroundObject obj) {
		levelFloor.remove(obj);
	}

	// Returns bounds for Player, and the two different gliteches.
	public Rectangle[] getGlitchBounds() {
		Rectangle[] list = new Rectangle[3];

		for (int i = 0; i < levelFloor.size(); i++) {
			if (levelFloor.get(i) instanceof GlitchObject) {
				list[1] = levelFloor.get(i).getBounds();
			}
			if (levelFloor.get(i) instanceof GlitchShadow) {
				list[2] = levelFloor.get(i).getBounds();
			}
		}
		list[0] = player.getBounds();
		return list;
	}

	public void setFinished() {
		/*if (finished != true) {
			finished = true;
		}*/
		if(!fadeFinish) {
			fadeFinish = true;
			stopMovement = true;
		}
	}
	
	public boolean getFinished() {
		return finished;
	}

	public void addButton() {
		amountOfButtons++;
	}

	public int getAmountOfButtons() {
		return amountOfButtons;
	}

}
