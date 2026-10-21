package gui;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.FloatControl;

/**
 * This class is used to play Audio files.
 */
public class Audio {

    private static AudioClip clip;
    private static AudioClip king;

    /**
     * This function allows to play an audio file and choose if it plays on repeat.
     * @param filePath The path to the audio file.
     * @param loop true if it should loop, false if not.
     * @return The Audio Clip.
     */
    public static AudioClip getAudioClip(String filePath, boolean loop) {
        AudioClip clip = new AudioClip(Audio.class.getResource(filePath).toExternalForm());
        if (loop) {
            clip.setCycleCount(AudioClip.INDEFINITE);
        }
        return clip;
    }

    /**
     * This function starts playing an AudioClip.
     * @param filePath The path to the audio file.
     */
    public static void play(String filePath) {
        clip = new AudioClip(Audio.class.getResource(filePath).toExternalForm());
        clip.play();
    }

    /**
     * This method stops the audio clip.
     */
    public static void stop() {
        clip.stop();
    }

    /**
     * This method stops the audio clips from the king.
     */
    public static void stopKing() {
        king.stop();
    }

    /**
     * This method plays the click sound after a button is pressed.
     */
    public static void buttonOne() {
        AudioClip button = new AudioClip(Audio.class.getResource("/UI-Sounds/click1.wav").toExternalForm());
        button.play();
    }

    /**
     * This method plays an error sound after an error occurs.
     */
    public static void error() {
        AudioClip error = new AudioClip(Audio.class.getResource("/UI-Sounds/error_006.wav").toExternalForm());
        error.play();
    }

    /**
     * This method plays the intro music when starting the client.
     */
    public static void intromusic() {
        if (clip == null) {
            play("/UI-Sounds/intromusic.mp3");
            return;
        }
        if (clip.isPlaying()) {
            stop();
        }
        play("/UI-Sounds/intromusic.mp3");
    }

    /**
     * This method plays the game music when starting the game.
     */
    public static void gamemusic() {
        stop();
        clip = new AudioClip(Audio.class.getResource("/UI-Sounds/gamemusic.mp3").toExternalForm());
        clip.play();
    }

    /**
     * This function is used to play the kings voice audios.
     * @param filePath The path to the audio.
     */
    public static void king(String filePath) {
        if (king != null && king.isPlaying()) {
            stopKing();
        }
        king = new AudioClip(Audio.class.getResource(filePath).toExternalForm());
        king.play();
    }

    /**
     * This function is used to play the kings voice audios.
     * @param filePath The path to the audio.
     */
    public static void kingAdd(String filePath) {
        if (king != null && !king.isPlaying()) {
            king = new AudioClip(Audio.class.getResource(filePath).toExternalForm());
            king.play();
        }
    }

    /**
     * This method plays the kings audio when the game starts.
     */
    public static void gamestart() {
        gamemusic();
        king("/UI-Sounds/gamestart.mp3");
    }
}
