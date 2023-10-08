package Model;

public abstract class Character extends Entity{
	
	protected boolean dead;
	protected int health;
	protected int death_animation_counter = 60;
	protected final static int MOVE_SPEED = 4;
	public Character() {
		setPos_x(96);
		setPos_y(96);
	}
	
	public void up() {
		setPos_y(getPos_y() - MOVE_SPEED);
	}

	public void down() {
		setPos_y(getPos_y() + MOVE_SPEED);
	}

	public void left() {
	    setPos_x(getPos_x() - MOVE_SPEED);

	}

	public int getDeath_animation_counter() {
		return death_animation_counter;
	}

	public void right() {
	    setPos_x(getPos_x() + MOVE_SPEED);

	}
	
	public void damage() {
		this.health -= 1;
		if (this.health == 0) {
			this.dead = true;
		}
		
	}
	public boolean isDead() {
		return dead;
	}

	public boolean decreaseDeathAnimationCounter() {
		if (death_animation_counter > 0) {
			this.death_animation_counter -= 1;			
		}
		
		if (death_animation_counter <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
