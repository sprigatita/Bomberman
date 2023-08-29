package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class AudioManager {
//    private static AudioManager instanceOf;
//
//    public static AudioManager getInstance() {
//        if (instanceOf == null)
//            instanceOf = new AudioManager();
//        return instanceOf;
//    }
//
//    private AudioManager() {
//    }
//
//    public void play(String filename) {
//        try {
//            File audioFile = new File(filename);
//            if (audioFile.exists()) {
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//                clip.start();
//
//                // Allow the clip to play for its entire duration
//                Thread.sleep(clip.getMicrosecondLength() / 1000);
//                clip.close();
//            } else {
//                System.out.println("Audio file not found.");
//            }
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e1) {
//            e1.printStackTrace();
//        }
//    }
	public ArrayList<Clip> clips = new ArrayList<Clip>();
	public ArrayList<File> files = new ArrayList<File>();
    public AudioManager() {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            File map_music_file = new File("src/resources/greenvillage.wav");
            File bomb_placed_file = new File("src/resources/Place_Bomb.wav");
            File bomb_explodes = new File("src/resources/Bomb_Explodes.wav");
            files.add(map_music_file);
            files.add(bomb_placed_file);
            files.add(bomb_explodes);
            int i = 0;
        for (File file : files) {
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
             // load the sound into memory (a Clip)
                clips.add(AudioSystem.getClip());
                clips.get(i).open(sound);
                i++;
            }
            else {
                throw new RuntimeException("Sound: file not found");
            }
        }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }
    
    public void play(int i){
    	this.clips.get(i).stop();
        this.clips.get(i).setFramePosition(0);  // Must always rewind!
        this.clips.get(i).start();
    }
    public float getVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(Clip clip, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
