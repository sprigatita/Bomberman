package View;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Main;
import Controller.UserSetter;
import Model.User;

public class UserButton extends JButton{
	User user;
	public UserButton(Main main, User user) {
		this.user = user;
		this.addActionListener(new UserSetter(main, user));
		// TODO Auto-generated constructor stub
	}

}
