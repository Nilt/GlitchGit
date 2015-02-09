package src.glitch.Controllers;
import src.glitch.Levels.Level;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

public class GameController {

	//private Game game;
	private Level level;
	//private Player player;
	//private Physics ph;
	private String[] levelsArea1; // = {"level1.png","level2.png","level3.png", "level4.png"};
	private ArrayList<String> listOfLevels = new ArrayList<String>();
	private int currentLevel = 0;

	public GameController(Game game) {
		getFiles();
		levelsArea1 = new String[listOfLevels.size()];
		for(int i  = 0; i < levelsArea1.length; i++) {
			levelsArea1[i] = listOfLevels.get(i);
		}
		//this.game = game;
		level = new Level(this, "Assets/Levels/" + levelsArea1[currentLevel]);
		
		// player = new Player(100,100,this);
		// ph = new Physics(player, level.getGroundList());
	}
	
	public void getFiles() {
		File folder = new File("Assets/Levels/");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        //System.out.println("File " + listOfFiles[i].getName());
		        listOfLevels.add(listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        //System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	}

	public void tick() {
		// ph.tick();
		if(!level.getFinished()) {
			level.tick();
		} else if(level.getFinished()) {
			level = null;
			if(currentLevel < levelsArea1.length -1) {
				currentLevel++;
			}
			level = new Level(this, "Assets/Levels/" + levelsArea1[currentLevel]);
			
		}
		// player.tick();
	}

	public void render(Graphics g) {
		level.render(g);
		// player.render(g);
	}

	public void addGlitch(int x, int y, int dir) {
		level.generateGlitch(x, y, dir);
	}
	
	public void addGlitchShadow(ArrayList<Point> pointList, ArrayList<Integer> imageIndexList) {
		level.generateGlitchShadow(pointList, imageIndexList);
	}

}
