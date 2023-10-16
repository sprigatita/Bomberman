package View;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import  java.awt.image.BufferedImage;
import java.io.File;

public class PowerUpView {
	
	public ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
	public PowerUpView() {
		try {
			this.icons.add(ImageIO.read(new File("src/resources/power_up/4.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/7.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/2.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/5.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/1.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/3.png")));
			this.icons.add(ImageIO.read(new File("src/resources/power_up/6.png")));
			this.icons.add(ImageIO.read(new File("src/resources/up_02.png")));
			System.out.println(this.icons.size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
