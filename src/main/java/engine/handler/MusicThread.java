package engine.handler;

import game.config.Config;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Thread controlling the music play
 *
 * @author tgutberl
 */
public class MusicThread extends Thread {

  /**
   * Mediaplayer for controlling Music
   */
  private MediaPlayer mediaPlayer;
  /**
   * Media File with Mp3
   */
  private Media media;
  /**
   * Counting for counting frames
   */
  private int count = 0;
  /**
   * Boolean to check if music was interupted
   */
  private boolean stopMusic = false;

  /**
   * Constructor for Music thread
   */
  public MusicThread() {
    try {
      //The Tetris file was copyright approved by the Tetris cooperation. Ask us for more Infos if needed.
      media = new Media(getClass().getResource("/music/Tetris.mp3").toExternalForm());
      mediaPlayer = new MediaPlayer(media);
      mediaPlayer.setCycleCount(10000);
      stopMusic = false;
    } catch (Exception e) {

    }
  }

  /**
   * Run Method for thread
   */
  @Override
  public void run() {
    try {
      while (!this.isInterrupted()) {
        if (Config.getStringValue("MUSIC").equals("ON")) {
          mediaPlayer.setAutoPlay(true);
          mediaPlayer.setMute(false);
          stopMusic = false;
          while (!stopMusic && (count - 8300) < 0) {
            count += 10;
            if (!Config.getStringValue("MUSIC").equals("ON")) {
              mediaPlayer.setMute(true);
              stopMusic = true;
            }
            try {
              Thread.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          count = 0;
        }
      }
    } catch (Exception e) {

    }
  }
}
