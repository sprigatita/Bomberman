package View;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShooterView extends EnemyView {

	public ShooterView() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void createAnimationArr() {
    	
		try {
			leftAnimations[0] = ImageIO.read(new File("src/resources/enemy/Shooter/left_1.png"));
			leftAnimations[1] = ImageIO.read(new File("src/resources/enemy/Shooter/left_2.png"));
			leftAnimations[2] = ImageIO.read(new File("src/resources/enemy/Shooter/left_3.png"));
			
			rightAnimations[0] = ImageIO.read(new File("src/resources/enemy/Shooter/right_1.png"));
			rightAnimations[1] = ImageIO.read(new File("src/resources/enemy/Shooter/right_2.png"));
			rightAnimations[2] = ImageIO.read(new File("src/resources/enemy/Shooter/right_3.png"));
			
			upAnimations[0] = ImageIO.read(new File("src/resources/enemy/Shooter/up_1.png"));
			upAnimations[1] = ImageIO.read(new File("src/resources/enemy/Shooter/up_2.png"));
			upAnimations[2] = ImageIO.read(new File("src/resources/enemy/Shooter/up_3.png"));
			
			downAnimations[0] = ImageIO.read(new File("src/resources/enemy/Shooter/down_1.png"));
			downAnimations[1] = ImageIO.read(new File("src/resources/enemy/Shooter/down_2.png"));
			downAnimations[2] = ImageIO.read(new File("src/resources/enemy/Shooter/down_3.png"));
			deathAnimations[0] = ImageIO.read(new File("src/resources/enemy/enemy_death/1.png"));
			deathAnimations[1] = ImageIO.read(new File("src/resources/enemy/enemy_death/2.png"));
			deathAnimations[2] = ImageIO.read(new File("src/resources/enemy/enemy_death/3.png"));
			deathAnimations[3] = ImageIO.read(new File("src/resources/enemy/enemy_death/4.png"));
			deathAnimations[4] = ImageIO.read(new File("src/resources/enemy/enemy_death/5.png"));
			deathAnimations[5] = ImageIO.read(new File("src/resources/enemy/enemy_death/6.png"));
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
		}
    			
    }
	
}
