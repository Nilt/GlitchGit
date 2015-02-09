package src.glitch.Levels;

import src.glitch.Controllers.GameController;
import src.glitch.Controllers.Physics;
import src.glitch.GameObjects.FinishZone;
import src.glitch.GameObjects.Ground;
import src.glitch.GameObjects.LevelTextureObject;
import src.glitch.GameObjects.Doors.Button;
import src.glitch.GameObjects.Doors.Door;
import src.glitch.GameObjects.PlayerObjects.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LevelGenerator {

	private Level level;
	private GameController gc;
	private Player player;
	private String fileName;
	private BufferedImage tileSet;
	private BufferedImage[][] tiles;
	private BufferedImage test;
	private int backGround = 0;

	// Position variables for the buttons
	// private int B1X, B1Y, B2X, B2Y, B3X, B3Y, FX, FY;
	private int PX, PY, FX, FY;
	
	private static boolean willRain;

	public LevelGenerator(Level level, String fileName) {
		this.level = level;
		this.fileName = fileName;
		willRain = false;

		try {
			tileSet = ImageIO.read(new File("tiles5.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tileWidth: " + tileSet.getWidth() / 16);
		System.out.println("tileWidth: " + tileSet.getHeight() / 16);
		tiles = new BufferedImage[tileSet.getWidth() / 16][tileSet.getHeight() / 16];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = this.tileSet.getSubimage(i * 16, j * 16, 16, 16);
			}
		}
		test = this.tileSet.getSubimage(0, 0, 16, 16);
		//System.out.println(test);
		
		
		generateImageLevel(fileName);
		addComponents();
	}


	public void addComponents() {
		player = new Player(PX, PY, gc);
	}

	public void generateImageLevel(String fileName) {
		BufferedImage img;
		System.out.println(fileName + "\n");
		try {
			img = ImageIO.read(new File(fileName));
			// img = convertToARGB(img);
			int width = img.getWidth();
			int height = img.getHeight();
			//System.out.println("Width: " + width + "| Height: " + height);
			// int counter = 0;
			int offX = -16;
			int offY = 0;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < 24; j++) {
					int clr = img.getRGB(i, j);
					Color color = new Color(clr, false);

					generateGroundObject(offX, offY, color);
					offY += 16;

					// counter++;
				}
				offY = 0;
				offX += 16;
				// System.out.println("------------------------------------------------------------");
			}
			offX = -16;
			for (int i = 0; i < width; i++) {
				for (int j = 23; j < 46; j++) {
					int clr = img.getRGB(i, j);
					Color color = new Color(clr, false);

					if(color.getRed()<100) {
					generateTextureObject(offX, offY, new Point(color.getRed(),
							color.getGreen()));
					}
					offY += 16;

					// counter++;
				}
				offY = 0;
				offX += 16;
				// System.out.println("------------------------------------------------------------");
			}
			
			for(int i = 0; i < width; i++) {
				for(int j = height; j <= height; j++) {
					int clr = img.getRGB(i, j-1);
					Color color = new Color(clr, false);
					if(color.equals(new Color(120,120,120))) {
						backGround++;
					}
					if(color.equals(new Color(0,255,255))) {
						willRain = true;
					}
					
				}
			}
			//System.out.println("bakground :" + backGround);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void generateGroundObject(int x, int y, Color color) {
		if (color.equals(Color.black)) {
			level.levelFloor.add(new Ground(x, y, 16, 16));
		}
		if (color.equals(Color.red)) {
			PX = x;
			PY = y;
		}
		if (color.equals(Color.blue)) {
			level.levelFloor.add(new Door(x, y, 16, 16, level));
		}
		if (color.equals(Color.green)) {
			Button temp = new Button(x, y, 16, 16, level);
			level.levelFloor.add(temp);
			level.levelButtons.add(temp);
			level.addButton();
		}
		if (color.equals(new Color(255, 0, 255))) {
			FX = x;
			FY = y;
		}
	}

	public void generateTextureObject(int x, int y, Point p) {
		//System.out.println("Point: " + p);
		//level.levelTexture.add(new LevelTextureObject(x, y, tileSet));
		//level.levelTexture.add(new LevelTextureObject(x, y, test));
		level.levelTexture.add(new LevelTextureObject(x, y, tiles[(int) p.getX()][(int) p.getY()]));
	}

	public static BufferedImage convertToARGB(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

	public Player getPlayer() {
		return player;
	}
	

	public int getFX() {
		return FX;
	}

	public void setFX(int fX) {
		FX = fX;
	}

	public int getFY() {
		return FY;
	}

	public void setFY(int fY) {
		FY = fY;
	}

	public int getPX() {
		return PX;
	}

	public void setPX(int pX) {
		PX = pX;
	}

	public int getPY() {
		return PY;
	}

	public void setPY(int pY) {
		PY = pY;
	}
	
	public int getBGIndex() {
		return backGround;
	}
	
	public boolean getWillRain() {
		return willRain;
	}
}
