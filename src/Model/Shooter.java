package Model;

import java.util.ArrayList;

import Controller.ControlsHandler;

public class Shooter extends WalkerDecorator{
	
	private ArrayList<Projectile> projectiles;
	private int shooting_cooldown = 0;
	
	public Shooter(Walker wrappee, ArrayList<Projectile> projectiles) {
		super(wrappee);
		this.projectiles = projectiles;
	}
	
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		super.move(tile_size, map_structure, controls);
		if (shooting_cooldown <= 0) {
			this.shootProjectile(tile_size, projectiles, map_structure);
			shooting_cooldown = 500;
		}
		else {
			shooting_cooldown -= 10;
		}
		
	}
	
	public void shootProjectile(int tile_size, ArrayList<Projectile> projectiles, TileModel[][] map_structure) {
		switch(this.wrappee.getDir()) {
		case UP:
			if (!map_structure[(this.getPos_y()-tile_size)/tile_size][this.getPos_x()/tile_size].getCollision()) {
				projectiles.add(new Projectile(wrappee.getDir(), this.getPos_x(), this.getPos_y()-tile_size));	
			}
			break;
		case RIGHT:
			if (!map_structure[this.getPos_y()/tile_size][(this.getPos_x()+tile_size)/tile_size].getCollision()) {
				projectiles.add(new Projectile(wrappee.getDir(), this.getPos_x()+tile_size, this.getPos_y()));				
			}
			break;
		case DOWN:
			if (!map_structure[(this.getPos_y()+tile_size)/tile_size][this.getPos_x()/tile_size].getCollision()) {
				projectiles.add(new Projectile(wrappee.getDir(), this.getPos_x(), this.getPos_y()+tile_size));
			}
			break;
		case LEFT:
			if (!map_structure[this.getPos_y()/tile_size][(this.getPos_x()-tile_size)/tile_size].getCollision()) {
				projectiles.add(new Projectile(wrappee.getDir(), this.getPos_x()-tile_size, this.getPos_y()));				
			}
			break;
		default:
		}
		
	}

}
