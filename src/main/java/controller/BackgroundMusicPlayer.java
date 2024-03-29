package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


public class BackgroundMusicPlayer {

    private static Clip clip;
    private static float volume = 0.8f;

    public static void playBackgroundMusic(String musicFile) {
        try {
            URL resource = BackgroundMusicPlayer.class.getResource(musicFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void setVolume(double volume) {
        BackgroundMusicPlayer.volume = (float) volume;

        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public static float getVolume() {
        return volume;
    }
}
