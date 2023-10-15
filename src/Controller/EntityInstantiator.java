package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import Model.Character;
import Model.Direction;
import Model.LaserUtil;
import Model.Laserer;
import Model.MapModel;
import Model.Projectile;
import Model.Shooter;
import Model.TileModel;
import Model.TrapModel;
import Model.Trapper;
import Model.Walker;
import View.EntityView;
import View.ImmobileView;
import View.ShooterView;
import View.TrapperView;
import View.WalkerView;

public class EntityInstantiator {
	
	public ArrayList<Character> chars = new ArrayList<Character>(); 
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();;
	public ArrayList<TrapModel> traps = new ArrayList<TrapModel>();
	public HashMap<TileModel, LaserUtil> laser_tiles = new HashMap<TileModel, LaserUtil>();
	public HashMap<Character, EntityView> characterModelsView = new HashMap<Character, EntityView>();

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
			Character c = new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			this.characterModelsView.put(c, new WalkerView());
			return c;
		case "1":
			Character c1 = new Shooter(Integer.parseInt(values[1]), Integer.parseInt(values[2]), this.projectiles);
			this.characterModelsView.put(c1, new ShooterView());
			return c1;

		case "2":
			Character c2 = new Trapper(Integer.parseInt(values[1]), Integer.parseInt(values[2]), this.traps);
			this.characterModelsView.put(c2, new TrapperView());
			return c2;
		case "3":
			Character c3; 
			switch(values[3]) {
			case "0":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.UP, this.laser_tiles);
				this.characterModelsView.put(c3, new ImmobileView());
				return c3;
			case "1":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.RIGHT, this.laser_tiles);
				this.characterModelsView.put(c3, new ImmobileView());
				return c3;
				
			case "2":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.DOWN, this.laser_tiles);
				this.characterModelsView.put(c3, new ImmobileView());
				return c3;
				
			case "3":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.LEFT, this.laser_tiles);
				this.characterModelsView.put(c3, new ImmobileView());
				return c3;
				
			}
		default:
			return new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			
		}
	}
	

}
