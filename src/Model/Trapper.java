package Model;

import java.util.ArrayList;

import Controller.ControlsHandler;
import Controller.Coordinates;
import View.GamePanel;

public class Trapper extends Walker{

	private ArrayList<TrapModel> traps;
	private int trap_timer = 100;
	
	public Trapper(int x, int y, ArrayList<TrapModel> traps) {
		super(x,y);
		this.traps = traps;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		super.move(tile_size, map_structure, controls);
		placeTrap(tile_size,map_structure, controls, traps );
		
	}
	
	public ArrayList<TrapModel> getTraps() {
		return traps;
	}

	private void placeTrap(int tile_size, TileModel[][] map_structure, ControlsHandler controls, ArrayList<TrapModel> traps) {
		if (this.trap_timer <= 0) {
			int b_center_x = getPos_x() + tile_size/2;
			int b_center_y = getPos_y() + tile_size/2;
			int trap_aligned_x = b_center_x - b_center_x%tile_size;
			int trap_aligned_y = b_center_y - b_center_y%tile_size;
			int trap_tile_col = trap_aligned_x/tile_size;
			int trap_tile_row = trap_aligned_y/tile_size;
			traps.add(new TrapModel(trap_tile_row, trap_tile_col));
			trap_timer = 1000;
		}
		else {
			trap_timer-=10;
		}
	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		// TODO Auto-generated method stub
		return false;
	}
}
