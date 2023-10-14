package Model;



import java.util.Random;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public abstract class Enemy extends Character {
	
	protected int damage_timer = 0;
	Random r = new Random();

	
	/*
	 * Funzione per muovere un nemico. La prima parte introduce una componente random che potrebbe far effettuare un cambio di direzione ogni qual volta il 
	 * modello del nemico si trova sovrapposto perfettamente ad un tile (si verifica banalmente controllando l'angolo in alto a sinistra del modello)
	 * In seguito si controlla se il movimento sia possibile (controllando tramite hitObstacle che il modello non vada incontro ad un tile con collisione), nel quale
	 * caso si cambia direzione, altrimenti si effettua il movimento vero e proprio verso la direzione dir
	 */
	
	
	
	public Enemy(int x, int y) {
		this.setPos_x(x);
		this.setPos_y(y);
		this.health = 1;
	}

	

		
		
		
	
	/*
	 * Funzione utilitaria per associare un valore intero ad ogni direzione
	 */
	




	

}
