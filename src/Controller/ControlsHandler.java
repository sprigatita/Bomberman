package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Bomberman;
import View.BombermanView;
import View.GamePanel;

public class ControlsHandler implements KeyListener{
	Bomberman b = Bomberman.getInstance();
	BombermanView s;

	
	private static final int WINDOW_WIDTH = GamePanel.getPanelWidth();
	private static final int WINDOW_HEIGHT = GamePanel.getPanelHeight();
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean space;
	
	public ControlsHandler(BombermanView v) {
		this.s = v;
	}
	
	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}


	public boolean isLeft() {
		return left;
	}


	public boolean isRight() {
		return right;
	}

	public boolean isSpace() {
		return space;
	}



	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key_code = e.getKeyCode();
		System.out.println("pressed" + key_code);
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				System.out.println("pressed up");
				up = true;					
				break;
			case KeyEvent.VK_S:
				System.out.println("pressed down");
				down = true;					

				
				break;
			case KeyEvent.VK_A:
				System.out.println("pressed left");
				left = true;
				break;
			case KeyEvent.VK_D:
				System.out.println("pressed right");
				right = true;
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("pressed sbacebar");
				space = true;
				break;
			default:
			
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key_code = e.getKeyCode();
		System.out.println("pressed" + key_code);
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_S:
				down = false;
				break;
			case KeyEvent.VK_A:
				left = false;
				break;
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_SPACE:
				space = false;
				break;
			default:
			
		}
	}

	
}
