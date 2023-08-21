package Model;



import java.util.Random;

import View.GamePanel;

public class Enemy extends Character {
	Random r = new Random();
	public boolean hitObstacle = false;
	public char dir = 'u';
	public int prev_dir;
	public int curr_dir;
	public void move() {
		if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
			System.out.println("angle");
			int i = r.nextInt(5);
			System.out.println(i);
			if (i == 0) {
				changeDir();
				return;
			}
		}
		if (hitObstacle == true) {
			System.out.println("Obstacle");
			changeDir();
		}
		else {
			switch(dir) {
			case 'u':
				up();
				break;
			case 'd':
				down();
				break;
			case 'l':
				left();
				break;
			case 'r':
				right();
				break;
			}
		}
		
		
		
	}
	
	public int dir_number(char dir) {
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
	
	public boolean oppositeDir(int dir1, int dir2) {
		if ((dir1+dir2)%2 == 0) {
			return true;
		}
		else return false;
	}
	
	public void changeDir() {
		int i = r.nextInt(4);
		int counter = 0;
		while (counter <= 5 && oppositeDir(i, this.prev_dir)) {
			i = r.nextInt(4);
			counter++;
		}
		this.prev_dir = i;
		switch(i) {
		case 0:
			this.dir = 'u';
			break;
		case 1:
			this.dir = 'l';
			break;
		case 2:
			this.dir = 'd';

			break;
		case 3:
			this.dir = 'r';
			break;
		}
		
		
	}
	
	public Enemy() {
		// TODO Auto-generated constructor stub
	}

}
