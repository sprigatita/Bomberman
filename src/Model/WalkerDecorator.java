package Model;

import Controller.ControlsHandler;

public abstract class WalkerDecorator implements Moveable{

	
	public int getPos_x() {
		return wrappee.getPos_x();
	}
	
	public void setPos_x(int pos_x) {
		wrappee.setPos_x(pos_x);
	}
	
	public int getPos_y() {
		return wrappee.getPos_y();
	}
	
	public void setPos_y(int pos_y) {
		wrappee.setPos_y(pos_y);
	}
	
	
	protected Walker wrappee;
	
	
	
	protected WalkerDecorator(Walker wrappee) {
		this.wrappee = wrappee;
	}
	
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		wrappee.move();
		
	}
	
}
