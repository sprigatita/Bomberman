package Model;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public class Walker extends Enemy{
	
	private Direction dir = Direction.UP;
	private Direction prev_dir;
	private Direction curr_dir;
	
	public Walker(int health) {
		this.health = health;
	}
	
	public Walker() {
		super();
	}
	
	@Override
	public Coordinates[] collisionHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y();
		int hitBoxUpperRight_x = getPos_x() + tile_size - 1;
		int hitBoxUpperRight_y = getPos_y();
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
	}
	
	public void move() {
//		if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
//			int i = r.nextInt(5);
//
//			if (i == 0) {
//				changeDir();
//			}
//		}
		switch(dir) {
		case UP:
			up();
			break;
		case DOWN:
			down();
			break;
		case LEFT:
			left();
			break;
		case RIGHT:
			right();
			break;
		}

	}
	
	private int dir_number(char dir) {
		switch(dir) {
		case 'u':
			return 0;
		case 'd':
			return 2;
		case 'l':
			return 1;
		case 'r':
			return 3;
		default:
			return -1;
		}
	}
	
	/*
	 * Funzione utilitaria per calcolare che due direzioni non siano una l'opposto dell'altra. Usata per diminuire la componente casuale e fare
	 * in modo che il modello non torni troppo spesso dalla direzione da cui è venuto
	 */
	private boolean oppositeDir(Direction dir1, Direction dir2) {
		if (dir2 == null) {
			return false;
		}
		if (dir1 == Direction.UP && dir2 == Direction.DOWN) {
			return true;
		}
		if (dir1 == Direction.DOWN && dir2 == Direction.UP) {
			return true;
		}
		if (dir1 == Direction.LEFT && dir2 == Direction.RIGHT) {
			return true;
		}
		if (dir1 == Direction.RIGHT && dir2 == Direction.LEFT) {
			return true;
		}
		return false;
	}
	
	/*
	 * Funzione che cambia direzione in modo (quasi) casuale.
	 */
	
	public Direction dirFromInt(int i) {
		Direction dir = Direction.UP;
		switch(i){
		case 0:
			dir = Direction.UP;
			break;
		case 1:
			dir = Direction.DOWN;
			break;
		case 2:
			dir = Direction.LEFT;
			break;
		case 3:
			dir = Direction.RIGHT;
			break;
		default:
		}
		return dir;
	}
	
	public void changeDir() {
//		int i = r.nextInt(4);
//		Direction dir;
//		}
//		int counter = 0;
//		
//		// Se la direzione ottenuta è l'opposto di quella da cui si viene, si fanno
//		// cinque tentativi per ottenere una direzione differente.
//		while (counter <= 5 && oppositeDir(dir, this.prev_dir) ) {
//			while (dir == this.prev_dir) {
//				i = r.nextInt(4);
//			}
//			counter++;
//		}
//		this.prev_dir = i;
//		switch(i) {
//		case 0:
//			this.dir = Direction.UP;
//			break;
//		case 1:
//			this.dir = Direction.LEFT;
//			break;
//		case 2:
//			this.dir = Direction.DOWN;
//
//			break;
//		case 3:
//			this.dir = Direction.RIGHT;
//			break;
//		}
//		
//		
		int i = r.nextInt(4);
		Direction dir = dirFromInt(i);
		int counter = 0;
		
		// Se la direzione ottenuta è l'opposto di quella da cui si viene, si fanno
		// cinque tentativi per ottenere una direzione differente.
		while (counter <= 5 && oppositeDir(dir, this.prev_dir) ) {
			while (dir == this.prev_dir) {
				i = r.nextInt(4);
				dir = dirFromInt(i);
			}
			counter++;
		}
		this.prev_dir = this.dir;
		this.dir = dir;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
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
//				bv.setNextUp();				
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
//				bv.setNextDown();
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
//				bv.setNextLeft();				
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
//				bv.setNextRight();			
			}
			else {
				changeDir();
			}
			
		}
		this.notifyObservers(this.getDir());
		this.clearChanged();
	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = false;
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
		if (canPass1 && canPass2 && canPass3) {
			return false;
		}
		else {
			return true;
		}
	}
}
