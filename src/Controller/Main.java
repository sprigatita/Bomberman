package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.User;
import View.*;
import java.awt.Color;

public class Main{
	
	boolean game_started = false;
	public User playing_user;
	public User[] users = new User[3];
	public UserButton[] user_selection = new UserButton[3];
	
	public Main() {
		initialize_users();
//		FinestraDiGioco finestra_principale = new FinestraDiGioco();
//		JButton btnNewButton_1 = new JButton("New button");
//		btnNewButton_1.setBounds(272, 213, 150, 50);
//		btnNewButton_1.setIcon(new ImageIcon(menu.class.getResource("/resources/menu/btn-play.png")));
//		JPanel menu = new menu();
//		menu.add(btnNewButton_1);
//		btnNewButton_1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				finestra_principale.add(new GamePanel());
//				finestra_principale.remove(menu);
//			}
//		});
//		finestra_principale.add(menu);
//		finestra_principale.pack();
//		finestra_principale.setVisible(true);
		
	}
	
	private void initialize_users() {
		users[0] = new User("niko", 0);
		users[1] = new User("marta", 1);
		users[2] = new User("seki", 0);
		user_selection[0] = new UserButton(this, users[0]);
		user_selection[1] = new UserButton(this, users[1]);
		user_selection[2] = new UserButton(this, users[2]);
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.showMenu();
//		Main main_controller = new Main();
	}
	
	public void showMenu() {
		JFrame menu = new JFrame("Menu");
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setBounds(100, 100, 768, 576);
		menu.getContentPane().setLayout(null);
		
		JPanel panel = new Menu();
		panel.setLayout(null);
		panel.setForeground(new Color(0, 0, 255));
		panel.setBounds(0, 0, 768, 576);
		menu.getContentPane().add(panel);
		for (int i = 0; i<3; i++) {
			JButton user = user_selection[i];
			user.setOpaque(false);
			user.setContentAreaFilled(false);
			user.setBorderPainted(false);
			user.setIcon(new ImageIcon(Test.class.getResource("/resources/menu/btn-play.png")));
			user.setBounds(100, 350+50*i, 150, 50);
			panel.add(user);
			user.setVisible(false);
		}
		JButton play = new JButton("New button");
//		JButton play2 = new JButton("New button");
//		JButton play3 = new JButton("New button");
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setIcon(new ImageIcon(Test.class.getResource("/resources/menu/btn-play.png")));
		play.addActionListener(new ButtonSetter(play, this.user_selection));
//		public void actionPerformed(ActionEvent e) { 
//			System.out.println("test");
////				  menu.setVisible(false);
//			
//			JFrame game = new FinestraDiGioco();
//		} 
//	} );
		
	
		panel.add(play);
		play.setBounds(100, 400, 150, 50);
		menu.setVisible(true);
	}
}
