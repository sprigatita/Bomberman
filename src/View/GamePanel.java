package View;

import Controller.*;
import Model.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable {
	
	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	ArrayList<BombModel> placedBombs = new ArrayList<BombModel>();
	BombView bombView = new BombView();
	private int bombTimer = 0;
	
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}
	
	
	BombermanView c = new BombermanView();
	Bomberman b = Bomberman.getInstance();
	Enemy e = new Enemy();
	EnemyView ev = new EnemyView();
	TileView terrain = new TileView("green_village");
	int[] config = {1};
	MapModel map = new MapModel("src/resources/map.txt", config);
	TileModel[][] map_structure = map.getMapStructure();
	ControlsHandler controls;
	
	//All'interno del costruttore creiamo il thread, lo facciamo partire ed inizializziamo tutti gli handler e le caratteristiche del panel.
	public GamePanel() {
		b.setPos_x(100);
		b.setPos_y(100);
		this.setPreferredSize(new Dimension((X_TILES*FINAL_TILE_SIZE),(Y_TILES*FINAL_TILE_SIZE)));
		this.setBackground(new Color(107, 106, 104));
		controls = new ControlsHandler(c);
		this.addKeyListener(controls);
		this.setFocusable(true);
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D)g;
		super.paintComponent(gg);
		terrain.drawTile(g, map_structure);
		for (BombModel bomb : placedBombs) {
			bombView.drawBomb(g, bomb.getPos_x(), bomb.getPos_y());
		}
		g.drawImage(c.getSprite(), b.getPos_x(), b.getPos_y(), c.getSpriteWidth()*2, c.getSpriteHeight()*2, null);
		g.drawImage(ev.getSprite(), e.getPos_x()+7, e.getPos_y(), ev.getSpriteWidth()*2, ev.getSpriteHeight()*2, null);
//		g.drawRect(e.getPos_x(), e.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE);
	}

	@Override
	public void run() {
		
		while(true) {
			updatePos();
			updateEnemyPos();
			placeBomb();
			repaint();
			bombTimer -= 3;
			
			//Lo sleep lancia un'eccezione non gestita
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 * funzione che controlla se due punti si trovano in un blocco con la collision attiva. Ritorna un valore
	 * true se e solo se entrambi i punti non si trovano in un blocco con la collision attiva
	 */
	
	private boolean checkCollision(int corner1_x, int corner1_y, int corner2_x, int corner2_y) {
		
		int corner1_tile_x = corner1_x/(FINAL_TILE_SIZE);
		int corner1_tile_y = corner1_y/(FINAL_TILE_SIZE);
		int corner2_tile_x = corner2_x/(FINAL_TILE_SIZE);
		int corner2_tile_y = corner2_y/(FINAL_TILE_SIZE);
		boolean canPass1 = !this.map_structure[corner1_tile_y][corner1_tile_x].getCollision();
		boolean canPass2 = !this.map_structure[corner2_tile_y][corner2_tile_x].getCollision();
		if (canPass1 && canPass2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateEnemyPos() {
		
		int HitBoxUpperLeft_x = e.getPos_x()+1;
		int HitBoxUpperLeft_y = e.getPos_y()+1;
		int HitBoxUpperRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxUpperRight_y = e.getPos_y()+1;
		int HitBoxBottomLeft_x = e.getPos_x()+1;
		int HitBoxBottomLeft_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxBottomRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxBottomRight_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		if (e.dir == 'u' && 	e.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			System.out.println("updating pos up");
//			boolean canMove = checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
//			if (canMove) {
//				e.move();
//				c.setNextUp();				
//			}
//			else {
//				e.changeDir();
//			}
			e.hitObstacle= !checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
			e.move();
			if (!e.hitObstacle) {
				ev.setNextUp();				
			}
		}
		else if (e.dir == 'd' && e.getPos_y()+ev.getSpriteHeight()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			System.out.println("updating pos down");
			e.hitObstacle = !checkCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getMoveSpeed());			
			e.move();
			if (!e.hitObstacle) {
				ev.setNextDown();				
			}
		}
		else if (e.dir == 'l' && e.getPos_x()-Bomberman.getMoveSpeed() >= 0) {
			System.out.println("updating pos left");
//			boolean canMove = checkCollision(HitBoxUpperLeft_x - Bomberman.getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getMoveSpeed(), HitBoxBottomLeft_y);
////			boolean canMove = true;
//			if (canMove) {
//				e.move();
//				c.setNextLeft();				
//			}
//			else {
//				e.changeDir();
//			}
			e.hitObstacle = !checkCollision(HitBoxUpperLeft_x - Bomberman.getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getMoveSpeed(), HitBoxBottomLeft_y);
			e.move();
			if (!e.hitObstacle) {
				ev.setNextLeft();				
			}
		}
		else if (e.dir == 'r' && e.getPos_x()+ev.getSpriteWidth()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			System.out.println("updating pos right");
//			boolean canMove = checkCollision(HitBoxUpperRight_x + Bomberman.getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getMoveSpeed(), HitBoxBottomRight_y);
////			boolean canMove = true;
//			if (canMove) {
//				e.move();
//				c.setNextRight();			
//			}
//			else {
//				e.changeDir();
//			}
			e.hitObstacle = !checkCollision(HitBoxUpperRight_x + Bomberman.getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getMoveSpeed(), HitBoxBottomRight_y);
			e.move();
			if (!e.hitObstacle) {
				ev.setNextRight();				
			}
		}
		else {
			System.out.println("changing dir");
			e.changeDir();
			
		}
		
		
	}
	
	/*
	 * Funzione per aggiornare le posizioni.
	 * 
	 * Collision: viene chiamata checkCollision su una coppia di angoli di una HitBox (i due angoli superiori se si va in alto,
	 * i due angoli sinistri se si va a sinistra e così via..) La chiamata viene effettuata passandogli i valori degli angoli
	 * che si dovrebbero avere dopo il movimento (si fa una previsione). Se la funzione checkCollision ritorna true (e quindi
	 * nessuno dei due angoli finisce in un blocco con collisione) allora si può effettuare il movimento.
	 */
	
	public void updatePos() {
		int border_const = 5;
		int HitBoxUpperLeft_x = b.getPos_x()+10;
		int HitBoxUpperLeft_y = b.getPos_y() + c.getSpriteHeight();
		int HitBoxUpperRight_x = b.getPos_x() + c.getSpriteWidth()*2-border_const*2;
		int HitBoxUpperRight_y = b.getPos_y() + c.getSpriteHeight();
		int HitBoxBottomLeft_x = b.getPos_x()+border_const*2;
		int HitBoxBottomLeft_y = b.getPos_y() + c.getSpriteHeight()*2;
		int HitBoxBottomRight_x = b.getPos_x() + c.getSpriteWidth()*2-border_const*2;
		int HitBoxBottomRight_y = b.getPos_y() + c.getSpriteHeight()*2;
//		int HitBoxUpperLeft_x = b.getPos_x();
//		int HitBoxUpperLeft_y = b.getPos_y();
//		int HitBoxUpperRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxUpperRight_y = b.getPos_y();
//		int HitBoxBottomLeft_x = b.getPos_x();
//		int HitBoxBottomLeft_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxBottomRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxBottomRight_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		if (controls.isUp() == true && 	b.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			boolean canMove = checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
//			boolean canMove = checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed()+1, HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
			if (canMove) {
				b.up();
				c.setNextUp();				
			}
		}
		else if (controls.isDown() == true && b.getPos_y()+c.getSpriteHeight()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			boolean canMove = checkCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getMoveSpeed());
			if (canMove) {				
				b.down();
				c.setNextDown();
			}
		}
		else if (controls.isLeft() == true && b.getPos_x()-Bomberman.getMoveSpeed() >= 0) {
			boolean canMove = checkCollision(HitBoxUpperLeft_x - Bomberman.getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getMoveSpeed(), HitBoxBottomLeft_y);
			if (canMove) {
				b.left();
				c.setNextLeft();				
			}
			
		}
		else if (controls.isRight() == true && b.getPos_x()+c.getSpriteWidth()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			boolean canMove = checkCollision(HitBoxUpperRight_x + Bomberman.getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getMoveSpeed(), HitBoxBottomRight_y);
			if (canMove) {
				b.right();
				c.setNextRight();			
			}
			
		}
	}
	
	public void placeBomb() {
		if (controls.isSpace()) {
			
			if (bombTimer <= 0) {
				//ricordare di mettere i controlli e il timer per le bombe!!!!!!!!
				placedBombs.add(new BombModel(b.getPos_x(), b.getPos_y()));
				bombTimer = 100;
			}
			
		}
		
	}
	
	
}
