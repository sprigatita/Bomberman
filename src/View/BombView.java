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
	
	
	public BufferedImage[][] explosionMatrix = new BufferedImage[4][2];
	
	
	int bomb_tile_size = GamePanel.FINAL_TILE_SIZE;

	private BufferedImage spriteToDraw;
	public BufferedImage bombSprite;
	public BufferedImage centralExplosionSprite;
	
	
	public BufferedImage[] bombAnimations = new BufferedImage[3];
	
	public BufferedImage[] cExplosionAnimations = new BufferedImage[3];
    
    public BufferedImage[] verticalExplosionAnimations1 = new BufferedImage[3];
    public BufferedImage[] horizontalExplosionAnimations1 = new BufferedImage[3];

    public BufferedImage[] uExplosionAnimations2 = new BufferedImage[3];
    public BufferedImage[] dExplosionAnimations2 = new BufferedImage[3];
    public BufferedImage[] lExplosionAnimations2 = new BufferedImage[3];
    public BufferedImage[] rExplosionAnimations2 = new BufferedImage[3];
    
    
    public BombView() {
    	createBombAnimationArr();
    }
    
    
    public void createBombAnimationArr() {
    	
		try {
			centralExplosionSprite = ImageIO.read(new File("src/resources/explosion/center_01.png"));
			bombSprite = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			spriteToDraw = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			
			explosionMatrix[0][0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
			explosionMatrix[0][1] = ImageIO.read(new File("src/resources/explosion/up_01.png"));
			explosionMatrix[1][0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
			explosionMatrix[1][1] = ImageIO.read(new File("src/resources/explosion/right_01.png"));
			explosionMatrix[2][0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
			explosionMatrix[2][1] = ImageIO.read(new File("src/resources/explosion/down_01.png"));
			explosionMatrix[3][0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
			explosionMatrix[3][1] = ImageIO.read(new File("src/resources/explosion/left_01.png"));
			
			bombAnimations[0] = ImageIO.read(new File("src/resources/bomb/bomb_01.png"));
			bombAnimations[1] = ImageIO.read(new File("src/resources/bomb/bomb_02.png"));
			bombAnimations[2] = ImageIO.read(new File("src/resources/bomb/bomb_03.png"));

//			cExplosionAnimations[0] = ImageIO.read(new File("src/resources/explosion/center_01.png"));
//			cExplosionAnimations[1] = ImageIO.read(new File("src/resources/explosion/center_02.png"));
//			cExplosionAnimations[2] = ImageIO.read(new File("src/resources/explosion/center_03.png"));
//			
//			verticalExplosionAnimations1[0] = ImageIO.read(new File("src/resources/explosion/vertical_01.png"));
//			verticalExplosionAnimations1[1] = ImageIO.read(new File("src/resources/explosion/vertical_02.png"));
//			verticalExplosionAnimations1[2] = ImageIO.read(new File("src/resources/explosion/vertical_03.png"));
//			
//			horizontalExplosionAnimations1[0] = ImageIO.read(new File("src/resources/explosion/horizontal_01.png"));
//			horizontalExplosionAnimations1[1] = ImageIO.read(new File("src/resources/explosion/horizontal_02.png"));
//			horizontalExplosionAnimations1[2] = ImageIO.read(new File("src/resources/explosion/horizontal_03.png"));
//			
//			uExplosionAnimations2[0] = ImageIO.read(new File("src/resources/explosion/up_01.png"));
//			uExplosionAnimations2[1] = ImageIO.read(new File("src/resources/explosion/up_02.png"));
//			uExplosionAnimations2[2] = ImageIO.read(new File("src/resources/explosion/up_03.png"));
//			
//			dExplosionAnimations2[0] = ImageIO.read(new File("src/resources/explosion/down_01.png"));
//			dExplosionAnimations2[1] = ImageIO.read(new File("src/resources/explosion/down_02.png"));
//			dExplosionAnimations2[2] = ImageIO.read(new File("src/resources/explosion/down_03.png"));
//			
//			lExplosionAnimations2[0] = ImageIO.read(new File("src/resources/explosion/bomb_01.png"));
//			lExplosionAnimations2[1] = ImageIO.read(new File("src/resources/explosion/bomb_02.png"));
//			lExplosionAnimations2[2] = ImageIO.read(new File("src/resources/explosion/bomb_03.png"));
//			
//			rExplosionAnimations2[0] = ImageIO.read(new File("src/resources/explosion/bomb_01.png"));
//			rExplosionAnimations2[1] = ImageIO.read(new File("src/resources/explosion/bomb_02.png"));
//			rExplosionAnimations2[2] = ImageIO.read(new File("src/resources/explosion/bomb_03.png"));
			
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel programma in cui è avvenuto l'errore
		}
    			
    }
    
}
