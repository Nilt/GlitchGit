package src.glitch.Controllers;
import src.glitch.Audio.AudioController;
import src.glitch.Audio.AudioUnit;
import src.glitch.Menu.GameMenu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	// Versjon 0.0.1
	
	public static final String TITLE = "Glitch";

	public static final int WIDTH = 606;  //598
	public static final int HEIGHT = 397; //

	public boolean running;
	private Thread thread;
	private int fps;
	
	public static boolean recording;
	BufferedImage recordImage[];
	public int recordIndex;
	
	// Noise filter
	private static BufferedImage[] noiseImageFilter;
	private static int noiseImageFilterCounter = 0;
	private static int noiseImageFilterRate = 0;

	// Keyboard variables
	private static boolean left, right, up, down, enter, s, space, shift;

	//private Level level1;
	private GameController gc;
	private AudioController ac;
	private GameMenu gameMenu;
	
	//game state
	public static String gameState;

	public void init() {
		requestFocus();
		addKeyListener(this);

		left = false;
		right = false;
		
		gameState = "menu";
		
		recordIndex = 0;
		recording = false;
		recordImage = new BufferedImage[3];
		noiseImageFilter = new BufferedImage[3];
		try {
			recordImage[0] = ImageIO.read(new File("RecT_2.png"));
			recordImage[1] = ImageIO.read(new File("RecT_3.png"));
			recordImage[2] = ImageIO.read(new File("RecT_4.png"));
			
			for(int i = 0 ; i< noiseImageFilter.length; i++) {
				noiseImageFilter[i] = ImageIO.read(new File("Assets/Images/NoiseFilter/noisefilter" + (i + 1) + ".png"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gc = new GameController(this);
	    ac = new AudioController();
	    gameMenu = new GameMenu();
	    
	}

	public void tick() {
		switch(gameState) {
		case "menu":
			gameMenu.tick();
			break;
		case "running":
			if(gameMenu != null) {
				gameMenu = null;
			}
			gc.tick();
			break;
		case "pause":
			
			break;
		}
		
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// ////
		// TEGNING SKJER HER
		// ///
		
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		switch(gameState) {
		case "menu":
			gameMenu.render(g);
			break;
		case "running":
			gc.render(g);
			if(recording) {
				g.setColor(new Color(140,140,140,80));
				g.fillRect(0, 0, WIDTH, HEIGHT);
				g.drawImage(recordImage[getIndex()],0,0,null);
			}
			renderNoiseFilter(g);
			break;
		case "paused":
				gc.render(g);
				g.setColor(new Color(80,80,200, 120));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			break;
		}
		
		g.setColor(Color.black);
		//g.drawString("state: " + gameState, 20, 20);
		
		// g.drawString("FPS:" + fps, 10, 10);
		// ////
		g.dispose();
		bs.show();
	}
	
	public void renderNoiseFilter(Graphics g) {
		noiseImageFilterRate++;
		if(noiseImageFilterRate > 400) { // 777
			noiseImageFilterCounter++;
			noiseImageFilterRate = 0;
			
		}
		if(noiseImageFilterCounter > 2) noiseImageFilterCounter = 0;
		g.drawImage(noiseImageFilter[noiseImageFilterCounter], 0, 0, null);
		
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; // 60
		double ns = 1000000000 / amountOfTicks; // default value 1000000000
		double delta = 0;
		int updates = 0;
		//int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			// game loop here
			render();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta > 1) {
				tick();
				updates++;
				delta--;
			}

			// render();
			//frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ticks, FPS " + frames);
				fps = updates;
				updates = 0;
				//frames = 0;
			}
		}
		stop();
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();

		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);

		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (key == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (key == KeyEvent.VK_UP) {
			up = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (key == KeyEvent.VK_SHIFT) {
			shift = true;
		}
		if (key == KeyEvent.VK_CONTROL) {
			s = true;
		}
		if (key == KeyEvent.VK_SPACE) {
			space = true;
		}
		if (key == KeyEvent.VK_ENTER) {
			enter = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			up = false;
			switch(gameState) {
			case "menu":
				gameMenu.swapState();
				//ac.bgMusicIntro.playOnce();
				ac.playMusic();
				break;
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (key == KeyEvent.VK_SHIFT) {
			shift = false;
		}
		if (key == KeyEvent.VK_CONTROL) {
			s = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			// player.moveRight();
			right = false;
		}
		if (key == KeyEvent.VK_LEFT) {
			// player.moveLeft();
			left = false;
		}
		if (key == KeyEvent.VK_SPACE) {
			// player.moveLeft();
			space = false;
		}
		if (key == KeyEvent.VK_ENTER) {
			enter = false;
		}
		if(key == KeyEvent.VK_P) {
			switch(gameState) {
			case "menu":
				gameState = "running";
				ac.playMusic();
				break;
			case "running":
				gameState = "paused";
				ac.pause();
				ac.pauseMusic(true);
				break;
			case "paused":
				gameState = "running";
				ac.pauseMusic(false);
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//int key = e.getKeyCode();

	}

	public static boolean getLeft() {
		return left;
	}

	public static boolean getRight() {
		return right;
	}

	public static boolean getUp() {
		return up;
	}

	public static boolean getDown() {
		return down;
	}

	public static boolean getEnter() {
		return enter;
	}

	public static boolean getS() {
		return s;
	}

	public static boolean getShift() {
		return shift;
	}
	
	public static boolean getSpace() {
		return space;
	}

	// Gets reset when player hits the ground. From Physics
	public static void setUp(boolean b) {
		up = b;
	}
	
	public static void setRecording(boolean b) {
		recording = b;
	}
	
	public int getIndex() {
		recordIndex++;
		if(recordIndex >= recordImage.length -1) {
			recordIndex = 0;
		}
		return recordIndex;
	}

}
