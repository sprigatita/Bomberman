package Controller;
import View.*;
import View.GamePanel;

public class Main {
	
	public Main() {

		FinestraDiGioco finestra_principale = new FinestraDiGioco();
		finestra_principale.pack();
		finestra_principale.setVisible(true);
		
	}

	public static void main(String[] args) {
		Main main_controller = new Main();
	}

}
