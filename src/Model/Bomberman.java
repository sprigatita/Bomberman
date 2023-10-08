package Model;

public class Bomberman extends Character {
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	private PowerUpModel power_up;
	
	
	public void setPower_up(PowerUpModel power_up) {
		this.power_up = power_up;
	}

	public static int getMoveSpeed() {
		return Character.MOVE_SPEED;
	}

	private Bomberman() {
		this.setHealth(1);
	}
	
	public static Bomberman getInstance() {
		if (bomberman == null) {
			bomberman = new Bomberman();
		}
		return bomberman;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public PowerUpModel getPower_up() {
		return power_up;
	}

}
