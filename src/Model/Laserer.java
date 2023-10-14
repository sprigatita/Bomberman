package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Controller.ControlsHandler;
import Controller.Coordinates;

public class Laserer extends Enemy {
	private int shooting_cd = 0;
	private Direction shooting_dir;
	private HashMap<TileModel, Integer> laser_tiles;
	
	
	public Laserer(int x, int y, Direction dir, HashMap<TileModel, Integer> laser_tiles) {
		super(x,y);
		this.shooting_dir = dir;
		this.laser_tiles = laser_tiles;
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler control) {
		
		if (shooting_cd == 0) {
			this.shootLaser(tile_size, map_structure, this.laser_tiles);
			System.out.println("test");
			System.out.println(this.shooting_dir);
			shooting_cd = 200;
		}
		else {
			shooting_cd -= 1;
		}
		
		Coordinates[] hit_box = this.collisionHitBox(tile_size);
		Bomberman b = Bomberman.getInstance();
		if ((b.getPos_x() <= hit_box[0].i && hit_box[0].i <= b.getPos_x()+tile_size) || 
				(b.getPos_x() <= hit_box[1].i && hit_box[1].i <= b.getPos_x() + tile_size)) {
			if ((b.getPos_y() <= hit_box[0].j && hit_box[0].j <= b.getPos_y()+tile_size) || 
					(b.getPos_y() <= hit_box[3].j && hit_box[3].j <= b.getPos_y()+tile_size)) {
				if(this.damage_timer <= 0) {					
					b.damage();
					System.out.println("hit");
					this.damage_timer = 1000;
				}
			}
			
		}
		this.damage_timer -= 10;

	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void shootLaser(int tile_size, TileModel[][] map_structure, HashMap<TileModel, Integer> laser_tiles) {
		int pos_row = this.getPos_y()/tile_size;
		int pos_col = this.getPos_y()/tile_size;
		int i = 1;
		switch(this.shooting_dir) {
		case UP:
			while(!map_structure[pos_row - i][pos_col].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row - i][pos_col], 50);
				i+=1;
			}
			break;
		case RIGHT:
			while(!map_structure[pos_row][pos_col+i].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row][pos_col+i], 50);
				i+=1;
			}
			break;
		case DOWN:
			System.out.println("insisde down");
			while(!map_structure[pos_row + i][pos_col].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row + i][pos_col], 50);
				System.out.println(this.laser_tiles.size() + i);
				i+=1;
			}
			break;
		case LEFT:
			while(!map_structure[pos_row][pos_col-i].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row][pos_col -i], 50);
				i+=1;
			}
			break;
		default:
			
		}
		
		Bomberman b = Bomberman.getInstance();
		Coordinates[] hit_box = b.collisionHitBox(tile_size);
		for (Coordinates c : hit_box) {
			int row = c.j/tile_size;
			int col = c.i/tile_size;
			if (this.laser_tiles.containsKey(map_structure[row][col])){
				b.damage();
			}
		}
		
	}

}
