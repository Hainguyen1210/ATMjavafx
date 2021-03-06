package ATMjavafx;

import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;

/*
Load sound files From library
 */
public class Sound {  
  public static AudioClip button = new AudioClip( Sound.class.getResource("lib/buttonPressed.wav").toExternalForm());
  public static AudioClip correct = new AudioClip( Sound.class.getResource("lib/correct.mp3").toExternalForm());
  public static AudioClip error = new AudioClip( Sound.class.getResource("lib/error.wav").toExternalForm());
  public static AudioClip bigError = new AudioClip( Sound.class.getResource("lib/BigError.wav").toExternalForm());
  public static AudioClip kidsLaughter = new AudioClip( Sound.class.getResource("lib/kidsLaughter.wav").toExternalForm());
  public static AudioClip boo = new AudioClip( Sound.class.getResource("lib/boo.wav").toExternalForm());
  public static AudioClip gameOver = new AudioClip( Sound.class.getResource("lib/gameOver.mp3").toExternalForm());
  public static AudioClip printer = new AudioClip( Sound.class.getResource("lib/printer.wav").toExternalForm());
  public static AudioClip coinsOut = new AudioClip( Sound.class.getResource("lib/coinsOut.wav").toExternalForm());
  public static AudioClip cashOut = new AudioClip( Sound.class.getResource("lib/cashOut.wav").toExternalForm());
  public static AudioClip pleaseEnterYourPassword = new AudioClip( Sound.class.getResource("lib/pleaseEnterYourPassword.wav").toExternalForm());
  public static AudioClip background = new AudioClip( Sound.class.getResource("lib/background.wav").toExternalForm());
  
  public static void playBackgoundSound(){
    background.setCycleCount(INDEFINITE);
    background.stop();    background.play();
  } 
}
