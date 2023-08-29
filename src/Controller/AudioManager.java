package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AudioManager {
    private static AudioManager instanceOf;

    public static AudioManager getInstance() {
        if (instanceOf == null)
            instanceOf = new AudioManager();
        return instanceOf;
    }

    private AudioManager() {
    }

    public void play(String filename) {
        try {
            File audioFile = new File(filename);
            if (audioFile.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

                // Allow the clip to play for its entire duration
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.close();
            } else {
                System.out.println("Audio file not found.");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
