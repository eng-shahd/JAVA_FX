
package space2;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Sound{
    private MediaPlayer mediaPlayer;
    
    public Sound(String audioFile) {
        try {
            Media media = new Media(new File(audioFile).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.err.println("Error loading audio file: " + e.getMessage());
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            
        }
        
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            
        }
    }
    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }
}
