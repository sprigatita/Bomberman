package View;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import  java.awt.image.BufferedImage;
import java.io.File;

public class PowerUpView {
	
	public ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
	public PowerUpView() {
		try {
			this.icons.add(ImageIO.read(new File("src/resources/left_01.png")));
			System.out.println(this.icons.size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
