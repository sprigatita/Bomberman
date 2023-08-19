package View;

import Controller.*;
import Model.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable {
	
	private static final int X_TILES = 16;
	private static final int Y_TILES = 12;
	private static final int TILE_SIZE = 16;
	private static final int SCALING_CONST = 3;
	private char dir = 'd';
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}
	
	private static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	BombermanView c = new BombermanView();
	Bomberman b = Bomberman.getInstance();
	TileView terrain = new TileView();
	ControlsHandler controls;
	
	//All'interno del costruttore creiamo il thread, lo facciamo partire ed inizializziamo tutti gli handler e le caratteristiche del panel.
	public GamePanel() {
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
		g.drawImage(terrain.getTileSamples(0), 0, 0, 16*SCALING_CONST, 16*SCALING_CONST, null);
		g.drawImage(c.getSprite(), b.getPos_x(), b.getPos_y(), c.getSpriteWidth()*2, c.getSpriteHeight()*2, null);
	
	}

	@Override
	public void run() {
		while(true) {
			updatePos();
			repaint();
			System.out.println("Running...");
			
			//Lo sleep lancia un'eccezione non gestita
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//Funzione per modificare posizione del personaggio, delle bombe, dei nemici, all'interno di un loop
	public void updatePos() {
		if (controls.isUp() == true && 	b.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			b.up();
			c.setNextUp();
		}
		else if (controls.isDown() == true && b.getPos_y()+c.getSpriteHeight()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			b.down();
			c.setNextDown();
		}
		else if (controls.isLeft() == true && b.getPos_x()-Bomberman.getMoveSpeed() >= 0) {
			b.left();
			c.setNextLeft();
		}
		else if (controls.isRight() == true && b.getPos_x()+c.getSpriteWidth()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			b.right();
			c.setNextRight();
		}
	}
	
	
}
