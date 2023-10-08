package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;

public class BombermanView extends CharacterView {

	public BombermanView() {
		createAnimationArr();
		sprite = downAnimations[1];
	}
	
	@Override
	public BufferedImage getSprite() {
		return sprite;
	}
	
	@Override
    public int getSpriteHeight() {
        return sprite.getHeight();
    }
    
	@Override
    public int getSpriteWidth() {
    	return sprite.getWidth();
    }
    
	@Override
    public void setNextRight() {
    	this.sprite = rightAnimations[rightCount/ANIMATION_SPEED];
    	rightCount++;
    	if (rightCount == rightAnimations.length*ANIMATION_SPEED) {
    		rightCount = 0;
    	}
    }
	
	@Override
    public void setNextLeft() {
    	this.sprite = leftAnimations[leftCount/ANIMATION_SPEED];
    	leftCount++;
    	if (leftCount == leftAnimations.length*ANIMATION_SPEED) {
    		leftCount = 0;
    	}
    }
	
	@Override
    public void setNextUp() {
    	this.sprite = upAnimations[upCount/ANIMATION_SPEED];
    	upCount++;
    	if (upCount == upAnimations.length*ANIMATION_SPEED) {
    		upCount = 0;
    	}
    }
	
	@Override
    public void setNextDown() {
    	this.sprite = downAnimations[downCount/ANIMATION_SPEED];
    	downCount++;
    	if (downCount == downAnimations.length*ANIMATION_SPEED) {
    		downCount = 0;
    	}
    }
    
	@Override
    public void createAnimationArr() {
    	
		try {
			leftAnimations[0] = ImageIO.read(new File("src/resources/left_01.png"));
			leftAnimations[1] = ImageIO.read(new File("src/resources/left_02.png"));
			leftAnimations[2] = ImageIO.read(new File("src/resources/left_03.png"));
			
			rightAnimations[0] = ImageIO.read(new File("src/resources/right_01.png"));
			rightAnimations[1] = ImageIO.read(new File("src/resources/right_02.png"));
			rightAnimations[2] = ImageIO.read(new File("src/resources/right_03.png"));
			
			upAnimations[0] = ImageIO.read(new File("src/resources/up_01.png"));
			upAnimations[1] = ImageIO.read(new File("src/resources/up_02.png"));
			upAnimations[2] = ImageIO.read(new File("src/resources/up_03.png"));
			
			downAnimations[0] = ImageIO.read(new File("src/resources/down_01.png"));
			downAnimations[1] = ImageIO.read(new File("src/resources/down_02.png"));
			downAnimations[2] = ImageIO.read(new File("src/resources/down_03.png"));
			
			deathAnimations[0] = ImageIO.read(new File("src/resources/bomberman_death/1.png"));
			deathAnimations[1] = ImageIO.read(new File("src/resources/bomberman_death/2.png"));
			deathAnimations[2] = ImageIO.read(new File("src/resources/bomberman_death/3.png"));
			deathAnimations[3] = ImageIO.read(new File("src/resources/bomberman_death/4.png"));
			deathAnimations[4] = ImageIO.read(new File("src/resources/bomberman_death/5.png"));
			deathAnimations[5] = ImageIO.read(new File("src/resources/bomberman_death/6.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
		}
    			
    }
    

    
}
