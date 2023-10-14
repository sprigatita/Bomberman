package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import Model.Character;
import Model.Direction;
import Model.Laserer;
import Model.MapModel;
import Model.Projectile;
import Model.Shooter;
import Model.TileModel;
import Model.TrapModel;
import Model.Trapper;
import Model.Walker;

public class EntityInstantiator {
	
	public ArrayList<Character> chars = new ArrayList<Character>(); 
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();;
	public ArrayList<TrapModel> traps = new ArrayList<TrapModel>();
	public HashMap<TileModel, Integer> laser_tiles = new HashMap<TileModel, Integer>();

	public EntityInstantiator(String path) {

		Stream<String> mapText;
		
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			String[] values = mapText.toArray(String[]::new);
			for (String s : values) {
				this.chars.add(charCreation(s));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Character charCreation(String s) {
		String[] values = s.split(" ");
		switch(values[0]) {
		case "0":
			return new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		case "1":
			return new Shooter(Integer.parseInt(values[1]), Integer.parseInt(values[2]), this.projectiles);

		case "2":
			return new Trapper(Integer.parseInt(values[1]), Integer.parseInt(values[2]), this.traps);
		case "3":
			switch(values[3]) {
			case "0":
				return new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.UP, this.laser_tiles);
			case "1":
				return new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.RIGHT, this.laser_tiles);
			case "2":
				return new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.DOWN, this.laser_tiles);
			case "3":
				return new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.LEFT, this.laser_tiles);
			}
		default:
			return new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			
		}
	}
	

}
