package View;

import java.awt.image.BufferedImage;
import java.util.Observer;

public abstract class EntityView implements Observer {
	protected BufferedImage sprite;
	public EntityView() {
		// TODO Auto-generated constructor stub
	}
	

	public BufferedImage getSprite() {
		return this.sprite;
	}
    public int getSpriteHeight() {
        return this.sprite.getHeight();
    }
    
    
    public abstract BufferedImage getDeadSprite(int animationCounter);
    
    public int getSpriteWidth() {
    	return this.sprite.getWidth();
    }

}
