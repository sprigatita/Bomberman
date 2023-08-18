package Controller;
import View.*;
import View.GamePanel;

public class Main {
	
	public Main() {
		GamePanel panel = new GamePanel();
		
		FinestraDiGioco finestra_principale = new FinestraDiGioco();
		finestra_principale.getContentPane().add(panel);
		finestra_principale.pack();
		finestra_principale.setVisible(true);
		
	}

	public static void main(String[] args) {
		Main main_controller = new Main();
	}

}
