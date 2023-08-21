package View;

	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;
	import java.util.Observable;
	import java.util.Observer;
	import javax.imageio.ImageIO;

	@SuppressWarnings("deprecation")
	public class EnemyView implements Observer {
	    private BufferedImage sprite;
	    private BufferedImage[] leftAnimations = new BufferedImage[3];
	    private BufferedImage[] rightAnimations = new BufferedImage[3];
	    private BufferedImage[] upAnimations = new BufferedImage[3];
	    private BufferedImage[] downAnimations = new BufferedImage[3];
	    
	    private int upCount = 0;
	    private int downCount = 0;
	    private int leftCount = 0;
	    private int rightCount = 0;
	    
		final int SCALING_CONST = 3;
		final int ANIMATION_SPEED = 6;
		
		public EnemyView() {
			createAnimationArr();
			sprite = downAnimations[1];
		}
		
		
		public BufferedImage getSprite() {
			return sprite;
		}

	    public void createAnimationArr() {
	    	
			try {
				leftAnimations[0] = ImageIO.read(new File("src/resources/enemy/left_01.png"));
				leftAnimations[1] = ImageIO.read(new File("src/resources/enemy/left_02.png"));
				leftAnimations[2] = ImageIO.read(new File("src/resources/enemy/left_03.png"));
				
				rightAnimations[0] = ImageIO.read(new File("src/resources/enemy/right_01.png"));
				rightAnimations[1] = ImageIO.read(new File("src/resources/enemy/right_02.png"));
				rightAnimations[2] = ImageIO.read(new File("src/resources/enemy/right_03.png"));
				
				upAnimations[0] = ImageIO.read(new File("src/resources/enemy/up_01.png"));
				upAnimations[1] = ImageIO.read(new File("src/resources/enemy/up_02.png"));
				upAnimations[2] = ImageIO.read(new File("src/resources/enemy/up_03.png"));
				
				downAnimations[0] = ImageIO.read(new File("src/resources/enemy/down_01.png"));
				downAnimations[1] = ImageIO.read(new File("src/resources/enemy/down_02.png"));
				downAnimations[2] = ImageIO.read(new File("src/resources/enemy/down_03.png"));
			} catch (IOException e) {
				e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
			}
	    			
	    }

		
	    public int getSpriteHeight() {
	        return sprite.getHeight();
	    }
	    
	    public int getSpriteWidth() {
	    	return sprite.getWidth();
	    }
	    
	    public void setNextRight() {
	    	this.sprite = rightAnimations[rightCount/ANIMATION_SPEED];
	    	rightCount++;
	    	if (rightCount == rightAnimations.length*ANIMATION_SPEED) {
	    		rightCount = 0;
	    	}
	    }
	    public void setNextLeft() {
	    	this.sprite = leftAnimations[leftCount/ANIMATION_SPEED];
	    	leftCount++;
	    	if (leftCount == leftAnimations.length*ANIMATION_SPEED) {
	    		leftCount = 0;
	    	}
	    }
	    public void setNextUp() {
	    	this.sprite = upAnimations[upCount/ANIMATION_SPEED];
	    	upCount++;
	    	if (upCount == upAnimations.length*ANIMATION_SPEED) {
	    		upCount = 0;
	    	}
	    }
	    public void setNextDown() {
	    	this.sprite = downAnimations[downCount/ANIMATION_SPEED];
	    	downCount++;
	    	if (downCount == downAnimations.length*ANIMATION_SPEED) {
	    		downCount = 0;
	    	}
	    }
	    
		
		@Override
		public void update(Observable o, Object arg) {
			
		}
	
}