package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import View.UserButton;

public class ButtonSetter implements ActionListener{
	
	JButton play;
	UserButton[] users;
	public ButtonSetter(JButton play, UserButton[] users) {
		this.play = play;
		this.users = users;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i<users.length; i++) {
			users[i].setVisible(true);
		}
		play.setVisible(false);
	}

}
