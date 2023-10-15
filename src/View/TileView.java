package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Model.TileModel;

public class TileView {
	
	
	
	
	
	String mapName;
	private int num_of_samples = 24;
	private BufferedImage[] tileSamples = new BufferedImage[num_of_samples];
	private BufferedImage[] exploding_block = new BufferedImage[6];
	
	public BufferedImage getTileSamples(int i) {
		return tileSamples[i];
	}
	
	public TileView(String mapName) {
		this.mapName = mapName + "/";
		createBasicTerrain();
	}
	
	public void createBasicTerrain() {
		try {
			
			String filename = "src/resources/";
			
			for (int n = 1; n <= num_of_samples; n++) {
					tileSamples[n-1] = ImageIO.read(new File(filename + mapName + n + ".png"));
				}
			for (int n = 1; n <= 6; n++) {
				exploding_block[n-1] = ImageIO.read(new File(filename + mapName + "exploding/"+ n + ".png"));
			}

		} catch (IOException e) {
			e.printStackTrace(); //Pos nel programmaq in cui è avvenuto l'errore
		}
	}
	
	
	public void drawExplosion(Graphics g) {
		
	}
	
	public void drawTile(Graphics g, TileModel[][] mapStructure) {
		
		int h_tiles_num = GamePanel.X_TILES;
		int v_tiles_num = GamePanel.Y_TILES;
		int tile_width = GamePanel.FINAL_TILE_SIZE;
		for (int j = 0; j < h_tiles_num; j++) {
			for (int k = 0; k < v_tiles_num; k++) {
				TileModel tile = mapStructure[k][j];
				int tile_num = tile.getModel_num();
				if (tile.is_disappearing) {
					g.drawImage(exploding_block[5-(tile.destruction_counter/10)%6], j*tile_width, k*tile_width, tile_width, tile_width, null);
				}
				else {
					g.drawImage(tileSamples[tile_num-1], j*tile_width, k*tile_width, tile_width, tile_width, null);					
				}
			}
		}
	}

}
