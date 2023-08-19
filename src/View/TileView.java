package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Model.TileModel;

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
			e.printStackTrace(); //Pos nel programmaq in cui è avvenuto l'errore
		}
	}
	
	public void drawTile(Graphics g, TileModel[][] mapStructure) {
		
		int h_tiles_num = GamePanel.X_TILES;
		int v_tiles_num = GamePanel.Y_TILES;
		int tile_width = GamePanel.FINAL_TILE_SIZE;
		
		for (int j = 0; j < h_tiles_num; j++) {
			for (int k = 0; k < v_tiles_num; k++) {
				int tile_num = mapStructure[k][j].getModel_num();
				g.drawImage(tileSamples[tile_num], j*tile_width, k*tile_width, tile_width, tile_width, null);
			}
		}
	}

}
