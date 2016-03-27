/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.scenebuilder;

import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 *
 * @author Hainguyen
 */
public class Sound {  
  public static AudioClip button = new AudioClip( Sound.class.getResource("lib/Pickup_Coin.wav").toExternalForm());
  public static AudioClip selection = new AudioClip( Sound.class.getResource("lib/Powerup.wav").toExternalForm());
  public static AudioClip error = new AudioClip( Sound.class.getResource("lib/searching.wav").toExternalForm());
  public static AudioClip printer = new AudioClip( Sound.class.getResource("lib/typewriter.wav").toExternalForm());
  public static AudioClip background = new AudioClip( Sound.class.getResource("lib/typewriter.wav").toExternalForm());
  
  public static void playBackgoundSound(){
    int s = INDEFINITE;
    background.setCycleCount(s);
    background.stop();
    background.play();
  } 
}
