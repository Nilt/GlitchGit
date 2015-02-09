package src.glitch.SpriteClasses;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {

	BufferedImage img;
	BufferedImage[] imgList;
	
	public SpriteSheet(String fileName) {
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(img == null) {
			System.out.println("Err");
		}
		int width = img.getWidth() / 16;

		imgList = new BufferedImage[width];
		int offX = 0;
		for(int i = 0; i < imgList.length; i++) {
			imgList[i] = img.getSubimage(offX, 0, 16, 16);
			offX += 16;
		}
	
	}
	
	public void draw(Graphics g) {
		//g.drawImage(imgList[0][0], 50, 50, null);
	}
	
	public BufferedImage getImage(int i) {
		return imgList[i];
	}
	
}
