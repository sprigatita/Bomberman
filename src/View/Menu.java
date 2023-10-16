package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Menu extends JPanel {
	BufferedImage sfondo;
	BufferedImage game_over;
	BufferedImage top_bar;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Menu() {
		try {
			sfondo = ImageIO.read(new File("src/resources/menu/bg-menu.png"));
			game_over = ImageIO.read(new File("src/resources/menu/game_over.png"));
			top_bar = ImageIO.read(new File("src/resources/menu/topbar.png"));
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(sfondo, 0, 0, sfondo.getWidth(), sfondo.getHeight(), null);
	}

}
