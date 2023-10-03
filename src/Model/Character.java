package Model;

public class Character extends Entity{
	
	
	protected int health;
	
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

	public void right() {
	    setPos_x(getPos_x() + MOVE_SPEED);

	}
	
	public void damage() {
		
	}
	
	
}
