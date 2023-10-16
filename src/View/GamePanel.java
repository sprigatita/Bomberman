package View;

import Controller.*;
import java.util.Random;
import Model.*;
import Model.Character;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import javax.sound.sampled.AudioInputStream;
import View.*;

public class GamePanel extends JPanel implements Runnable {
	
	// Dimensioni del panel e dei Tiles
	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	private Random random_gen = new Random();
	public FinestraDiGioco fdg;
	public Menu menu = new Menu();
	AudioManager audio_samples = new AudioManager();
	EntityInstantiator enemies = new EntityInstantiator("src/resources/enemies.txt");
	ArrayList<Character> damageableCharacters = new ArrayList<Character>();
	ArrayList<Character> moveableCharacters = new ArrayList<Character>();
//	HashMap<Character, EntityView> characterModelsView = new HashMap<Character, EntityView>();
	HashMap<Character, EntityView> characterModelsView = this.enemies.characterModelsView;
	public HashMap<String, EntityView> modelViews = new HashMap<String, EntityView>();
	PowerUpView powerUpIcons = new PowerUpView();
	BufferedImage projectile;
	//Tutti i dati relativi alle bombe: lista di tutte le bombe attive in un dato momento, le view associate ai modelli
	//e un timer utilizzato per prevenire il piazzamento sequenziale troppo velocemente di diverse bombe
	
	//placedBombs associa in ogni momento ad ogni bomba piazzata un set di tutti i tile che contengono le fiamme scatenate dall'esplosione di quella precisa
	//bomba
	
//	HashMap<TileModel, Integer> laser_tiles = new HashMap<TileModel, Integer>();
	HashMap<TileModel, LaserUtil> laser_tiles = this.enemies.laser_tiles;
	HashMap<Direction, BufferedImage> laser_views = new HashMap<Direction, BufferedImage>();
	
	boolean next_level = false;
	boolean game_over = false;
	int current_level = 0;
	boolean level_over = false;
	boolean pause = false;
	int pause_timer = 0;
	
//	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Projectile> projectiles = this.enemies.projectiles;
//	ArrayList<TrapModel> traps = new ArrayList<TrapModel>();
	ArrayList<TrapModel> traps = this.enemies.traps;
	HashMap<BombModel, HashSet<TileModel>> placedBombs = new HashMap<BombModel, HashSet<TileModel>>();
	BombView bombView = new BombView();
	private int bombTimer = 0;
	EnemyView ew = new EnemyView();

	ArrayList<PowerUpModel> powerUpList = new ArrayList<PowerUpModel>();
	
	//Dati relativi al giocatore, ai nemici, modelli, view associate e ----da aggiungere--- lista di tutti i nemici sulla mappa
	User currentUser = new User();
	
	
	//Dati per la creazione della mappa, un array di TileModel che rappresenta la struttura della mappa
	//sotto forma di matrice di tiles, una view che contiene tutti i diversi tiles istanziata a partire da un nome di una mappa e da 
	//una configurazione che contiene tutti i valori dei tiles sui quali rimuovere la collision o rimuovere la distruttibilità. 
	TileView[] maps = new TileView[5];
	TileView terrain;
	//i seguenti due array andranno ricavati da un file txt di configurazione, sono momentaneamente impostati a mano per testare
	int[][] collision_config_list = {
			{1},
			{1},
			{}
	};
	
	int[] collision_config = {1};
	int[][] destructible_config_list = {
			{14,12,8,9, 10, 11, 2,6,7,3,15, 17,16,18,13, 19, 22, 23},
			{12,2,3,4,5,6,7,8,9,10,11,1,13,14,16,17},
			{}		
	};
//	int[] destructible_config = {14,12,8,9, 10, 11, 2,6,7,3,15, 17,16,18,13, 19, 22, 23};
	int[] destructible_config = {12,2,3,4,5,6,7,8,9,10,11,1,13,14,16,17};
	
	int[][] border_config_list = {
			{12,8,9,24,13,14,2,6,7,21,3,15},
			{12,2,3,4,5,6,11,7,16,17,5,13,10,9},
			{}
	};
//	int[] border_config = {12,8,9,24,13,14,2,6,7,21,3,15};
	int[] border_config = {12,2,3,4,5,6,11,7,16,17,5,13,10,9};
	int[] myIntArray = new int[3];
	String[] levels = new String[2];
	String[] EntityInit = new String[2];
	MapModel map;
	
	TileModel[][] map_structure;
	ControlsHandler controls;
	
	//Tile da aggiornare in seguito alla collisione con un'esplosione. E' momentaneamente necessario l'uso di una mappa con un oggetto di tipo
	//Coordinates per tener conto delle coordinate del tile da aggiornare. Si può semplificare aggiornando TileModel così da immagazzinare lui stesso 
	//le coordinate del tile a cui si riferisce.
	Map<TileModel, Coordinates> tiles_to_update = new HashMap<TileModel, Coordinates>();
	private int death_screen_timer = -1;
	
	
	//getter e setters per le varie dimensioni del pannello di gioco
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}
	
	private void instantiateMaps() {
		this.maps[0] = new TileView("green_village", 24, ".png");
		this.maps[1] = new TileView("blue_castle", 17, ".jpg");
		this.terrain = this.maps[current_level];
		this.map = new MapModel(this.levels[0], collision_config_list[0], destructible_config_list[0], border_config_list[0]);
		this.map_structure = map.getMapStructure();
	}
	
	private void instantiateLevels() {
		levels[0] = "src/resources/map.txt";
		levels[1] = "src/resources/map1.txt";
	}
	
	private void instantiateEnemies() {
		
		try {
			this.projectile = ImageIO.read(new File("src/resources/enemy/projectile.png"));
			BufferedImage laser_1 = ImageIO.read(new File("src/resources/enemy/Laserer/laser_1.png"));
			BufferedImage laser_2 = ImageIO.read(new File("src/resources/enemy/Laserer/laser_2.png"));
			this.laser_views.put(Direction.UP,laser_2);
			this.laser_views.put(Direction.DOWN,laser_2);
			this.laser_views.put(Direction.LEFT,laser_1);
			this.laser_views.put(Direction.RIGHT,laser_1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.EntityInit[0] = "src/resources/enemies.txt";
		this.EntityInit[1] = "src/resources/enemies1.txt";
	}
	
	public void changeLevel() {
//		this.current_level++;
		System.out.println(current_level);
//		map = new MapModel("src/resources/map2.txt", collision_config, destructible_config, border_config);
		this.map = new MapModel(this.levels[current_level], collision_config_list[current_level], destructible_config_list[current_level], border_config_list[current_level]);
		this.map_structure = map.getMapStructure();
		this.enemies = new EntityInstantiator(this.EntityInit[this.current_level]);
		this.terrain = this.maps[this.current_level];
		damageableCharacters = new ArrayList<Character>();
		moveableCharacters = new ArrayList<Character>();
		characterModelsView = this.enemies.characterModelsView;
		projectiles = this.enemies.projectiles;
		traps = this.enemies.traps;
		laser_tiles = this.enemies.laser_tiles;
		placedBombs = new HashMap<BombModel, HashSet<TileModel>>();
		powerUpList = new ArrayList<PowerUpModel>();
	}
	
	private void instantiateCharacters() {
		//istance dell'unico possibile modello di Bomberman e della view associata
		
		Bomberman b = Bomberman.getInstance();
		BombermanView bw = new BombermanView();
		b.addObserver(bw);
		b.setPos_x(480);
		b.setPos_y(480);
		b.setHealth(1);
//		Enemy e = new Walker();
//		Enemy e2 = new Laserer(96,96,Direction.DOWN, this.laser_tiles);
//		Enemy e3 = new Shooter(projectiles);
//		Enemy boss = new FatBoss();
//		Boss1View bw = new Boss1View();
//		EnemyView ev = new WalkerView();
//		this.moveableCharacters.add(e2);
////		this.moveableCharacters.add(e3);
////		this.moveableCharacters.add(e);
		this.moveableCharacters.add(b);
//		this.moveableCharacters.add(boss);
//		this.damageableCharacters.add(b);
////		this.damageableCharacters.add(e);
////		this.damageableCharacters.add(e2);
////		this.damageableCharacters.add(e3);
//		b.addObserver(bw);
//		e.addObserver(ev);
//		boss.addObserver(bw);
//		this.characterModelsView.put(e2, ev);
		this.characterModelsView.put(b, bw);
//		this.characterModelsView.put(e, ev);
//		this.characterModelsView.put(e3, ev);
//		this.characterModelsView.put(boss, bw);
//		b.setHealth(5);
		for (Character c : this.enemies.chars) {
			EntityView ew = this.characterModelsView.get(c);
			this.moveableCharacters.add(c);
			this.characterModelsView.put(c, ew);
			c.addObserver(ew);
			this.damageableCharacters.add(c);
		}
		System.out.println(this.moveableCharacters.size());
	}
	
	//All'interno del costruttore creiamo il thread, lo facciamo partire ed inizializziamo tutti gli handler e le caratteristiche del panel.
	public GamePanel() {
		this.instantiateLevels();
		this.instantiateMaps();
		this.instantiateEnemies();
		this.instantiateCharacters();
		this.setPreferredSize(new Dimension((X_TILES*FINAL_TILE_SIZE),(Y_TILES*FINAL_TILE_SIZE)));
		this.setBackground(new Color(107, 106, 104));
		controls = new ControlsHandler();
		this.addKeyListener(controls);
		this.setFocusable(true);
		audio_samples.clips.get(0).start();
		audio_samples.setVolume(audio_samples.clips.get(0), 0.1f);
		Thread t = new Thread(this);
		t.start();
	}
	
	
	public void drawTraps(Graphics g) {
		for (Iterator<TrapModel> iterator = this.traps.iterator(); iterator.hasNext();) {
			TrapModel t = iterator.next();
			Bomberman b = Bomberman.getInstance();
			int b_center_x = b.getPos_x() + FINAL_TILE_SIZE/2;
			int b_center_y = b.getPos_y() + FINAL_TILE_SIZE/2;
			int row = b_center_y/FINAL_TILE_SIZE;
			int col = b_center_x/FINAL_TILE_SIZE;
			if (t.getRow()  == row && t.getCol() == col) {
				b.damage();
				iterator.remove();
			}
			if (t.getDuration() >= 0) {
				t.setDuration(t.getDuration()-10);
			}
			else {
				iterator.remove();
			}
			System.out.println(traps.size());
			g.drawImage(ew.sprite, t.getCol()*FINAL_TILE_SIZE, t.getRow()*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);
		}
	}
	
	
	public void drawProjectiles(Graphics g) {
		for (Iterator<Projectile> iterator = this.projectiles.iterator(); iterator.hasNext();) {
			Projectile t = iterator.next();
//			Bomberman b = Bomberman.getInstance();
//			int b_center_x = b.getPos_x() + FINAL_TILE_SIZE/2;
//			int b_center_y = b.getPos_y() + FINAL_TILE_SIZE/2;
//			int row = b_center_y/FINAL_TILE_SIZE;
//			int col = b_center_x/FINAL_TILE_SIZE;
//			if (t.getRow()  == row && t.getCol() == col) {
//				b.damage();
//				iterator.remove();
//			}
//			if (t.getDuration() >= 0) {
//				t.setDuration(t.getDuration()-10);
//			}
//			else {
//				iterator.remove();
//			}
//			System.out.println(traps.size());
			g.drawImage(projectile, t.getPos_x(), t.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);
		}
	}
	
	
	//Override del paintComponent del GamePanel per disegnare in ordine sequenziale tutto il necessario del gioco, dal Bomberman, alle bombe
	//a tutti i nemici
	@Override
	public void paintComponent(Graphics g) {
		
		if (this.game_over) {
			if (this.controls.isEnter()) {
				this.game_over = false;
				this.audio_samples.play(0);
			}
//			g.fillRect(0, 0, this.getPanelWidth(), this.getPanelHeight());
			g.drawImage(this.menu.game_over, 0, 0, GamePanel.getPanelWidth(), GamePanel.getPanelHeight(), null);
//			g.drawString("YOU DIED. PRESS ENTER TO RESTART THE LEVEL", 300,300);
//			this.death_screen_timer-=1;

		}
		else if (this.next_level) {
			if (this.controls.isEnter()) {
				this.next_level = false;
			}
			g.fillRect(0, 0, this.getPanelWidth(), this.getPanelHeight());
//			this.death_screen_timer-=1;

		}
		else {
			
			Graphics2D gg = (Graphics2D)g;
			super.paintComponent(gg);
			terrain.drawTile(g, map_structure);
			
			drawBombs(g);
//		drawTraps(g);
			drawProjectiles(g);
			for (Character c : this.characterModelsView.keySet()) {
				if (c.isDead()) {
					g.drawImage(this.characterModelsView.get(c).getDeadSprite(c.getDeath_animation_counter()), c.getPos_x()+this.characterModelsView.get(c).getSpriteWidth()/2, c.getPos_y(), this.characterModelsView.get(c).getSpriteWidth()*2, this.characterModelsView.get(c).getSpriteHeight()*2, null);
					
				}
				else {
					if (c instanceof FatBoss) {
						g.drawImage(this.characterModelsView.get(c).getSprite(), c.getPos_x(), c.getPos_y(), this.characterModelsView.get(c).getSpriteWidth()*3, this.characterModelsView.get(c).getSpriteHeight()*3, null);
						g.drawRect(c.getPos_x(), c.getPos_y(), this.characterModelsView.get(c).getSpriteWidth()*3, this.characterModelsView.get(c).getSpriteHeight()*3);
						
					}
					else {
						
						g.drawImage(this.characterModelsView.get(c).getSprite(), c.getPos_x()+this.characterModelsView.get(c).getSpriteWidth()/2, c.getPos_y(), this.characterModelsView.get(c).getSpriteWidth()*2, this.characterModelsView.get(c).getSpriteHeight()*2, null);				
					}
				}
			}
			
			for (TileModel t : this.laser_tiles.keySet()) {
				int x = t.getMatrix_pos_col()*GamePanel.FINAL_TILE_SIZE;
				int y = t.getMatrix_pos_row()*GamePanel.FINAL_TILE_SIZE;
				System.out.println(this.laser_tiles.get(t).dir);
				g.drawImage(this.laser_views.get(this.laser_tiles.get(t).dir), x, y, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
				
			}
			
//			updateMap(g);
			drawPowerUps(g);
		}

	}
	
	
	/*
	Funzione per l'avvio di un thread separato rispetto a quello di swing associato al GamePanel. Questo thread rappresenta il ciclo di gioco 
	che si ripete ogni determinata quantità di tempo (da regolare) e aggiorna tutte le entità sulla mappa e la mappa stessa, dopodiché ridisegna il 
	pannello di gioco aggiornato.
	 */
	@Override
	public void run() {
		
		while(true) {
			if (this.game_over) {
				repaint();
				continue;
			}
			if (this.next_level) {
				repaint();
				continue;
			}
			if(Bomberman.getInstance().isReallyDead()) {
				System.out.println("ded");
				this.audio_samples.clips.get(0).stop();
				Bomberman.getInstance().revive();
				this.changeLevel();
				this.instantiateCharacters();
				this.game_over = true;
			}
			else if (this.moveableCharacters.size() == 1 && this.moveableCharacters.get(0) instanceof Bomberman) {
				System.out.println("changed level");
				this.current_level+=1;
				this.changeLevel();
				this.instantiateCharacters();
				this.next_level = true;
				
			}
//			if (levelcounter == 100) {
//				this.changeLevel();
//				this.instantiateCharacters();
//				
//			}
			
//			
	
//			if (level_over) {
//				this.map_structure 
//			}
//			if (this.controls.isSpace()) {
//				System.out.println(pause_timer);
//				if (pause_timer <= 0) {
//					System.out.println("pressed space");
//					if (this.pause) {
//						this.pause = false;
//					}
//					else {
//						this.pause = true;
//					}
//					pause_timer = 100000;
//				}
//				else {
//					pause_timer -= 1;
//				}
//			}
//			if (pause) {
//				System.out.println("pause");
//				continue;
//			}
			
//			for (Character c : this.characterModelsView.keySet()) {
//				if (!c.isDead()) {
//					if (c instanceof Enemy) {
//						Enemy e = (Enemy)c;
//						e.move(FINAL_TILE_SIZE, map_structure, controls);
////						updateEnemyPos(e);
//					}
//					if (c instanceof Bomberman) {
////						updatePos();
//						Bomberman.getInstance().move(FINAL_TILE_SIZE, map_structure, controls);
//					}
//				}
//			}
			for (Character c : this.moveableCharacters) {
				c.move(FINAL_TILE_SIZE, map_structure, controls);
			}
			for (Projectile p : this.projectiles) {
				p.move(FINAL_TILE_SIZE, map_structure, controls);
				
			}
			
			manageLasers();
			manageProjectiles();
			kickBombs();
			slideBombs();
			checkPowerUp();
			updateBombTimer();
			placeBomb();
			explodeBlocks();
			manageDeadCharacters();
			repaint();
			
			//Lo sleep lancia un'eccezione non gestita
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
	private void manageLasers() {
		 for (Iterator<Map.Entry<TileModel, LaserUtil>> iterator = this.laser_tiles.entrySet().iterator(); iterator.hasNext();) {
	            TileModel t = iterator.next().getKey();
	            if ( this.laser_tiles.get(t).i <= 0) {  
	            	iterator.remove();
	            }
	           	else {
	            	this.laser_tiles.put(t, new LaserUtil(this.laser_tiles.get(t).i - 1,this.laser_tiles.get(t).dir ));
	           	} 
	        }
	
		
	}

	private void checkPowerUp() {
		
		int row_tile1 = (Bomberman.getInstance().getPos_y()+10)/FINAL_TILE_SIZE;
		int col_tile1 = (Bomberman.getInstance().getPos_x()+10)/FINAL_TILE_SIZE;
		int row_tile2 = (Bomberman.getInstance().getPos_y()+ FINAL_TILE_SIZE-10)/FINAL_TILE_SIZE;
		int col_tile2 = (Bomberman.getInstance().getPos_x()+10)/FINAL_TILE_SIZE;
		int row_tile3 = Bomberman.getInstance().getPos_y()/FINAL_TILE_SIZE;
		int col_tile3 = (Bomberman.getInstance().getPos_x()+FINAL_TILE_SIZE-10)/FINAL_TILE_SIZE;
		int row_tile4 = (Bomberman.getInstance().getPos_y()+FINAL_TILE_SIZE-10)/FINAL_TILE_SIZE;
		int col_tile4 = (Bomberman.getInstance().getPos_x()+FINAL_TILE_SIZE-10)/FINAL_TILE_SIZE;
		if (this.map_structure[row_tile1][col_tile1].containsPowerUp()){
			Bomberman.getInstance().resetPowerUps();
			checkPowerUpUtility(row_tile1, col_tile1);
		}
		else if (this.map_structure[row_tile2][col_tile2].containsPowerUp()){
			Bomberman.getInstance().resetPowerUps();
			checkPowerUpUtility(row_tile2, col_tile2);
		}
		else if (this.map_structure[row_tile3][col_tile3].containsPowerUp()){
			Bomberman.getInstance().resetPowerUps();
			checkPowerUpUtility(row_tile3, col_tile3);
		}
		else if (this.map_structure[row_tile4][col_tile4].containsPowerUp()){
			Bomberman.getInstance().resetPowerUps();
			checkPowerUpUtility(row_tile4, col_tile4);
		}
	}
	
	public void checkPowerUpUtility(int row_tile, int col_tile) {
		System.out.println("has power up");
		PowerUpModel power_up = this.map_structure[row_tile][col_tile].getPowerUp();
		Bomberman.getInstance().setPower_up(power_up);
		this.powerUpList.remove(power_up);
		this.map_structure[row_tile][col_tile].setPower_up(null);
		switch(power_up.getId()) {
		case 0:
			Bomberman.getInstance().setShield();
			break;
		case 1:
			Bomberman.getInstance().setMoveSpeed(6);
			break;
		case 2:
			Bomberman.getInstance().setKicksBombs();
			break;
		case 5:
			Bomberman.getInstance().setGhosting_timer(200);
			break;
		case 6:
			Bomberman.getInstance().increaseExplosionRange();
			break;
		default:
		}
	}
	
	public void kickBombs() {
		Bomberman b = Bomberman.getInstance();
		BombModel placedBomb = null;
		int bomberman_row = 0;
		int bomberman_col = 0;
		Coordinates[] hit_box = b.hitBox(GamePanel.FINAL_TILE_SIZE);
		ArrayList<BombModel> bombs = new ArrayList<BombModel>();
		for (Coordinates c : hit_box) {
			bomberman_row = c.j/GamePanel.FINAL_TILE_SIZE;
			bomberman_col = c.i/GamePanel.FINAL_TILE_SIZE;
			BombModel bomb = this.map_structure[bomberman_row][bomberman_col].getPlacedBomb();
			if ( bomb != null) {
				bombs.add(bomb);
			}
		}
		for (BombModel bomb : bombs) {
			
			if (b.getPower_up() != null && !bomb.isCan_slide()  && b.getPower_up().getId() == 2 &&  controls.canKickBomb()) {
				System.out.println("test");
				bomb.setSlide_dir(b.getDir());
				bomb.setCan_slide(true);
			}
		}
	}
	
	public void slideBombs() {
		for (BombModel b : this.placedBombs.keySet()) {
			b.slide(Bomberman.getInstance().getDir(), this.map_structure);
		}
	}

	/*
	 * funzione che controlla se due punti (che in genere rappresentano gli estremi di una 
	 * Hitbox) si trovano in un tile con la collision attiva. Ritorna un valore
	 * false se e solo se entrambi i punti NON si trovano in un tile con la collision attiva, e quindi non c'è collisione
	 */
	
	private boolean checkCollision(int corner1_x, int corner1_y, int corner2_x, int corner2_y) {
//		PowerUpModel power_up = Bomberman.getInstance().getPower_up();
//		boolean ghosting = power_up != null && power_up.getId() == 5;
//		boolean can_ghost = false;
//		for (Coordinates c : Bomberman.getInstance().hitBox(GamePanel.FINAL_TILE_SIZE)) {
//			int c_row = c.j/GamePanel.FINAL_TILE_SIZE;
//			int c_col = c.i/GamePanel.FINAL_TILE_SIZE;
//			if (this.map_structure[c_row][c_col].getCollision()) {
//				System.out.println("test");
//				can_ghost = true;
//				break;
//			}
//		}
//		if (Bomberman.getInstance().getGhosting_timer() > 0) {
//			Bomberman.getInstance().decreaseGhosting_timer();
//			System.out.println(Bomberman.getInstance().getGhosting_timer());
//			can_ghost = true;
//		}
//		else {
//			ghosting = false;
//		}
//		can_ghost = can_ghost || ghosting;
//		System.out.println("ghosting = " + ghosting);
//		System.out.println("can ghost = " + can_ghost);
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
	 * Funzione analoga a checkCollision, ma con un controllo in più per la collisione con le bombe. 
	 * Nota: si può migliorare fondendo le due funzioni, passandogli l'oggetto su cui va effettuato il controllo di collisione ed effettuare
	 * il controllo della bomba solo per determinati tipi di oggetti (come i nemici). Così si utilizza solo una funzione e si evita di riscrivere lo stesso
	 * codice
	 */
	
	private boolean checkEnemyCollision(int corner1_x, int corner1_y, int corner2_x, int corner2_y) {
		int corner1_tile_x = corner1_x/(FINAL_TILE_SIZE);
		int corner1_tile_y = corner1_y/(FINAL_TILE_SIZE);
		int corner2_tile_x = corner2_x/(FINAL_TILE_SIZE);
		int corner2_tile_y = corner2_y/(FINAL_TILE_SIZE);
		boolean canPass1 = !this.map_structure[corner1_tile_y][corner1_tile_x].getCollision();
		boolean canPass2 = !this.map_structure[corner2_tile_y][corner2_tile_x].getCollision();
		boolean canPass3 = this.map_structure[corner2_tile_y][corner2_tile_x].getPlacedBomb() == null;
		if (canPass1 && canPass2 && canPass3) {
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
//	public void updateEnemyPos(Enemy e) {
//		EnemyView ev = (EnemyView)this.characterModelsView.get(e);
//		//creazione hitbox che è minore di un tile di un pixel in tutte le direzioni, così da poter passare perfettamente tra due tiles.
//		int HitBoxUpperLeft_x = e.getPos_x()+1;
//		int HitBoxUpperLeft_y = e.getPos_y()+1;
//		int HitBoxUpperRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxUpperRight_y = e.getPos_y()+1;
//		int HitBoxBottomLeft_x = e.getPos_x()+1;
//		int HitBoxBottomLeft_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxBottomRight_x = e.getPos_x() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxBottomRight_y = e.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
//		//Quattro controlli per le quattro possibili direzioni
//		
//		//Si controlla innanzitutto se in base alla direzioni non si finisce fuori dai confini della mappa
//		if (e.dir == 'u' && 	e.getPos_y()-Bomberman.getInstance().getMoveSpeed() >= 0) {
//			//Si verifica se, prevedendo il movimento successivo, non si incappi in un tile con collision, segnalandolo nel campo hitObstacle di Enemy. 
//			e.hitObstacle= checkEnemyCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getInstance().getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getInstance().getMoveSpeed());
//			//Il movimento eseguito dalla chiamata move() viene eseguito solo se hitObstacle = false.
//			e.move();
//			//L'animazione della view viene aggiornata solo se il personaggio si è effettivamente mosso
//			if (!e.hitObstacle) {
//				ev.setNextUp();				
//			}
//		}
//		else if (e.dir == 'd' && e.getPos_y()+ev.getSpriteHeight()*2+Bomberman.getInstance().getMoveSpeed() <= 
//				GamePanel.getPanelHeight()) {
//			e.hitObstacle = checkEnemyCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getInstance().getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getInstance().getMoveSpeed());			
//			e.move();
//			if (!e.hitObstacle) {
//				ev.setNextDown();				
//			}
//		}
//		else if (e.dir == 'l' && e.getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
//			e.hitObstacle = checkEnemyCollision(HitBoxUpperLeft_x - Bomberman.getInstance().getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getInstance().getMoveSpeed(), HitBoxBottomLeft_y);
//			e.move();
//			if (!e.hitObstacle) {
//				ev.setNextLeft();				
//			}
//		}
//		else if (e.dir == 'r' && e.getPos_x()+ev.getSpriteWidth()*2+Bomberman.getInstance().getMoveSpeed() <= 
//				GamePanel.getPanelWidth())  {
//			e.hitObstacle = checkEnemyCollision(HitBoxUpperRight_x + Bomberman.getInstance().getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getInstance().getMoveSpeed(), HitBoxBottomRight_y);
//			e.move();
//			if (!e.hitObstacle) {
//				ev.setNextRight();				
//			}
//		}
//		//Se la direzione di movimento non era valida (e quindi non rientra in nessuna delle 4 condizioni precedenti) si cambia direzione.
//		else {
//			e.changeDir();
//			
//		}
		
		
//	}
	
	
	/*
	 * Funzione per aggiornare le posizioni.
	 * 
	 * Collision: viene chiamata checkCollision su una coppia di angoli di una HitBox (i due angoli superiori se si va in alto,
	 * i due angoli sinistri se si va a sinistra e così via..) La chiamata viene effettuata passandogli i valori degli angoli
	 * che si dovrebbero avere dopo il movimento (si fa una previsione). Se la funzione checkCollision ritorna true (e quindi
	 * nessuno dei due angoli finisce in un blocco con collisione) allora si può effettuare il movimento.
	 */
	public void updatePos() { //idealmente dovrebbe prendere come argomento un particolare Enemy ed essere chiamata su tutti gli enemy presenti nella mappa
		Bomberman b = Bomberman.getInstance();
		BombermanView bv = (BombermanView)this.characterModelsView.get(b);
//		int HitBoxUpperLeft_x = b.getPos_x()+bv.getSpriteWidth()/2;
//		int HitBoxUpperLeft_y = b.getPos_y()+10;
//		int HitBoxUpperRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-bv.getSpriteWidth()/2-1;
//		int HitBoxUpperRight_y = b.getPos_y()+10;
//		int HitBoxBottomLeft_x = b.getPos_x()+bv.getSpriteWidth()/2;
//		int HitBoxBottomLeft_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
//		int HitBoxBottomRight_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE-bv.getSpriteWidth()/2-1;
//		int HitBoxBottomRight_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE-1;
		int tile_size = GamePanel.FINAL_TILE_SIZE;
		int HitBoxUpperLeft_x = b.getPos_x()+tile_size/5;
		int HitBoxUpperLeft_y = b.getPos_y()+tile_size*2/5;
		int HitBoxUpperRight_x = b.getPos_x() + tile_size - tile_size/5;
		int HitBoxUpperRight_y = b.getPos_y()+tile_size*2/5;
		int HitBoxBottomLeft_x = b.getPos_x()+tile_size/5;
		int HitBoxBottomLeft_y = b.getPos_y()+tile_size-1;
		int HitBoxBottomRight_x = b.getPos_x() + tile_size - tile_size/5;
		int HitBoxBottomRight_y = b.getPos_y()+ tile_size-1;
		PowerUpModel power_up = b.getPower_up();
		boolean needs_to_ghost = false;
		for (Coordinates c : b.collisionHitBox(GamePanel.FINAL_TILE_SIZE)) {
			int c_row = c.j/GamePanel.FINAL_TILE_SIZE;
			int c_col = c.i/GamePanel.FINAL_TILE_SIZE;
			if (this.map_structure[c_row][c_col].getCollision()) {
				needs_to_ghost = true;
				break;
			}
		}
		boolean ghosting = false;
		if (power_up != null) {
			ghosting = power_up.getId() == 5;
		}
		if (b.getGhosting_timer() > 0) {
			b.decreaseGhosting_timer();
			System.out.println(b.getGhosting_timer());
		}
		else {
			ghosting = false;
		}
		if (controls.isUp() == true && 	b.getPos_y()-Bomberman.getInstance().getMoveSpeed() >= 0) {
			boolean canMove = !checkCollision(HitBoxUpperLeft_x, HitBoxUpperLeft_y - Bomberman.getInstance().getMoveSpeed(), HitBoxUpperRight_x, HitBoxUpperRight_y - Bomberman.getInstance().getMoveSpeed());
			if (canMove || ghosting || needs_to_ghost) {
				b.up();
				bv.setNextUp();				
			}
		}
		else if (controls.isDown() == true && b.getPos_y()+bv.getSpriteHeight()*2+Bomberman.getInstance().getMoveSpeed() <= 
				GamePanel.getPanelHeight()) {
			boolean canMove = !checkCollision(HitBoxBottomLeft_x, HitBoxBottomLeft_y + Bomberman.getInstance().getMoveSpeed(), HitBoxBottomRight_x, HitBoxBottomRight_y + Bomberman.getInstance().getMoveSpeed());
			if (canMove || ghosting || needs_to_ghost) {				
				b.down();
				bv.setNextDown();
			}
		}
		else if (controls.isLeft() == true && b.getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
			boolean canMove = !checkCollision(HitBoxUpperLeft_x - Bomberman.getInstance().getMoveSpeed(), HitBoxUpperLeft_y, HitBoxBottomLeft_x - Bomberman.getInstance().getMoveSpeed(), HitBoxBottomLeft_y);
			if (canMove || ghosting || needs_to_ghost) {
				b.left();
				bv.setNextLeft();				
			}
			
		}
		else if (controls.isRight() == true && b.getPos_x()+bv.getSpriteWidth()*2+Bomberman.getInstance().getMoveSpeed() <= 
				GamePanel.getPanelWidth())  {
			boolean canMove = !checkCollision(HitBoxUpperRight_x + Bomberman.getInstance().getMoveSpeed(), HitBoxUpperRight_y, HitBoxBottomRight_x + Bomberman.getInstance().getMoveSpeed(), HitBoxBottomRight_y);
			if (canMove || ghosting || needs_to_ghost) {
				b.right();
				bv.setNextRight();			
			}
			
		}
	}
	
	
	
	/*
	 * Funzione utilitaria per verificare se un'entità si trova in uno dei tile in cui è presente una fiamma. Ritorna 
	 * true se il personaggio incontra una fiamma.
	 */
	public void bombDamage() {
		for (Iterator<Character> iterator = this.damageableCharacters.iterator(); iterator.hasNext();){
			Character c = iterator.next();
			int HitBoxUpperLeft_x = (c.getPos_x()+10) / FINAL_TILE_SIZE;
			int HitBoxUpperLeft_y = (c.getPos_y()+10) / FINAL_TILE_SIZE;
			int HitBoxUpperRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
			int HitBoxUpperRight_y = (c.getPos_y()+10)/ FINAL_TILE_SIZE;
			int HitBoxBottomLeft_x = (c.getPos_x()+10)/ FINAL_TILE_SIZE;
			int HitBoxBottomLeft_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
			int HitBoxBottomRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
			int HitBoxBottomRight_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
			int row_pos = c.getPos_y()/FINAL_TILE_SIZE;
			int col_pos = c.getPos_x()/FINAL_TILE_SIZE;
			if (	this.map_structure[HitBoxUpperLeft_y][HitBoxUpperLeft_x].isExploding() || 
					this.map_structure[HitBoxUpperRight_y][HitBoxUpperRight_x].isExploding() ||
					this.map_structure[HitBoxBottomLeft_y][HitBoxBottomLeft_x].isExploding() ||
					this.map_structure[HitBoxBottomRight_y][HitBoxBottomRight_x].isExploding() ) {
				c.damage();
//				if (is_dead) {
//					boolean can_disappear = c.decreaseDeathAnimationCounter();
//					/*
//					 * se il death_animation_counter dura di più della fiamma il personaggio non sparisce, 
//					 * bisogna creare can_disappear come campo della classe e fare questo controllo in un altra funzione
//					 * a parte, e non bombdamage
//					 */
//					if (can_disappear) {						
//						this.modelsView.remove(c);
//					}
//				}
			}
			
		}
		
//		//calcola il tile preciso in cui si trova il Character in un determinato momento
//		TileModel tile_pos = this.map_structure[row_pos][col_pos];
//		
//		//controlla se quel tile fa parte dei tile in cui è presente una fiamma
//		for (HashSet<TileModel> flames : placedBombs.values()) {
//			if (flames.contains(tile_pos)) {
//				return true;
//			}
//		}
//		return false;
	}
	
	
	/*
	 * Funzione che aggiorna i tiles che sono entrati in contatto con l'esplosione di una bomba, facendoli diventare dopo una serie di condizioni
	 * (come un counter o un'animazione) un blocco di tipo calpestabile.
	 */
	public void explodeBlocks() {
//		for (TileModel tile : tiles_to_update.keySet()) {
//			if (tile.destruction_counter == 0){
//				tile.setModel_num(1);
//				tile.setCollision(false);	
//			}
//			else {
//				tile.destruction_counter--;
//			}
//		}
//		for (Iterator<BombModel> iterator = placedBombs.iterator(); iterator.hasNext();) {
//            BombModel bomba = iterator.next();
//            bomba.fireFuse();
//
//            if (bomba.hasExpired()) {    
//                iterator.remove(); 
//            }
//        }
		Iterator<Map.Entry<TileModel, Coordinates>> iterator = tiles_to_update.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<TileModel, Coordinates> entry = iterator.next();
	        TileModel tile = entry.getKey();
	        if (tile.destruction_counter == 0){
				tile.setModel_num(1);
				boolean has_power_up = tile.hasPowerUp();
				if (has_power_up) {	
					int i = this.random_gen.nextInt(7);
					PowerUpModel power_up = new PowerUpModel(i, tile.getMatrix_pos_row(), tile.getMatrix_pos_col());
					this.powerUpList.add(power_up);
					tile.setPower_up(power_up);
				}
				tile.setDisappearing(false);
				tile.setCollision(false);	
				iterator.remove();
			}
			else {
				tile.destruction_counter--;
			}
	    }
	}
	
	public void drawPowerUps(Graphics g) {
		for (PowerUpModel p : powerUpList) {
			g.drawImage(this.powerUpIcons.icons.get(p.getId()), p.getCol()*this.FINAL_TILE_SIZE, p.getRow()*this.FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);
		}
	}
	
	/*
	 * Funzione che aggiorna la view di un tile dopo che è stato modificato da explodeBlocks (e quindi è stato fatto diventare un tile di tipo terreno).
	 */
//	public void updateMap(Graphics g) {
//		
//		//per ogni tile nei tiles da aggiornare (tutti quelli entrati in contatto con un'esplosione) controlla se esso sia diventato pavimento, e nel caso
//		//lo ridisegna
//		for (TileModel tile : this.tiles_to_update.keySet()) {
//			if (tile.getModel_num() == 1) {
//				BufferedImage tile_image = terrain.getTileSamples(tile.getModel_num()-1);
//				g.drawImage(tile_image, this.tiles_to_update.get(tile).i, this.tiles_to_update.get(tile).i, FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);
//				
//			}
//		}
//	}
//	
	/*
	 * Funzione che piazza la bomba in seguito alla pressione della spacebar (aggiungendola alla lista di bombe che verranno disegnate nel ciclo di gioco)
	 */
	public void placeBomb() {
		Bomberman b = Bomberman.getInstance();
		if (controls.isSpace()) {
			
			//Valori calcolati per fare in modo che la bomba venga disegnata allineata con un tile
			
			int b_center_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE/2;
			int b_center_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE/2;
			int bomb_aligned_x = b_center_x - b_center_x%GamePanel.FINAL_TILE_SIZE;
			int bomb_aligned_y = b_center_y - b_center_y%GamePanel.FINAL_TILE_SIZE;
			int bomb_tile_col = bomb_aligned_x/FINAL_TILE_SIZE;
			int bomb_tile_row = bomb_aligned_y/FINAL_TILE_SIZE;
			
			//Si utilizza un timer per evitare di piazzare troppe bombe in un determinato istante di tempo. La bomba viene piazzata solo se il timer è giunto allo zero
			if (bombTimer <= 0) {
				PowerUpModel power_up = Bomberman.getInstance().getPower_up();
				if (this.placedBombs.isEmpty() || (power_up != null && power_up.getId() ==4) ) {
					
					BombModel placedBomb = new BombModel(bomb_aligned_x, bomb_aligned_y);
					//ogni volta che viene piazzata una bomba, essa viene inserita in placedBombs e gli viene associato il set di tutti i tiles che saranno affetti
					//dalla sua fiamma. Inizialmente questo set è vuoto e viene costruito in modo adeguato da drawBombs, ma è sbagliato farlo in quella funzione
					//perché fa già troppe cose. Si può costruire una funzione utilitaria che calcola i tile dove saranno le fiamme e chiamarla direttamente qui dentro
					placedBombs.put(placedBomb, new HashSet<TileModel>());	
//				Thread t = new Thread(new SoundPlayer(this.audio_samples.files, 1));
//				t.start();
					this.audio_samples.play(1);
//				this.audio_samples.play(2);
					map_structure[bomb_tile_row][bomb_tile_col].setPlacedBomb(placedBomb);
					//Si riavvia il timer dopo il piazzamento
					bombTimer = 100;
				}
			}
		}
		//Si decrementa il timer se non è stata pizzata nessuna bomba
		else {				
			bombTimer -= 10; 
		}
		
	}
	
	public void damageEnemies() {
		//Aggiungere anche l'incremento punti utente quando un nemico muore
	}
	
	/*
	 * Funzione che disegna tutte le bombe piazzate, disegnando la bomba stessa se inesplosa, o l'esplosione se esplosa. 
	 */
	public void drawBombs(Graphics g) {
		Bomberman bomberman = Bomberman.getInstance();
		for (BombModel b : placedBombs.keySet()) {
			
			
			
			int b_tile_col = b.getPos_x()/FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/FINAL_TILE_SIZE;
			
			
			
			//Disegna l'esplosione di ogni bomba, disegnando prima tutta la parte superiore, poi a destra, giù e infine a sinistra. Il disegno dell'esplosione
			//si interrompe non appena si incontra un tile con collision attiva.
			if (b.hasExploded()) {
				if (!b.processed_explosion) {
					b.setExplosionLimit(Bomberman.getInstance());
				}
				if (b.scoreUpdated == false) {
					PowerUpModel power_up = Bomberman.getInstance().getPower_up();
					if (power_up != null && power_up.getId() == 3) {
						this.updateScore(500, 3);
					}
					else {
						this.updateScore(500,1);						
					}
					b.scoreUpdated = true;
				}
				if (b.soundPlayed == false) {
					this.audio_samples.play(2);
					b.soundPlayed = true;
				}
				this.map_structure[b_tile_row][b_tile_col].setPlacedBomb(null);
				//g.drawImage(bombView.explosionSprite, b.getPos_x()-96, b.getPos_y()-96, 5*FINAL_TILE_SIZE, 5*FINAL_TILE_SIZE, null);
				//disegna l'esplosione centrale nel tile della bomba
				placedBombs.get(b).add(this.map_structure[b_tile_row][b_tile_col]);
				this.map_structure[b_tile_row][b_tile_col].setExploding(true);
				g.drawImage(bombView.cExplosionAnimations[(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
				//b.setExplosionLimit(Bomberman.getInstance());
				//disegna tutta la parte superiore dell'esplosione, partendo dal centro e salendo
				for (int j = 0; j < b.up_explosion_limit; j++) {
					//si controllano i bounds della mappa per l'esplosione
					if (b_tile_row-(j+1) >= 0 && b_tile_row-(j+1) < Y_TILES && b_tile_col >= 0 && b_tile_col < X_TILES) {
						//se viene incontrato dalla fiamma un tile con collisione attiva si interrompe il disegno della fiamma e si aggiunge il tile 
						//ai tile da modificare (tiles_to_update) se il tile è distruttibile.
						if (this.map_structure[b_tile_row-(j+1)][b_tile_col].getCollision() && !b.processed_explosion) {
							b.up_explosion_limit = j;
							if (this.map_structure[b_tile_row-(j+1)][b_tile_col].getDestructible() == true) {
								this.map_structure[b_tile_row-(j+1)][b_tile_col].setDisappearing(true);
								tiles_to_update.put(this.map_structure[b_tile_row-(j+1)][b_tile_col], new Coordinates(b_tile_row-(j+1), b_tile_col));
							}
							break;
						}
						else {
							//se l'esplosione può continuare ad espandersi si aggiunge il tile della fiamma al set di tiles di tutte le fiamme associate ad ogni bomba
							//nota: questa cosa va fatta in un'altra funzione!
							if (!b.processed_explosion) {								
								placedBombs.get(b).add(this.map_structure[b_tile_row-(j+1)][b_tile_col]);
								this.map_structure[b_tile_row-(j+1)][b_tile_col].setExploding(true);
							}
							//disegna la fiamma in quel tile prima di procedere al prossimo ed espanderla ulteriormente
							if (j == bomberman.getExplosion_limit()-1) {
								g.drawImage(bombView.explosionMatrix[0][1][(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y()-(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}

							else {
								
								g.drawImage(bombView.explosionMatrix[0][0][(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y()-(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
							}
						}
					}
				}
				for (int j = 0; j < b.right_explosion_limit; j++) {
					if (b_tile_row >= 0 && b_tile_row < Y_TILES && b_tile_col+j+1 >= 0 && b_tile_col+j+1 < X_TILES) {
						if (this.map_structure[b_tile_row][b_tile_col+j+1].getCollision() && !b.processed_explosion) {
							b.right_explosion_limit = j;
							
							if (this.map_structure[b_tile_row][b_tile_col+j+1].getDestructible() == true) {
								this.map_structure[b_tile_row][b_tile_col+j+1].setDisappearing(true);
								tiles_to_update.put(this.map_structure[b_tile_row][b_tile_col+j+1], new Coordinates(b_tile_row,b_tile_col+j+1));
							}
							break;							
						}
						else {
							if (!b.processed_explosion) {	
								placedBombs.get(b).add(this.map_structure[b_tile_row][b_tile_col+j+1]);
								this.map_structure[b_tile_row][b_tile_col+j+1].setExploding(true);
							}
							if (j == bomberman.getExplosion_limit()-1) {
								g.drawImage(bombView.explosionMatrix[1][1][(b.explosionAnimationCounter/5)%3], b.getPos_x()+(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}

							else {
								g.drawImage(bombView.explosionMatrix[1][0][(b.explosionAnimationCounter/5)%3], b.getPos_x()+(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}
						}
					}
				}
				for (int j = 0; j < b.down_explosion_limit; j++) {
					if (b_tile_row+j+1 >= 0 && b_tile_row+j+1 < Y_TILES && b_tile_col >= 0 && b_tile_col < X_TILES) {
						
						if (this.map_structure[b_tile_row+j+1][b_tile_col].getCollision() && !b.processed_explosion) {
							b.down_explosion_limit = j;
							if (this.map_structure[b_tile_row+j+1][b_tile_col].getDestructible() == true) {
								this.map_structure[b_tile_row+j+1][b_tile_col].setDisappearing(true);
								tiles_to_update.put(this.map_structure[b_tile_row+j+1][b_tile_col], new Coordinates(b_tile_row+j+1,b_tile_col));
							}
							break;
							
						}
						else {
							if (!b.processed_explosion) {								
								placedBombs.get(b).add(this.map_structure[b_tile_row+j+1][b_tile_col]);
								this.map_structure[b_tile_row+j+1][b_tile_col].setExploding(true);
							}
							
							if (j == bomberman.getExplosion_limit()-1) {
								g.drawImage(bombView.explosionMatrix[2][1][(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y()+(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}

							else {
								g.drawImage(bombView.explosionMatrix[2][0][(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y()+(j+1)*FINAL_TILE_SIZE, FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);
	
							}
						}
					}
				}
				for (int j = 0; j < b.left_explosion_limit; j++) {
					if (b_tile_row >= 0 && b_tile_row < Y_TILES && b_tile_col-(j+1) >= 0 && b_tile_col-(j+1) < X_TILES) {	
						if (this.map_structure[b_tile_row][b_tile_col-(j+1)].getCollision() && !b.processed_explosion) {
							b.left_explosion_limit = j;
							if (this.map_structure[b_tile_row][b_tile_col-(j+1)].getDestructible() == true) {
								this.map_structure[b_tile_row][b_tile_col-(j+1)].setDisappearing(true);
								tiles_to_update.put(this.map_structure[b_tile_row][b_tile_col-(j+1)], new Coordinates(b_tile_row+j+1,b_tile_col));
							}
							break;
						}
						else {
							if (!b.processed_explosion) {
								placedBombs.get(b).add(this.map_structure[b_tile_row][b_tile_col-(j+1)]);
								this.map_structure[b_tile_row][b_tile_col-(j+1)].setExploding(true);
							}
							if (j == bomberman.getExplosion_limit()-1) {
								g.drawImage(bombView.explosionMatrix[3][1][(b.explosionAnimationCounter/5)%3], b.getPos_x()-(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}

							else {
								g.drawImage(bombView.explosionMatrix[3][0][(b.explosionAnimationCounter/5)%3], b.getPos_x()-(j+1)*FINAL_TILE_SIZE, b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE,null);

							}
						}
					}

				}
				for (Character c : this.moveableCharacters) {
					var flames = placedBombs.get(b);
					int c_tile_col = c.getPos_x()/FINAL_TILE_SIZE;
					int c_tile_row = c.getPos_y()/FINAL_TILE_SIZE;
					int HitBoxUpperLeft_x = (c.getPos_x()+10) / FINAL_TILE_SIZE;
					int HitBoxUpperLeft_y = (c.getPos_y()+10) / FINAL_TILE_SIZE;
					int HitBoxUpperRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
					int HitBoxUpperRight_y = (c.getPos_y()+10)/ FINAL_TILE_SIZE;
					int HitBoxBottomLeft_x = (c.getPos_x()+10)/ FINAL_TILE_SIZE;
					int HitBoxBottomLeft_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
					int HitBoxBottomRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
					int HitBoxBottomRight_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
					int row_pos = c.getPos_y()/FINAL_TILE_SIZE;
					int col_pos = c.getPos_x()/FINAL_TILE_SIZE;
					if (	flames.contains(this.map_structure[HitBoxUpperLeft_y][HitBoxUpperLeft_x]) || 
							flames.contains(this.map_structure[HitBoxUpperRight_y][HitBoxUpperRight_x]) ||
							flames.contains(this.map_structure[HitBoxBottomLeft_y][HitBoxBottomLeft_x]) ||
							flames.contains(this.map_structure[HitBoxBottomRight_y][HitBoxBottomRight_x])){
						if (!b.hasDamaged(c)) {
							c.damage();
							b.damaged(c);
							
						}
						
//					TileModel characterTile = this.map_structure[c_tile_row][c_tile_col];
//					if(placedBombs.get(b).contains(characterTile) && !b.processed_explosion) {
//						c.damage();
//						System.out.println("damaged");
//					}
					}
				}
				b.processed_explosion = true;
				
			}
			else {
				BufferedImage bombSprite = bombView.bombAnimations[(b.animationCounter/2)%3];
				g.drawImage(bombSprite, b.getPos_x(), b.getPos_y(), FINAL_TILE_SIZE, FINAL_TILE_SIZE, null);

			}

			b.explosionAnimationCounter++;
		}
	}
	
	public void manageDeadCharacters() {
		for (Iterator<Character> iterator = this.characterModelsView.keySet().iterator(); iterator.hasNext();) {
			Character c = iterator.next();
			if (c.isDead()) {
				boolean can_disappear = c.decreaseDeathAnimationCounter();
				this.moveableCharacters.remove(c);
				/*
				 * se il death_animation_counter dura di più della fiamma il personaggio non sparisce, 
				 * bisogna creare can_disappear come campo della classe e fare questo controllo in un'altra funzione
				 * a parte, e non bombdamage
				 */
				if (can_disappear) {
					c.setReallyDead();
					iterator.remove();
				}
			}
		}
	}
	
	public void manageProjectiles() {
		for (Iterator<Projectile> iterator = this.projectiles.iterator(); iterator.hasNext();) {
			Projectile p = iterator.next();
			if (p.is_expired()) {
				iterator.remove();
			}
		}
	}
	
	
	//Creiamo una coda di bombe. Poi per ogni bomba nella coda facciamo partire la funzione fireFuse che aggiorna
	//continuamente il timer della miccia :)
	

	/*
	 * Funzione che aggiorna ad ogni ciclo il timer della bomba. 
	 * Nota: nella funzione si controlla anche se delle entità prendano danno dalla fiamma di ogni bomba, ma questa cosa va fatta in un'altra funzione.
	 */
    public void updateBombTimer() {
        // Aggiorna il timer di ogni bomba attiva
    	 //placeholder, deve essere quello di ogni singola bomba preso dal bombmodel
        for (Iterator<Map.Entry<BombModel, HashSet<TileModel>>> iterator = placedBombs.entrySet().iterator(); iterator.hasNext();) {
            BombModel bomba = iterator.next().getKey();
            bomba.fireFuse(GamePanel.FINAL_TILE_SIZE);
            if (bomba.hasExpired()) {  
            	for (TileModel t : placedBombs.get(bomba)) {
            		t.setExploding(false);
            	}
                iterator.remove(); 

            }
        }
    }
    


   
    public void updateScore(int newScore, int multiplier) {
        currentUser.setScore(currentUser.getScore()+(newScore*multiplier));
        fdg.scoreLabel.setText("Score: " + currentUser.getScore());

    }
	
	
}

//Segnalare su ogni tile con delle variabili se è presente una bomba o un nemico così da poter lavorare con la collision tra nemici
