package View;

import Controller.*;
import Model.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable {
	
	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	private char dir = 'd';
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	
	
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}
	
	
	BombermanView c = new BombermanView();
	Bomberman b = Bomberman.getInstance();
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
		g.drawImage(c.getSprite(), b.getPos_x(), b.getPos_y(), c.getSpriteWidth()*2, c.getSpriteHeight()*2, null);
	}

	@Override
	public void run() {
		
		for (int i =0 ; i < map_structure.length; i++) {
			for (int j = 0; j < map_structure[0].length; j++) {
				char ch;
				if (map_structure[i][j].getCollision() == true) {
					ch = 'O';
				}
				else {
					ch = 'X';
				}
				System.out.print(ch + " ");
			}
			System.out.println();
		}
		while(true) {
			updatePos();
			repaint();
			
			//Lo sleep lancia un'eccezione non gestita
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean checkCollision(int corner1_x, int corner1_y, int corner2_x, int corner2_y) {
		
		int corner1_tile_x = corner1_x/FINAL_TILE_SIZE;
		int corner1_tile_y = corner1_y/FINAL_TILE_SIZE;
		int corner2_tile_x = corner2_x/FINAL_TILE_SIZE;
		int corner2_tile_y = corner2_y/FINAL_TILE_SIZE;
		boolean canPass1 = !this.map_structure[corner1_tile_y][corner1_tile_x].getCollision();
		boolean canPass2 = !this.map_structure[corner2_tile_y][corner2_tile_x].getCollision();
		if (canPass1 && canPass2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Funzione per modificare posizione del personaggio, delle bombe, dei nemici, all'interno di un loop
	public void updatePos() {
		int HitBoxUpperLeft_x = b.getPos_x();
		int HitBoxUpperLeft_y = b.getPos_y();
		int HitBoxUpperRight_x = b.getPos_x() + c.getSpriteWidth()*2;
		int HitBoxUpperRight_y = b.getPos_y();
		int HitBoxBottomLeft_x = b.getPos_x();
		int HitBoxBottomLeft_y = b.getPos_y() + c.getSpriteHeight()*2;
		int HitBoxBottomRight_x = b.getPos_x() + c.getSpriteWidth()*2;
		int HitBoxBottomRight_y = b.getPos_y() + c.getSpriteHeight()*2;
		if (controls.isUp() == true && 	b.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			boolean canMove = checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
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
	
	
}
