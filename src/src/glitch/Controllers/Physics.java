package src.glitch.Controllers;
import src.glitch.GameObjects.GroundObject;
import src.glitch.GameObjects.PlayerObjects.Player;

import java.util.ArrayList;

public class Physics {

	private Player player;
	private static ArrayList<GroundObject> groundList;
	private double gravity = 0.2;
	private int padding = 1;

	public Physics(Player player, ArrayList<GroundObject> groundList) {
		this.player = player;
		this.groundList = groundList;
	}

	public void tick() {
		if (player != null) {
			if (player.getGrounded()) {
				player.setHasGravity(true);
			}
			if (player.getHasGravity()) {
				gravity();
			}

			// Tester collision under
			int boxCollisionGround = checkCollisionDown();
			if (boxCollisionGround >= 0) {
				player.setGrounded(true);
				player.setyVel(0);
				player.setHasGravity(false);
				player.setY(groundList.get(boxCollisionGround).getY()
						- player.getHeight());

			} else if (boxCollisionGround < 0) {
				player.setGrounded(false);
			}

			// Tester collision over
			int boxCollisionHead = checkCollisionHead();
			if (boxCollisionHead >= 0) {
				player.setyVel(0);
				player.setY(groundList.get(boxCollisionHead).getY()
						+ groundList.get(boxCollisionHead).getHeight()
						+ padding);
			}

			// Tester collision til vesntre
			int boxCollisionLeft = checkCollisionLeft();
			if (boxCollisionLeft >= 0) {
				player.setGrounded(true);
				player.setxVel(0);
				player.setX(groundList.get(boxCollisionLeft).getX()
						+ groundList.get(boxCollisionLeft).getWidth() + padding);
			}

			// Tester collision til høyre
			int boxCollisionRight = checkCollisionRight();
			if (boxCollisionRight >= 0) {
				player.setGrounded(true);
				player.setxVel(0);
				player.setX(groundList.get(boxCollisionRight).getX()
						- player.getWidth() - padding);
			}
		}
	}

	/*
	 * COLLISION UNDER
	 */

	public int checkCollisionDown() {
		for (int i = 0; i < groundList.size(); i++) {
			try {
				if (player.getFootBox().intersects(
						groundList.get(i).getBounds())) {
					if (groundList.get(i).getCollidable())
						return i;
				}
			} catch (NullPointerException e) {
			}
		}
		return -1;
	}

	/*
	 * COLLISION UNDER
	 */

	public int checkCollisionHead() {
		for (int i = 0; i < groundList.size(); i++) {
			try {
				if (player.getHeadBox().intersects(
						groundList.get(i).getBounds())) {
					if (groundList.get(i).getCollidable())
						return i;
				}
			} catch (NullPointerException e) {
			}
		}
		return -1;
	}

	/*
	 * COLLISION LEFT
	 */

	public int checkCollisionLeft() {
		for (int i = 0; i < groundList.size(); i++) {
			try {
				if (player.getLeftBox().intersects(
						groundList.get(i).getBounds())) {
					if (groundList.get(i).getCollidable())
						return i;
				}
			} catch (NullPointerException e) {
			}
		}
		return -1;
	}

	/*
	 * COLLISION RIGHT
	 */

	public int checkCollisionRight() {
		for (int i = 0; i < groundList.size(); i++) {
			try {
				if (player.getRightBox().intersects(
						groundList.get(i).getBounds())) {
					if (groundList.get(i).getCollidable())
						return i;
				}
			} catch (NullPointerException e) {
			}
		}
		return -1;
	}

	public void gravity() {
		if (player.getHasGravity()) {
			player.setyVelGravity(gravity);
		}
	}
	
	public static ArrayList<GroundObject> getGroundList() {
		return groundList;
		
	}
}
