package View;

import Controller.*;
import Model.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;


public class GamePanel extends JPanel implements Runnable {
	
	// Dimensioni del panel e dei Tiles
	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	//Tutti i dati relativi alle bombe: lista di tutte le bombe attive in un dato momento, le view associate ai modelli
	//e un timer utilizzato per prevenire il piazzamento sequenziale troppo velocemente di diverse bombe
	ArrayList<BombModel> placedBombs = new ArrayList<BombModel>();
	BombView bombView = new BombView();
	private int bombTimer = 0;

	//istance dell'unico possibile modello di Bomberman e della view associata
	Bomberman b = Bomberman.getInstance();
	BombermanView c = new BombermanView();
	
	//Dati relativi ai nemici, modelli, view associate e ----da aggiungere--- lista di tutti i nemici sulla mappa
	Enemy e = new Enemy();
	EnemyView ev = new EnemyView();
	
	//Dati per la creazione della mappa, un array di TileModel che rappresenta la struttura della mappa
	//sotto forma di matrice di tiles, una view che contiene tutti i diversi tiles istanziata a partire da un nome di una mappa e da 
	//una configurazione che contiene tutti i valori dei tiles sui quali rimuovere la collision. 
	TileView terrain = new TileView("green_village");
	int[] config = {1};
	MapModel map = new MapModel("src/resources/map.txt", config);
	TileModel[][] map_structure = map.getMapStructure();
	ControlsHandler controls;
	
	//Tile da aggiornare per le collisioni
	Map<TileModel, Coordinates> tiles_to_update = new HashMap<TileModel, Coordinates>();
	
	//getter e setters per le varie dimensioni del pannello di gioco
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}
	
	
	
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
	
	
	//Override del paintComponent del GamePanel per disegnare in ordine sequenziale tutto il necessario del gioco, dal Bomberman, alle bombe
	//a tutti i nemici
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D)g;
		super.paintComponent(gg);
		terrain.drawTile(g, map_structure);
		for (BombModel bomb : placedBombs) {
			bombView.drawBomb(g, bomb.getPos_x(), bomb.getPos_y());
		}
		g.drawImage(c.getSprite(), b.getPos_x()+ev.getSpriteWidth()/2, b.getPos_y(), ev.getSpriteWidth()*2, ev.getSpriteHeight()*2, null);
		g.drawImage(ev.getSprite(), e.getPos_x()+ev.getSpriteWidth()/2, e.getPos_y(), ev.getSpriteWidth()*2, ev.getSpriteHeight()*2, null);
//		g.drawRect(e.getPos_x(), e.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE);
		drawBombs(g);
		updateMap(g);

	}
	
	
	/*
	Funzione per l'avvio di un thread separato rispetto a quello di swing associato al GamePanel. Questo thread rappresenta il ciclo di gioco 
	che si ripete ogni determinata quantità di tempo (da regolare) e aggiorna tutte le entità sulla mappa e la mappa stessa, dopodiché ridisegna il 
	pannello di gioco aggiornato.
	 */
	@Override
	public void run() {
		
		while(true) {
			updatePos();
			updateEnemyPos();
			updateBombTimer();
			placeBomb();
			explodeBlocks();
			repaint();
			
			//Lo sleep lancia un'eccezione non gestita
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 * funzione che controlla se due punti (che in genere rappresentano gli estremi di una 
	 * Hitbox) si trovano in un tile con la collision attiva. Ritorna un valore
	 * false se e solo se entrambi i punti NON si trovano in un tile con la collision attiva, e quindi non c'è collisione
	 */
	
	private boolean checkCollision(int corner1_x, int corner1_y, int corner2_x, int corner2_y) {
		
		int corner1_tile_x = corner1_x/(FINAL_TILE_SIZE);
		int corner1_tile_y = corner1_y/(FINAL_TILE_SIZE);
		int corner2_tile_x = corner2_x/(FINAL_TILE_SIZE);
		int corner2_tile_y = corner2_y/(FINAL_TILE_SIZE);
		boolean canPass1 = !this.map_structure[corner1_tile_y][corner1_tile_x].getCollision();
		boolean canPass2 = !this.map_structure[corner2_tile_y][corner2_tile_x].getCollision();
		if (canPass1 && canPass2) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/*
	 * Funzione per aggiornare la posizione di un'entità nemica. Si generano le dimensioni di una Hitbox relativa al nemico e si fanno
	 * i dovuti controlli in base alla direzione. Si controlla per ogni direzione che non si incappi in un tile che ha la collisione attiva utilizzando
	 * checkCollision. Viene chiamata in seguito la funzione move() della classe Enemy che muove effettivamente il personaggio se e solo se non è stato
	 * incontrato un tile con collisione attiva. Se il personaggio è stato effettivamente mosso si aggiornano anche le animazioni.
	 */
	public void updateEnemyPos() {
		//creazione hitbox che è minore di un tile di un pixel in tutte le direzioni, così da poter passare perfettamente tra due tiles.
		int HitBoxUpperLeft_x = e.getPos_x()+1;
		int HitBoxUpperLeft_y = e.getPos_y()+1;
		int HitBoxUpperRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxUpperRight_y = e.getPos_y()+1;
		int HitBoxBottomLeft_x = e.getPos_x()+1;
		int HitBoxBottomLeft_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxBottomRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxBottomRight_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		//Quattro controlli per le quattro possibili direzioni
		
		//Si controlla innanzitutto se in base alla direzioni non si finisce fuori dai confini della mappa
		if (e.dir == 'u' && 	e.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			//Si verifica se, prevedendo il movimento successivo, non si incappi in un tile con collision, segnalandolo nel campo hitObstacle di Enemy. 
			e.hitObstacle= checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
			//Il movimento eseguito dalla chiamata move() viene eseguito solo se hitObstacle = false.
			e.move();
			//L'animazione della view viene aggiornata solo se il personaggio si è effettivamente mosso
			if (!e.hitObstacle) {
				ev.setNextUp();				
			}
		}
		else if (e.dir == 'd' && e.getPos_y()+ev.getSpriteHeight()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			e.hitObstacle = checkCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getMoveSpeed());			
			e.move();
			if (!e.hitObstacle) {
				ev.setNextDown();				
			}
		}
		else if (e.dir == 'l' && e.getPos_x()-Bomberman.getMoveSpeed() >= 0) {
			e.hitObstacle = checkCollision(HitBoxUpperLeft_x - Bomberman.getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getMoveSpeed(), HitBoxBottomLeft_y);
			e.move();
			if (!e.hitObstacle) {
				ev.setNextLeft();				
			}
		}
		else if (e.dir == 'r' && e.getPos_x()+ev.getSpriteWidth()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			System.out.println("updating pos right");
			e.hitObstacle = checkCollision(HitBoxUpperRight_x + Bomberman.getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getMoveSpeed(), HitBoxBottomRight_y);
			e.move();
			if (!e.hitObstacle) {
				ev.setNextRight();				
			}
		}
		//Se la direzione di movimento non era valida (e quindi non rientra in nessuna delle 4 condizioni precedenti) si cambia direzione.
		else {
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
//		int border_const = 5;
//		int HitBoxUpperLeft_x = b.getPos_x()+10;
//		int HitBoxUpperLeft_y = b.getPos_y() + c.getSpriteHeight();
//		int HitBoxUpperRight_x = b.getPos_x() + c.getSpriteWidth()*2-border_const*2;
//		int HitBoxUpperRight_y = b.getPos_y() + c.getSpriteHeight();
//		int HitBoxBottomLeft_x = b.getPos_x()+border_const*2;
//		int HitBoxBottomLeft_y = b.getPos_y() + c.getSpriteHeight()*2;
//		int HitBoxBottomRight_x = b.getPos_x() + c.getSpriteWidth()*2-border_const*2;
//		int HitBoxBottomRight_y = b.getPos_y() + c.getSpriteHeight()*2;
		int HitBoxUpperLeft_x = b.getPos_x()+ev.getSpriteWidth()/2;
		int HitBoxUpperLeft_y = b.getPos_y()+10;
		int HitBoxUpperRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-ev.getSpriteWidth()/2-1;
		int HitBoxUpperRight_y = b.getPos_y()+10;
		int HitBoxBottomLeft_x = b.getPos_x()+ev.getSpriteWidth()/2;
		int HitBoxBottomLeft_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		int HitBoxBottomRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-ev.getSpriteWidth()/2-1;
		int HitBoxBottomRight_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		if (controls.isUp() == true && 	b.getPos_y()-Bomberman.getMoveSpeed() >= 0) {
			boolean canMove = !checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getMoveSpeed());
			if (canMove) {
				b.up();
				c.setNextUp();				
			}
		}
		else if (controls.isDown() == true && b.getPos_y()+c.getSpriteHeight()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			boolean canMove = !checkCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getMoveSpeed());
			if (canMove) {				
				b.down();
				c.setNextDown();
			}
		}
		else if (controls.isLeft() == true && b.getPos_x()-Bomberman.getMoveSpeed() >= 0) {
			boolean canMove = !checkCollision(HitBoxUpperLeft_x - Bomberman.getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getMoveSpeed(), HitBoxBottomLeft_y);
			if (canMove) {
				b.left();
				c.setNextLeft();				
			}
			
		}
		else if (controls.isRight() == true && b.getPos_x()+c.getSpriteWidth()*2+Bomberman.getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			boolean canMove = !checkCollision(HitBoxUpperRight_x + Bomberman.getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getMoveSpeed(), HitBoxBottomRight_y);
			if (canMove) {
				b.right();
				c.setNextRight();			
			}
			
		}
	}
	
	
	
	
	
	public void explodeBlocks() {
		for (TileModel tile : tiles_to_update.keySet()) {
			if (tile.destruction_counter == 0){
				tile.setModel_num(1);
				tile.setCollision(false);	
			}
			else {
				tile.destruction_counter--;
			}
		}
	}
	public void updateMap(Graphics g) {
		for (TileModel tile : this.tiles_to_update.keySet()) {
			BufferedImage tile_image = terrain.getTileSamples(tile.getModel_num()-1);
			g.drawImage(tile_image, this.tiles_to_update.get(tile).i, this.tiles_to_update.get(tile).i, FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);
		}
	}
	
	/*
	 * Funzione che piazza la bomba in seguito alla pressione della spacebar (aggiungendola alla lista di bombe che verranno disegnate nel ciclo di gioco)
	 */
	public void placeBomb() {
		
		if (controls.isSpace()) {
			
			//Valori calcolati per fare in modo che la bomba venga disegnata allineata con un tile
			
			int b_center_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE/2;
			int b_center_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE/2;
			int bomb_aligned_x = b_center_x - b_center_x%GamePanel.FINAL_TILE_SIZE;
			int bomb_aligned_y = b_center_y - b_center_y%GamePanel.FINAL_TILE_SIZE;
			
			//Si utilizza un timer per evitare di piazzare troppe bombe in un determinato istante di tempo. La bomba viene piazzata solo se il timer è giunto allo zero
			if (bombTimer <= 0) {
				placedBombs.add(new BombModel(bomb_aligned_x, bomb_aligned_y));
				//Si riavvia il timer dopo il piazzamento
				bombTimer = 100;
			}
		}
		//Si decrementa il timer se non è stata pizzata nessuna bomba
		else {				
			bombTimer -= 10; 
		}
		
	}
	
	
	public void drawBombs(Graphics g) {
		for (BombModel b : placedBombs) {
			
			int b_tile_col = b.getPos_x()/FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/FINAL_TILE_SIZE;
			boolean left_going = true;
			
			if (b.hasExploded()) {
				//g.drawImage(bombView.explosionSprite, b.getPos_x()-96, b.getPos_y()-96, 5*FINAL_TILE_SIZE, 5*FINAL_TILE_SIZE, null);
				g.drawImage(bombView.centralExplosionSprite, b.getPos_x(), b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
				for (int j = 0; j < 2; j++) {
					if (b_tile_row-(j+1) >= 0 && b_tile_row-(j+1) < Y_TILES && b_tile_col >= 0 && b_tile_col < X_TILES) {
						if (this.map_structure[b_tile_row-(j+1)][b_tile_col].getModel_num() != 1) {
							
							tiles_to_update.put(this.map_structure[b_tile_row-(j+1)][b_tile_col], new Coordinates(b_tile_row-(j+1), b_tile_col));
							break;
						}
						else {
							g.drawImage(bombView.explosionMatrix[0][j], b.getPos_x(), b.getPos_y()-(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
						}
					}
				}
				for (int j = 0; j < 2; j++) {
					if (b_tile_row >= 0 && b_tile_row < Y_TILES && b_tile_col+j+1 >= 0 && b_tile_col+j+1 < X_TILES) {
						if (this.map_structure[b_tile_row][b_tile_col+j+1].getModel_num() != 1) {
							tiles_to_update.put(this.map_structure[b_tile_row][b_tile_col+j+1], new Coordinates(b_tile_row,b_tile_col+j+1));
							break;							
						}
						else {
							g.drawImage(bombView.explosionMatrix[1][j], b.getPos_x()+(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
						}
					}
				}
				for (int j = 0; j < 2; j++) {
					if (b_tile_row+j+1 >= 0 && b_tile_row+j+1 < Y_TILES && b_tile_col >= 0 && b_tile_col < X_TILES) {
						
						if (this.map_structure[b_tile_row+j+1][b_tile_col].getModel_num() != 1) {
							tiles_to_update.put(this.map_structure[b_tile_row+j+1][b_tile_col], new Coordinates(b_tile_row+j+1,b_tile_col));
							break;
							
						}
						else {
							g.drawImage(bombView.explosionMatrix[2][j], b.getPos_x(), b.getPos_y()+(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
						}
					}
				}
				for (int j = 0; j < 2; j++) {
					if (b_tile_row >= 0 && b_tile_row < Y_TILES && b_tile_col-(j+1) >= 0 && b_tile_col-(j+1) < X_TILES && left_going) {	
						if (this.map_structure[b_tile_row][b_tile_col-(j+1)].getModel_num() != 1) {
							
							tiles_to_update.put(this.map_structure[b_tile_row][b_tile_col-(j+1)], new Coordinates(b_tile_row+j+1,b_tile_col));
							left_going = false;
						}
						else {
							g.drawImage(bombView.explosionMatrix[3][j], b.getPos_x()-(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
						}
					}
					else {
						break;
					}
				}
			}
			else {
				g.drawImage(bombView.bombSprite, b.getPos_x(), b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);

			}

			
			
		}
	}
	
	
	
	
	//Creiamo una coda di bombe. Poi per ogni bomba nella coda facciamo partire la funzione fireFuse che aggiorna
	//continuamente il timer della miccia :)
	

    public void updateBombTimer() {
        // Aggiorna il timer di ogni bomba attiva
    	 //placeholder, deve essere quello di ogni singola bomba preso dal bombmodel
        for (Iterator<BombModel> iterator = placedBombs.iterator(); iterator.hasNext();) {
            BombModel bomba = iterator.next();
            bomba.fireFuse();

            if (bomba.hasExpired()) {    
                iterator.remove(); 
            }
        }
    }
    

	
	
}

//Segnalare su ogni tile con delle variabili se è presente una bomba o un nemico così da poter lavorare con la collision tra nemici
