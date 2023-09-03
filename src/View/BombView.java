package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import Model.TileModel;

public class BombView {
	
	
	public BufferedImage[][][] explosionMatrix = new BufferedImage[4][2][3];
	
	int bomb_tile_size = GamePanel.FINAL_TILE_SIZE;

	private BufferedImage spriteToDraw;
	public BufferedImage bombSprite;
	public BufferedImage centralExplosionSprite;
	
	
	public BufferedImage[] bombAnimations = new BufferedImage[3];
	
	public BufferedImage[] cExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] uExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] dExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] lExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] rExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] vExplosionAnimations = new BufferedImage[3];
    public BufferedImage[] hExplosionAnimations = new BufferedImage[3];
    
    
    public BombView() {
    	createBombAnimationArr();
    }
    
    
    public void createBombAnimationArr() {
    	
		try {
			centralExplosionSprite = ImageIO.read(new File("src/resources/explosion/center_01.png"));
			bombSprite = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			spriteToDraw = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			
//			explosionMatrix[0][0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
//			explosionMatrix[0][1] = ImageIO.read(new File("src/resources/explosion/up_01.png"));
//			explosionMatrix[1][0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
//			explosionMatrix[1][1] = ImageIO.read(new File("src/resources/explosion/right_01.png"));
//			explosionMatrix[2][0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
//			explosionMatrix[2][1] = ImageIO.read(new File("src/resources/explosion/down_01.png"));
//			explosionMatrix[3][0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
//			explosionMatrix[3][1] = ImageIO.read(new File("src/resources/explosion/left_01.png"));
			
			bombAnimations[0] = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			bombAnimations[1] = ImageIO.read(new File("src/resources/bomb/bomb_02.png"));
			bombAnimations[2] = ImageIO.read(new File("src/resources/bomb/bomb_03.png"));

			cExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/center_01.png"));
			cExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/center_02.png"));
			cExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/center_03.png"));
			
			
			uExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/up_01.png"));
			uExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/up_02.png"));
			uExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/up_03.png"));
			
			dExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/down_01.png"));
			dExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/down_02.png"));
			dExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/down_03.png"));
			
			lExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/left_01.png"));
			lExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/left_02.png"));
			lExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/left_03.png"));
			
			rExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/right_01.png"));
			rExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/right_02.png"));
			rExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/right_03.png"));
			
			vExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
			vExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/vertical_02.png"));
			vExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/vertical_03.png"));
			
			hExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
			hExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/horizontal_02.png"));
			hExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/horizontal_03.png"));
			
			explosionMatrix[0][0] = this.vExplosionAnimations;
			explosionMatrix[1][0] = this.hExplosionAnimations;
			explosionMatrix[2][0] = this.vExplosionAnimations;
			explosionMatrix[3][0] = this.hExplosionAnimations;
			
			explosionMatrix[0][1] = this.uExplosionAnimations;
			explosionMatrix[1][1] = this.rExplosionAnimations;
			explosionMatrix[2][1] = this.dExplosionAnimations;
			explosionMatrix[3][1] = this.lExplosionAnimations;
			
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel programma in cui è avvenuto l'errore
		}
    			
    }
    
}
