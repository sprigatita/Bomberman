package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

public class ImmobileView extends EntityView{

	
//    protected BufferedImage sprite;
//    protected BufferedImage dead_sprite;
    private BufferedImage[] animations;
    private int animation_counter = 0;
	
	public ImmobileView() {
		this.animations = new BufferedImage[14];
		createAnimationArr();
	}
	
	public void createAnimationArr() {
		
		try {
			
			for (int i = 1; i<=14; i++) {
				this.animations[i-1] = ImageIO.read(new File("src/resources/enemy/Laserer/"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
		}
		this.sprite = animations[0];
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.sprite = animations[(this.animation_counter/10)%14];
		if (animation_counter > 140) {
			animation_counter = 0;
		}
		else {
			animation_counter++;
		}
		
	}

	@Override
	public BufferedImage getDeadSprite(int animationCounter) {
		// TODO Auto-generated method stub
		return null;
	}

}
