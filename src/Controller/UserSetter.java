package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.User;
import View.FinestraDiGioco;

public class UserSetter implements ActionListener {

	Main main;
	User user;
	public UserSetter(Main main, User user) {
		this.main = main;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.main.playing_user = this.user;
		System.out.println(this.user.current_level);
		new FinestraDiGioco(this.user);
		
	}

}
