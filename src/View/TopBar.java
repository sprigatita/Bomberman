package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class TopBar extends JPanel {

	int x;
	int y;
	Menu menu = new Menu();
	public TopBar(GamePanel gp) {
		this.x = (gp.X_TILES*gp.FINAL_TILE_SIZE);
		this.setLayout(new GridLayout(1,4));
		this.setPreferredSize(new Dimension((gp.X_TILES*gp.FINAL_TILE_SIZE),100));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(menu.top_bar, 0, 0, x, 100, null);
	}
}
