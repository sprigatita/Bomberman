package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import Model.Direction;

@SuppressWarnings("deprecation")
public abstract class CharacterView implements Observer {
    protected BufferedImage sprite;
    protected BufferedImage dead_sprite;
    protected BufferedImage[] leftAnimations = new BufferedImage[3];
    protected BufferedImage[] rightAnimations = new BufferedImage[3];
    protected BufferedImage[] upAnimations = new BufferedImage[3];
    protected BufferedImage[] downAnimations = new BufferedImage[3];
    protected BufferedImage[] deathAnimations = new BufferedImage[6];
    
    protected int upCount = 0;
    protected int downCount = 0;
    protected int leftCount = 0;
    protected int rightCount = 0;
    protected int hitBoxWidth;
    protected int hitBoxHeight;
    
	protected final int SCALING_CONST = 3;
	protected int ANIMATION_SPEED = 4;
	
	
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public BufferedImage getDeadSprite(int animationCounter) {
		return deathAnimations[5-(animationCounter/10)%6];
	}
	
    public int getSpriteHeight() {
        return sprite.getHeight();
    }
    
    public int getSpriteWidth() {
    	return sprite.getWidth();
    }
    
    abstract void setNextRight();
    abstract void setNextLeft();
    abstract void setNextUp();
    abstract void setNextDown();
    
    abstract void createAnimationArr();
    
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			
			Direction dir = (Direction)arg;
			switch(dir) {
			case UP:
				this.setNextUp();
				break;
			case RIGHT:
				this.setNextRight();
				break;
			case DOWN:
				this.setNextDown();
				break;
			case LEFT:
				this.setNextLeft();
				break;
			default:
			}
			
		}
	}
}