package View;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Test extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Test() {
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setIcon(new ImageIcon(Test.class.getResource("/resources/menu/btn-play.png")));
		add(btnNewButton);

	}

}
