package src.glitch.GameObjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Ground extends GroundObject{
	
	private boolean touched;
	private BufferedImage image;
	private BufferedImage[] imageList;
	private Random r;
	private int imageIndex;

	public Ground(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		super.setCollidable(true);
		try {
			image = ImageIO.read(new File("Ground3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageList = new BufferedImage[image.getWidth() / 16];
		//System.out.println(imageList.length);
		
		int length = image.getWidth() / 16;
		int offX = 0;
		int offY = 0;
		for(int i = 0; i < length; i++) {
			imageList[i] = image.getSubimage(offX, offY, 16, 16);
			offX += 16;
		}
		r = new Random();
		imageIndex = r.nextInt(length);
		
	}
	

	

	public void tick() {

	}

	public void render(Graphics g) {
		/*if(!touched){
			g.setColor(Color.red);
		}	
		else if(touched) {
			g.setColor(Color.green);
		}
		g.fillRect(getX(), getY(), getWidth(),
				getHeight());
				*/
		//g.drawImage(imageList[imageIndex], getX(), getY(), null);
	}

	public void setTouched(boolean b) {
		touched = b;
	}
	
}
