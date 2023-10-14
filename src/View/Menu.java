package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Menu extends JPanel {
	BufferedImage sfondo;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Menu() {
		try {
			sfondo = ImageIO.read(new File("src/resources/menu/bg-menu.png"));
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
