package View;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WalkerView extends EnemyView {

	public WalkerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	 public void createAnimationArr() {
    	
		try {
			leftAnimations[0] = ImageIO.read(new File("src/resources/enemy/Walker/left_01.png"));
			leftAnimations[1] = ImageIO.read(new File("src/resources/enemy/Walker/left_02.png"));
			leftAnimations[2] = ImageIO.read(new File("src/resources/enemy/Walker/left_03.png"));
			
			rightAnimations[0] = ImageIO.read(new File("src/resources/enemy/Walker/right_01.png"));
			rightAnimations[1] = ImageIO.read(new File("src/resources/enemy/Walker/right_02.png"));
			rightAnimations[2] = ImageIO.read(new File("src/resources/enemy/Walker/right_03.png"));
			
			upAnimations[0] = ImageIO.read(new File("src/resources/enemy/Walker/up_01.png"));
			upAnimations[1] = ImageIO.read(new File("src/resources/enemy/Walker/up_02.png"));
			upAnimations[2] = ImageIO.read(new File("src/resources/enemy/Walker/up_03.png"));
			
			downAnimations[0] = ImageIO.read(new File("src/resources/enemy/Walker/down_01.png"));
			downAnimations[1] = ImageIO.read(new File("src/resources/enemy/Walker/down_02.png"));
			downAnimations[2] = ImageIO.read(new File("src/resources/enemy/Walker/down_03.png"));
			deathAnimations[0] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/1.png"));
			deathAnimations[1] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/2.png"));
			deathAnimations[2] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/3.png"));
			deathAnimations[3] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/4.png"));
			deathAnimations[4] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/5.png"));
			deathAnimations[5] = ImageIO.read(new File("src/resources/enemy/Walker/enemy_death/6.png"));
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
		}
    			
    }
}
