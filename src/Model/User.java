package Model;

import java.awt.image.BufferedImage;

public class User {
	
	public String username;
	public BufferedImage propic;
	private int score;
	public int current_level;
	
	public User(String name, int level) {
		this.username = name;
		this.current_level = level;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

}
