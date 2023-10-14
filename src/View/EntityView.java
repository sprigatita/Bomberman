package View;

import java.awt.image.BufferedImage;

public abstract class EntityView {
	protected BufferedImage sprite;
	public EntityView() {
		// TODO Auto-generated constructor stub
	}
	

	public BufferedImage getSprite() {
		return sprite;
	}
    public int getSpriteHeight() {
        return sprite.getHeight();
    }
    
    
    public abstract BufferedImage getDeadSprite(int animationCounter);
    
    public int getSpriteWidth() {
    	return sprite.getWidth();
    }

}
