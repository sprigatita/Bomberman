package Model;

import java.awt.image.BufferedImage;

public class User {
	
	public int level = 0;
	public String username;
	public BufferedImage propic;
	private int score;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

}
