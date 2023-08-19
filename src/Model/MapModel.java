package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Stream;

public class MapModel {
	
	private TileModel[][] mapStructure;
	
	
	public MapModel(String path) {
		
		Stream<String> mapText;
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			this.mapStructure = mapText.map(MapModel::tileMapping).toArray(TileModel[][]::new);
			
		}
		catch (Exception e) {
			e.printStackTrace();
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
