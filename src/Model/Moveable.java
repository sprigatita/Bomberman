package Model;

import Controller.ControlsHandler;

public interface Moveable {
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls);
}
