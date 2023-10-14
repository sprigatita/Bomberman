package Model;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public class FatBoss extends Walker {
	int attack_timer = 1500;
	int state = 0;
	
	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = false;
		Coordinates[] bomberman_hit_box = Bomberman.getInstance().collisionHitBox(tile_size);
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].isBorder();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].isBorder();
			canPass3 = map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getPlacedBomb() == null;
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].isBorder();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].isBorder();
			canPass3 = map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].isBorder();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].isBorder();
			canPass3 = map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getPlacedBomb() == null;
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].isBorder();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].isBorder();
			canPass3 = map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		default:
		}
		if (canPass1 && canPass2 && canPass3) {
			return false;
		}
		else {
			return true;
		}
	}

//	// 0 base
//	// 1 windup
//	// 2 attack
	public FatBoss(int x, int y, int health) {
		super(x,y,health);
		// TODO Auto-generated constructor stub
	}

	public FatBoss(int x, int y) {
		super(x, y);
		this.move_speed = 2;
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		if (this.attack_timer < 500) {
			
			this.attack_timer-=10;
		}
		else {
			
			this.attack_timer-= 5;
		}
		if (this.attack_timer< 0) {
			this.attack_timer = 1499;
		}
		this.state = 2 - this.attack_timer/500;
		if (!this.isDead() && this.attack_timer > 500) {
			Coordinates[] hit_box = this.collisionHitBox(tile_size);
			if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
				int i = r.nextInt(5);
				
				if (i == 0) {
					changeDir();
				}
			}
			
			if (this.dir == Direction.UP && 	getPos_y()-getMoveSpeed() >= 0) {
				boolean canMove = !checkCollision(hit_box, Direction.UP, map_structure, tile_size);
				if (canMove) {
					move();
					this.setChanged();
//			bv.setNextUp();				
				}
				else {
					changeDir();
				}
			}
			else if (this.dir == Direction.DOWN && getPos_y()+tile_size+Bomberman.getInstance().getMoveSpeed() <= 
					GamePanel.getPanelHeight()) {
				boolean canMove = !checkCollision(hit_box, Direction.DOWN, map_structure, tile_size);		
				if (canMove) {				
					move();
					this.setChanged();
//			bv.setNextDown();
				}
				else {
					changeDir();
				}
			}
			else if (this.dir == Direction.LEFT && getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
				boolean canMove = !checkCollision(hit_box, Direction.LEFT, map_structure, tile_size);
				if (canMove) {
					move();
					this.setChanged();
//			bv.setNextLeft();				
				}
				else {
					changeDir();
				}
				
			}
			else if (this.dir == Direction.RIGHT && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() <=GamePanel.getPanelWidth())  {
				boolean canMove = !checkCollision(hit_box, Direction.RIGHT, map_structure, tile_size);
				if (canMove) {
					move();
					this.setChanged();
//			bv.setNextRight();			
				}
				else {
					changeDir();
				}
				
			}
			
		}
		Coordinates[] hit_box = this.damageHitBox(tile_size);
		Bomberman b = Bomberman.getInstance();
		Coordinates[] b_hit_box = b.collisionHitBox(tile_size);
		for (Coordinates c : b_hit_box) {
			if (c.i >= hit_box[0].i && c.i <= hit_box[1].i) {
				if (c.j >= hit_box[0].j && c.j <= hit_box[3].j) {
					if (this.damage_timer <= 0) {
						System.out.println("hit");
						b.damage();
						this.damage_timer = 1000;
						
					}
				}
			}
		}
//		System.out.println(this.damage_timer);
		this.damage_timer -= 10;
		this.setChanged();
		this.notifyObservers(this.attack_timer);
		this.clearChanged();
		
	}
	


	@Override
	public Coordinates[] collisionHitBox(int tile_size) {

		Coordinates[] hit_box = new Coordinates[4];


		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y();
		int hitBoxUpperRight_x = getPos_x() + 5*tile_size - 1;
		int hitBoxUpperRight_y = getPos_y();
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+9*tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + 5*tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ 9*tile_size-1;
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);

		return hit_box;
	}
	
	
	public Coordinates[] damageHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y();
		int hitBoxUpperRight_x = getPos_x() + 5*tile_size - 1;
		int hitBoxUpperRight_y = getPos_y();
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+5*tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + 5*tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ 5*tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];

		switch (state) {
		
		case 0:
			hitBoxUpperLeft_x = getPos_x();
			hitBoxUpperLeft_y = getPos_y()+ tile_size -1;
			hitBoxUpperRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxUpperRight_y = getPos_y() + tile_size -1;
			hitBoxBottomLeft_x = getPos_x();
			hitBoxBottomLeft_y = getPos_y()+7*tile_size-1;
			hitBoxBottomRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxBottomRight_y = getPos_y()+ 7*tile_size-1;
			hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
			hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
			hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
			hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
//			System.out.println("state 0");
			break;
		case 1:
			hitBoxUpperLeft_x = getPos_x();
			hitBoxUpperLeft_y = getPos_y();
			hitBoxUpperRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxUpperRight_y = getPos_y();
			hitBoxBottomLeft_x = getPos_x();
			hitBoxBottomLeft_y = getPos_y()+7*tile_size-1;
			hitBoxBottomRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxBottomRight_y = getPos_y()+ 7*tile_size-1;
			hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
			hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
			hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
			hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);

			break;
		case 2:
			hitBoxUpperLeft_x = getPos_x();
			hitBoxUpperLeft_y = getPos_y()+ tile_size -1;
			hitBoxUpperRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxUpperRight_y = getPos_y() + tile_size -1;
			hitBoxBottomLeft_x = getPos_x();
			hitBoxBottomLeft_y = getPos_y()+9*tile_size-1;
			hitBoxBottomRight_x = getPos_x() + 5*tile_size - 1;
			hitBoxBottomRight_y = getPos_y()+ 9*tile_size-1;
			hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
			hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
			hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
			hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);

			break;
		}
		return hit_box;
	}
	
//	
//	@Override
//	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
////		boolean canPass1 = false;
////		boolean canPass2 = false;
////		boolean canPass3 = false;
////		Coordinates[] bomberman_hit_box = Bomberman.getInstance().collisionHitBox(tile_size);
////		switch(dir) {
////		case UP:
////			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].isBorder();
////			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].isBorder();
////			canPass3 = map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getPlacedBomb() == null;
////			break;
////		case RIGHT:
////			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].isBorder();
////			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].isBorder();
////			canPass3 = map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
////			break;
////		case DOWN:
////			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].isBorder();
////			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].isBorder();
////			canPass3 = map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getPlacedBomb() == null;
////			break;
////		case LEFT:
////			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].isBorder();
////			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].isBorder();
////			canPass3 = map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
////			break;
////		default:
////		}
////		if (canPass1 && canPass2 && canPass3) {
////			return false;
////		}
////		else {
////			return true;
////		}
//		return false;
//	}
//	
}


