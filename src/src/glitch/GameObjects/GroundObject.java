package src.glitch.GameObjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GroundObject {

	private int x, y, width, height;
	private boolean touched;
	private boolean collidable;
	private BufferedImage img;

	public GroundObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/*
	 * public void tick() {
	 * 
	 * }
	 */
	public void render(Graphics g) {
		if (!touched) {
			g.setColor(Color.red);
		} else if (touched) {
			g.setColor(Color.green);
		}
		//g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.drawImage(img, getX(), getY(), null);
	}

	/*
	 * GETTERS AND SETTERS
	 */

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public boolean getCollidable() {
		return collidable;
	}

	public void setCollidable(boolean b) {
		collidable = b;
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public void tick() {
		// TODO Auto-generated method stub

	}

	/*
	 * public void setTouched(boolean b) { touched = b; }
	 */
}
