package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Stream;

public class MapModel {
	
	private TileModel[][] mapStructure;
	private int[] config;
	
	public MapModel(String path, int[] config) {
		
		this.config = config;
		Stream<String> mapText;
		
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			this.mapStructure = mapText.map(MapModel::tileMapping).toArray(TileModel[][]::new);
			configureCollision();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void configureCollision() {
		for (int i = 0; i < mapStructure.length; i++) {
			for (int j = 0; j < mapStructure[0].length; j++) {
				for (int c : config) {
					if (mapStructure[i][j].getModel_num() == c) {
						mapStructure[i][j].setCollision(false);
						break;
					}
				}
			}
		}
	}
	
	public static TileModel[] tileMapping(String s) {
		String[] ss = s.split(" ");
		TileModel[] c = new TileModel[ss.length];

		
		for (int i=0; i<ss.length; i++) {
			c[i] = new TileModel(Integer.parseInt(ss[i]));
			
		}
		
		return c;
	}

	public TileModel[][] getMapStructure() {
		return mapStructure;
	}
	
}
