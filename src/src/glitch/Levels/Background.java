package src.glitch.Levels;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Background {

	private BufferedImage image;
	private boolean willRain;
	private RainParticle[] rain;
	
	public Background(String fileName, boolean willRain){
		this.willRain = willRain;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(willRain) {
		rain = new RainParticle[150];
		for(int i = 0; i < rain.length; i++) {
			rain[i] = new RainParticle();
		}
		}
	}
	
	public void tick() {
		if(willRain) {
			for(int i = 0; i < rain.length; i++) {
				rain[i].tick();
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(image,0,0,null);
	}
	
	public void renderRain(Graphics g) {
		if(willRain) {
			for(int i = 0; i < rain.length; i++) {
				rain[i].render(g);
			}
		}
		g.setColor(Color.black);
		//g.drawString("raindSize: " + rain.length, 20, 20);
	}
	
}
