package Model;

public class Bomberman extends Character {
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	private final static int MOVE_SPEED = 3;
	
	
	public static int getMoveSpeed() {
		return MOVE_SPEED;
	}

	private Bomberman() {
		setPos_x(0);
		setPos_y(0);
	}
	
	public static Bomberman getInstance() {
		if (bomberman == null) {
			bomberman = new Bomberman();
		}
		return bomberman;
	}
	//Fine Singleton

	public void up() {
		setPos_y(getPos_y() - MOVE_SPEED);
	}

	public void down() {
		setPos_y(getPos_y() + MOVE_SPEED);
	}

	public void left() {
	    setPos_x(getPos_x() - MOVE_SPEED);

	}

	public void right() {
	    setPos_x(getPos_x() + MOVE_SPEED);

	}
	
	
	
}
