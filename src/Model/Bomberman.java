package Model;

public class Bomberman extends Character {
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	
	
	
	public static int getMoveSpeed() {
		return Character.MOVE_SPEED;
	}

	private Bomberman() {
		
	}
	
	public static Bomberman getInstance() {
		if (bomberman == null) {
			bomberman = new Bomberman();
		}
		return bomberman;
	}
	//Fine Singleton

	
}
