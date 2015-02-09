package src.glitch.GameObjects.PlayerObjects;
import src.glitch.Audio.AudioController;
import src.glitch.Controllers.Game;
import src.glitch.Controllers.GameController;
import src.glitch.SpriteClasses.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

public class Player {

	private boolean renderCons = false;
	
	public static int glitchCounter = 1;
	
	

	private GameController gc;
	private double x, y, width, height;
	private final double STARTX,STARTY;	// The start position for reseting purposes
	private final int hitBoxPad = 1;   	// How far out on each side the hitbox reaches
	
	// Variables that deals with movement
	private double yVel, xVel, friction, accel, maxVel;
	private boolean hasGravity = true;
	private boolean grounded;
	private int jumpPower = 5;
	private int jumpTimer = 30;
	private boolean canJump= true;

	
	// Variables for creating shadow
	public static ArrayList<Point> pointList;		//Stores coordinates for shadow movement
	public static ArrayList<Integer> imageIndexList;

	private int recordShadow;   					//Defines when to start recording
	private int canSwapRecord;						//Timer controls how fast the player can swap
	public static boolean canRecord;
	public static boolean shadowCanRecord;
	private int maxRecordLength = 100; // 100
	
	// Player Image
	private String imageFile = "GlitchSprite.png";
	private SpriteSheet sprite;
	private int dir; // Decides which image to load

	public Player(double x, double y, GameController gc) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		
		STARTX = this.x;
		STARTY = this.y;
		
		width = 16;
		height = 16;
		
		// Loading image
		sprite = new SpriteSheet(imageFile);
		dir = 1;
		
		// List used for recording shadow
		pointList = new ArrayList<Point>();
		imageIndexList = new ArrayList<Integer>();


		// Movement variables
		maxVel = 5;
		friction = 0.2;
		accel = 0.2;

		// Record variables
		recordShadow = 0;
		canSwapRecord = 50;
		canRecord = true;
		shadowCanRecord = true;
		
		
	}

	public void tick() {
		keyBoard();
		setDirection();
		keyReleasedFriction();
		//moveGroundedBox();
		outSide();
		glitch();
		recordList();
	}

	/*
	 * Checks if keyboard is pressed in Game.java If keyboard is pressed, check
	 * if velocity is opposite of target velcity. If so, set xVel to 0 and start
	 * to decrease in the right direction.
	 */
	public void keyBoard() {
		jump();
		
		if(Game.getEnter()) {
			reset();
		}
		
		if (Game.getLeft() && xVel > (maxVel * -1)) {
			if (xVel > 0) {
				xVel = 0;
			}
			xVel -= accel;
		} else if (Game.getRight() && xVel < maxVel) {
			if (xVel < 0) {
				xVel = 0;
			}
			xVel += accel;
		}

		x += xVel;
		y += yVel;
	}
	
	/*
	 * JUMP
	 */
	
	public void jump() {
		jumpTimer--;
		if(jumpTimer <= 0) {
			canJump = true;
			
		}
		
		if(Game.getUp() && grounded && canJump) {
			//setY(getY() - 10);
			AudioController.jump();
			yVel = -jumpPower;
			grounded = false;
			hasGravity = true;
			canJump = false;
			jumpTimer = 30;
			
		}
		
	}
	
	/*
	 *  GLITCH
	 */
	
	public void glitch() {
		//if(Game.getSpace() && glitchCounter > 0) {
		if(Game.getSpace()) {
			glitchCounter -= 1;
			gc.addGlitch((int)x, (int)y, getDirection());
		} 
	}
	
	/*
	 * OUT OF BOUNDS
	 */
	
	public void outSide() {
		if(getX() + getWidth() < 0) {
			setX(Game.WIDTH);
		}
		if(getX() > Game.WIDTH) {
			setX(0-getWidth());
		}
		if(getY() + getHeight() < 0) {
			//setY(Game.HEIGHT);
		}
		if(getY() > Game.HEIGHT) {
			//setY(0-getHeight());
			reset();
		}
	}

	/*
	 * Checks if keyboard is released in Game.java If it is released and xVel is
	 * bigger than 0. Start to decrease it with friction
	 */

	public void keyReleasedFriction() {
		if (!Game.getLeft() && xVel < 0) {
			xVel += friction;
			if (xVel > 0)
				xVel = 0;
		}
		if (!Game.getRight() && xVel > 0) {
			xVel -= friction;
			if (xVel < 0) {
				xVel = 0;
			}
		}
	}
	
	/*
	 * Record list
	 */
	
	public void recordList() {
		canSwapRecord--;
		if(Game.getS() && canSwapRecord <= 0 && canRecord) {
			
			switch(recordShadow) {
			case 0: // start
				recordShadow = 1;
				//AudioController.rec.play();
				
				Game.setRecording(true);
				canSwapRecord = 50;
				break;
			case 1: // slutt
				recordShadow = 0;
				//AudioController.rec.stop();
				AudioController.bgMusic.resetVolume();
				canSwapRecord = 10;
				canRecord = false;
				Game.setRecording(false);
				gc.addGlitchShadow(pointList,imageIndexList);
				//pointList.clear();
				break;
			}
		}
		if(recordShadow == 1) {
			pointList.add(new Point((int)x,(int)y));
			imageIndexList.add(getDirection());
			if(pointList.size() > maxRecordLength) {
				AudioController.bgMusic.resetVolume();
				Game.setRecording(false);
				recordShadow = 0;
				gc.addGlitchShadow(pointList,imageIndexList);
				canRecord = false;
			}
		}
	}

	/*
	 * RENDER
	 */

	public void render(Graphics g) {
		//g.setColor(Color.white);
		//g.fillRect((int) x, (int) y, (int) width, (int) height);

		drawPlayer(g);
		//g.setColor(Color.green);
		//g.fillRect((int)gx,(int)gy,(int)gx2,(int)gy2);
		sprite.draw(g);
		if (renderCons)
			renderConsole(g);
	}
	
	/*
	 * DRAWIMAGE
	 */
	
	public void drawPlayer(Graphics g) {
		g.drawImage(sprite.getImage(getDirection()), (int)x, (int)y, null);
	}

	// Render console
	public void renderConsole(Graphics g) {

		g.drawString("xVel: " + xVel, 20, 20);
		g.drawString("yVel: " + yVel, 20, 40);
		g.drawString("GAME.Left : " + Game.getLeft(), 20, 60);
		g.drawString("GAME.Right: " + Game.getRight(), 20, 80);
		g.drawString("Grounded : " + grounded, 20, 100);
		g.drawString("HasGrav  :" + hasGravity, 20, 120);
		g.drawString("RecordShadow  :" + recordShadow, 20, 140);
		g.drawString("CanSwapRec: " + canSwapRecord, 20, 160);
		g.drawString("Dir : " + getDirection(), 20, 180);
		
		g.setColor(Color.blue);
		drawBox(g, getLeftBox());
		drawBox(g, getRightBox());
		drawBox(g, getHeadBox());
		drawBox(g, getFootBox());
	}
	
	public void drawBox(Graphics g, Rectangle box) {
		g.fillRect((int)box.getX(),(int)box.getY(),(int)box.getWidth(),(int)box.getHeight());
	}

	
	
	/*
	 * GET DIRECTION
	 * Follows the "clock" from where the player looks.
	 * 0 = LeftDown
	 * 1 = Left
	 * 2 = LeftUp
	 * 3 = RightUp
	 * 4 = Right
	 * 5 = RightDown
	 */
	
	public void setDirection() {
		//int dir = 0;
		
		if(getxVel() == 0) {
			String d = "";
			switch(getDirection()) {
			case 0:
				d = "L";
			case 1:
				d = "L";
				break;
			case 2:
				d = "L";
				break;
			case 3:
				d = "R";
				break;	
			case 4:
				d = "R";
				break;
			case 5:
				d = "R";
				break;
			}
			if(d.equals("L")) {
				double y = getyVel();
				if(y == 0) {
					dir = 1; // LEFT
				} if(y < 0) {
					dir = 2; // LEFTUP
				} if(y > 0) {
					dir = 0; // LEFTDOWN
				}
			}
			if(d.equals("R")) {
				double y = getyVel();
				if(y == 0) {
					dir = 4; // RIGHT
				} if(y < 0) {
					dir = 3; // RIGHT UP
				} if(y > 0) {
					dir = 5; // RIGHT DOWN
				}
			}
		}
		
		// Left
		if(getxVel() < 0) {
			double y = getyVel();
			if(y == 0) {
				dir = 1; // LEFT
			} if(y < 0) {
				dir = 2; // LEFTUP
			} if(y > 0) {
				dir = 0; // LEFTDOWN
			}
		}
		//Right
		if(getxVel() > 0) {
			double y = getyVel();
			if(y == 0) {
				dir = 4; // RIGHT
			} if(y < 0) {
				dir = 3; // RIGHT UP
			} if(y > 0) {
				dir = 5; // RIGHT DOWN
			}
		}
		
		
	}
	
	/*
	 * GETTERS AND SETTERS
	 */

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	public double getxVel() {
		return xVel;
	}

	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	public void setyVelGravity(double gravity) {
		this.yVel += gravity;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), (int) getWidth(),
				(int) getHeight());
	}

	public boolean getHasGravity() {
		return hasGravity;
	}

	public void setHasGravity(boolean b) {
		hasGravity = b;
	}

	public boolean getGrounded() {
		return grounded;
	}

	public void setGrounded(boolean b) {
		grounded = b;
	}
	
	//public Rectangle getGroundBox() {
	//	return groundedBox;
	//}
	
	public Rectangle getFootBox() {
		return new Rectangle((int)getX() + 4, (int)(getY() + getHeight()), (int)8,(int) hitBoxPad);
	}
	
	public Rectangle getLeftBox() {
		return new Rectangle((int)(getX() -hitBoxPad), (int)getY()+4, (int)hitBoxPad,(int) 8);
	}
	
	public Rectangle getRightBox() {
		return new Rectangle((int)(getX() + getWidth() ), (int)getY() +4 , (int)hitBoxPad,(int) 8);
	}
	
	public Rectangle getHeadBox() {
		return new Rectangle((int)getX() +4 , (int)(getY()-hitBoxPad), (int)8,(int) hitBoxPad);
	}

	public int getDirection() {
		return dir;
	}
	
	/*
	 * RESET POSITION AND LEVEL
	 */
	
	public void reset() {
		setX(STARTX);
		setY(STARTY);
		pointList.clear();
		imageIndexList.clear();
		Game.setRecording(false);
		recordShadow = 0;
	}
	
	
}
