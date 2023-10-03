package Model;



import java.util.Random;

import View.GamePanel;

public class Enemy extends Character {
	
	Random r = new Random();
	public boolean hitObstacle = false;
	public char dir = 'u';
	public int prev_dir;
	public int curr_dir;
	
	/*
	 * Funzione per muovere un nemico. La prima parte introduce una componente random che potrebbe far effettuare un cambio di direzione ogni qual volta il 
	 * modello del nemico si trova sovrapposto perfettamente ad un tile (si verifica banalmente controllando l'angolo in alto a sinistra del modello)
	 * In seguito si controlla se il movimento sia possibile (controllando tramite hitObstacle che il modello non vada incontro ad un tile con collisione), nel quale
	 * caso si cambia direzione, altrimenti si effettua il movimento vero e proprio verso la direzione dir
	 */
	public void move() {
		if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
			int i = r.nextInt(5);

			if (i == 0) {
				changeDir();
				return;
			}
		}
		if (hitObstacle == true) {
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
	
	/*
	 * Funzione utilitaria per associare un valore intero ad ogni direzione
	 */
	
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
	private boolean oppositeDir(int dir1, int dir2) {
		if ((dir1+dir2)%2 == 0) {
			return true;
		}
		else return false;
	}
	
	/*
	 * Funzione che cambia direzione in modo (quasi) casuale.
	 */
	public void changeDir() {
		int i = r.nextInt(4);
		int counter = 0;
		
		// Se la direzione ottenuta è l'opposto di quella da cui si viene, si fanno
		// cinque tentativi per ottenere una direzione differente.
		while (counter <= 5 && oppositeDir(i, this.prev_dir) ) {
			while (i == this.prev_dir) {
				i = r.nextInt(4);
			}
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
		super.health = 1;
	}

}
