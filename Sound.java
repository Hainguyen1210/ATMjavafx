/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATMjavafx;

import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 *
 * @author Hainguyen
 */
public class Sound {  
  public static AudioClip button = new AudioClip( Sound.class.getResource("lib/buttonPressed.wav").toExternalForm());
  public static AudioClip error = new AudioClip( Sound.class.getResource("lib/error.wav").toExternalForm());
  public static AudioClip bigError = new AudioClip( Sound.class.getResource("lib/BigError.wav").toExternalForm());
  public static AudioClip printer = new AudioClip( Sound.class.getResource("lib/printer.wav").toExternalForm());
  public static AudioClip moneyOut = new AudioClip( Sound.class.getResource("lib/moneyOut.wav").toExternalForm());
  public static AudioClip background = new AudioClip( Sound.class.getResource("lib/background.wav").toExternalForm());
  
  public static void playBackgoundSound(){
    int s = INDEFINITE;
    background.setCycleCount(s);
    background.stop();
    background.play();
  } 
}
