package Model;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public abstract class Character extends Entity{
	
	protected boolean dead;
	protected int health;
	protected int death_animation_counter = 60;
	protected int move_speed = 4;
	protected Direction dir = Direction.UP;
	private boolean is_actually_dead = false;
	
	
	public void setReallyDead() {
		this.is_actually_dead = true;
	}
	
	public boolean isReallyDead() {
		return this.is_actually_dead;
	}
	
	public void revive() {
		this.death_animation_counter = 60;
		this.dead = false;
		this.is_actually_dead = false;
		
	}
	
	public int getMoveSpeed() {
		return this.move_speed;
	}
	
	public Character() {
		setPos_x(96);
		setPos_y(96);
	}
	
	public void up() {
		setPos_y(getPos_y() - move_speed);
		this.dir = Direction.UP;
	}

	public void down() {
		setPos_y(getPos_y() + move_speed);
		this.dir = Direction.DOWN;
	}

	public void left() {
	    setPos_x(getPos_x() - move_speed);
	    this.dir = Direction.LEFT;

	}
	
	public void right() {
		setPos_x(getPos_x() + move_speed);
		this.dir = Direction.RIGHT;
		
	}

	public int getDeath_animation_counter() {
		return death_animation_counter;
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

	public Direction getDir() {
		return dir;
	}
	
	public abstract void move(int tile_size, TileModel[][] map_structure, ControlsHandler control);
	public abstract boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size);
	
	public Coordinates[] hitBox(int tile_size) {
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(this.getPos_x(), this.getPos_y());
		hit_box[1] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y());
		hit_box[2] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y()+tile_size-1);
		hit_box[3] = new Coordinates(this.getPos_x(), this.getPos_y()+tile_size-1);
		return hit_box;
	}
	
	public Coordinates[] collisionHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x()+tile_size/5;
		int hitBoxUpperLeft_y = getPos_y()+tile_size*2/5;
		int hitBoxUpperRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxUpperRight_y = getPos_y()+tile_size*2/5;
		int hitBoxBottomLeft_x = getPos_x()+tile_size/5;
		int hitBoxBottomLeft_y = getPos_y()+tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
	}

}
