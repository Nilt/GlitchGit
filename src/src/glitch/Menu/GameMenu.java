package src.glitch.Menu;

import src.glitch.Controllers.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class GameMenu {
	
	private BufferedImage[] logo;
	private BufferedImage[] pressSpace;
	private int index;
	Random r = new Random();
	private boolean swapState = false;
	private int alpha = 255;
	private Color color;

	public GameMenu() {
		logo = new BufferedImage[2];
		pressSpace = new BufferedImage[2];
		color = new Color(255,255,255,alpha);
		try {
			logo[0] = ImageIO.read(new File("Assets/Images/glitchLogo3.png"));
			logo[1] = ImageIO.read(new File("Assets/Images/glitchLogo3_2.png"));
			pressSpace[0] = ImageIO.read(new File("Assets/Images/pressX.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void tick() {
		int i = r.nextInt(30);
		if(i == 1) {
			index = 0;
		} else index = 1;
		if(swapState) {
			if(alpha >= 1) {
				alpha -= 15;
				color = new Color(255,255,255,alpha);
			}
			if(alpha <= 50) {
				Game.gameState = "running";
				
			}
		}
	}
	
	public void render(Graphics g) {
		if(!swapState)
		g.drawImage(logo[index], Game.WIDTH / 2 - logo[index].getWidth() / 2, Game.HEIGHT / 2 - 50, null);
		g.drawImage(pressSpace[0], Game.WIDTH / 2 - pressSpace[0].getWidth() / 2 , Game.HEIGHT / 2 + 30, null);
		
		// transition
		g.setColor(color);
		//g.drawString("Swap: " + swapState, 20, 20);
		if(swapState) 
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
	public void swapState() {
		swapState = true;
	}
	
}
