package src.glitch.GameObjects.PlayerObjects;
import src.glitch.Controllers.Game;
import src.glitch.GameObjects.GroundObject;
import src.glitch.Levels.Level;
import src.glitch.SpriteClasses.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class GlitchShadow extends GroundObject {

	ArrayList<Point> pointList;
	ArrayList<Integer> imageIndexList;
	ArrayList<ShadowTrail> shadowList;
	private int pointListIndex;
	private int imageIndex;
	private int copyIndex;
	private double alpha;
	private boolean fade;

	private Level level;
	
	// Image
	private String imageFile = "GlitchSprite2.png";
	private SpriteSheet sprite;
	private int dir; // Decides which image to load

	public GlitchShadow(double x, double y, int width, int height,
			ArrayList<Point> pointList,ArrayList<Integer> imageIndexList,Level level) {
		super((int) x, (int) y, width, height);
		this.pointList = pointList;
		this.imageIndexList = imageIndexList;
		this.level = level;
		pointListIndex = 0;
		imageIndex = 0;
		copyIndex = 0;
		// printList();
		//printIntList();
		super.setCollidable(true);
		alpha = 255;
		fade = false;
		
		// Loading image
		sprite = new SpriteSheet(imageFile);
		
		
		shadowList = new ArrayList<ShadowTrail>();
	}

	/*
	 * Prints pointList
	 */
	public void printPointList() {
		System.out.println(pointListIndex);
		System.out.println(pointList.size());
		for (int i = 0; i < pointList.size(); i++) {
			System.out.println(pointList.get(i).toString());
		}
	}

	/*
	 *  PrintsInt List
	 */
	
	public void printIntList() {
		System.out.println(imageIndexList);
		System.out.println(imageIndexList);
		for (int i = 0; i < imageIndexList.size(); i++) {
			System.out.println(imageIndexList.get(i).toString());
		}
	}
	
	/*
	 * Tick
	 *
	 */
	
	public void tick() {
		if(Game.getShift() || Game.getEnter()) {
			delete();
		}
		addShadow();
		shadowTrailTick();
		if (pointListIndex < pointList.size()) {
			Point temp = pointList.get(pointListIndex);
			super.setX((int) temp.getX());
			super.setY((int) temp.getY());
			pointListIndex++;
			if (pointListIndex >= pointList.size()) {
				fade = true;
			}
		}
		if(imageIndex < imageIndexList.size()-1) {
			imageIndex++;
		}
		
		// System.out.println("WORKING");
		if (fade) {
			if (alpha > 40) {
				alpha--;
			}
			if (alpha <= 40) {
				delete();
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(190, 190, 0, (int) alpha));
		//g.fillRect((int) getX(), (int) getY(), 16, 16);
		renderShadowTrail(g);
		g.drawImage(sprite.getImage(imageIndexList.get(imageIndex)),getX(),getY(), null);

		//g.drawString("counter :" + pointListIndex, 20, 200);
	}
	
	/*
	 * Deletes object and clears lists from player
	 */
	
	public void delete() {
		shadowList.clear();
		level.removeObject(this);
		Player.canRecord = true;
		Player.pointList.clear();
		Player.imageIndexList.clear();
	}
	
	/*
	 * Shadow
	 */
	
	public void addShadow() {
		shadowList.add(new ShadowTrail(getX(), getY(), this));
	}
	
	public void removeShadow(ShadowTrail o) {
		shadowList.remove(o);
	}
	
	public void renderShadowTrail(Graphics g) {
		for(int i = 0; i < shadowList.size(); i++) {
			shadowList.get(i).render(g);
		}
	}
	
	public void shadowTrailTick() {
		for(int i = 0; i < shadowList.size(); i++) {
			shadowList.get(i).tick();
		}
	}
}

