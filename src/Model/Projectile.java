package Model;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public class Projectile extends Character{

	private Direction dir;
	private int damage_timer = 0;
	private boolean projectile_expired = false;
	public Projectile(Direction dir, int pos_x, int pos_y) {
		this.dir = dir;
		this.setPos_x(pos_x);
		this.setPos_y(pos_y);
		this.move_speed = 8;
	}

	
	public boolean is_expired() {
		return this.projectile_expired;
	}
	
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler control) {

		Coordinates[] hit_box = this.collisionHitBox(tile_size);
		boolean can_pass = !this.checkCollision(hit_box, dir, map_structure, tile_size);

		switch(dir) {
		case UP:
			if (can_pass && getPos_y()-getMoveSpeed() >= 0) {
				up();
			}
			break;
		case RIGHT:
			if (can_pass && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() < GamePanel.getPanelWidth()) {
				right();
			}
			break;
		case DOWN:
			if (can_pass && getPos_y()+tile_size+this.move_speed < GamePanel.getPanelHeight()) {
				down();
			}
			break;
		case LEFT:
			if (can_pass && getPos_x()-this.getMoveSpeed() >= 0) {
				left();
			}
			break;
		default:
		}
		
	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = false;
		Coordinates[] bomberman_hit_box = Bomberman.getInstance().collisionHitBox(tile_size);
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].getCollision();
			canPass3 = map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getPlacedBomb() == null;
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].getCollision();
			canPass3 = map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].getCollision();
			canPass3 = map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getPlacedBomb() == null;
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].getCollision();
			canPass3 = map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		default:
		}
		
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
		
		if (canPass1 && canPass2 && canPass3) {
			return false;
		}
		else {
			this.projectile_expired = true;
			return true;
		}
	}

}
