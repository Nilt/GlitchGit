package src.glitch.GameObjects.PlayerObjects;

import java.awt.Color;
import java.awt.Graphics;

public class ShadowTrail {

	private float x,y,alpha;
	private Color color;
	private GlitchShadow glitchShadow;
	
	public ShadowTrail(double x, double y, GlitchShadow glitchShadow) {
		this.x = (float)x;
		this.y = (float)y;
		alpha = 255;
		color = new Color(34,177,76,(int) alpha);
		this.glitchShadow = glitchShadow;
	}
	
	public void tick() {
		alpha -= 10;
		if(alpha <= 5) {
			glitchShadow.removeShadow(this);
		}
		color = new Color(34,177,76,(int) alpha);
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x,(int) y, 16, 16);
	}
	
	
}
