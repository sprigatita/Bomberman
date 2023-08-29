package Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer implements Runnable {
	File sound;
	public SoundPlayer(List<File> files, int i) {
		this.sound = files.get(i);
	}

	@Override
	public void run() {
		System.out.println("test");
		
		try {
			AudioInputStream sound_stream = AudioSystem.getAudioInputStream(sound);
			Clip c = AudioSystem.getClip();
			c.open(sound_stream);
			c.start();
			Thread.sleep(c.getMicrosecondLength() / 1000);
            c.close();
            sound_stream.close();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
