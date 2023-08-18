package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileView {
	
	private BufferedImage[] tileSamples = new BufferedImage[3];
	
	public BufferedImage getTileSamples(int i) {
		return tileSamples[i];
	}
	
	public TileView() {
		createBasicTerrain();
	}
	
	public void createBasicTerrain() {
		try {
			tileSamples[0] = ImageIO.read(new File("src/resources/1.png"));
			tileSamples[1] = ImageIO.read(new File("src/resources/2.png"));
			tileSamples[2] = ImageIO.read(new File("src/resources/3.png"));
		} catch (IOException e) {
			e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
		}
	}

}
