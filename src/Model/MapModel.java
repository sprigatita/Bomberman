package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Stream;

public class MapModel {
	
	private TileModel[][] mapStructure;
	
	//File di configurazione per indicare, insieme alla mappa, quali blocchi devono avere la collision attiva.
	private int[] collision_config;
	private int[] destructible_config;
	
	public MapModel(String path, int[] collision_config, int[] destructible_config) {
		
		this.collision_config = collision_config;
		this.destructible_config = destructible_config;
		Stream<String> mapText;
		
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			this.mapStructure = mapText.map(MapModel::tileMapping).toArray(TileModel[][]::new);
			//fa un ulteriore passaggio sulla mapStructure per settare i blocchi con la collision in base al config
			configureCollision();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/*
	 * Funzione per impostare la collisione sulla mapStructure. Scorre su tutti i singoli TileModel della mapStructure e
	 * se il loro modelNum corrisponde ad uno dei valori nel file di config, attiva la collision su quel Tile
	 */
	public void configureCollision() {
		for (int i = 0; i < mapStructure.length; i++) {
			for (int j = 0; j < mapStructure[0].length; j++) {
				
				for (int c : collision_config) {
					if (mapStructure[i][j].getModel_num() == c) {
						mapStructure[i][j].setCollision(false);
						break;
					}
				}
				
				for (int d : destructible_config) {
					if (mapStructure[i][j].getModel_num() == d) {
						mapStructure[i][j].setDestructible(false);
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
