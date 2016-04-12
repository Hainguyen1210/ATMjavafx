package ATMjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 1. ask user whether want to choose Login or Register
 * 2. can use keyboard shortcuts ENTER to Login
 * 3. Enable or Disable annoying background sound effect
 */
public class A00_loginAndRegisterController implements Initializable {
  @FXML ImageView mute1, unmute2, mute3, unmute4;
  
  @FXML private Button chooseLoginOptionButton, chooseRegisterOptiontuButton;
  @FXML private void chooseLoginOption()throws IOException{
    
    ATMjavafx.Sound.button.stop();
    ATMjavafx.Sound.button.play();
    
    System.out.println("choose login option");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A01_loginInputUserName.fxml"));
    Stage window=(Stage) chooseLoginOptionButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void chooseRegisterOption()throws IOException{
    
    ATMjavafx.Sound.button.stop();
    ATMjavafx.Sound.button.play();
    
    System.out.println("choose register option");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/B01_registerOption.fxml"));
    Stage window=(Stage) chooseRegisterOptiontuButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }  
  @FXML private void enterOnFocus(){
    chooseLoginOptionButton.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {
        chooseLoginOption();
        } catch (IOException ex) {Logger.getLogger(A00_loginAndRegisterController.class.getName()).log(Level.SEVERE, null, ex);}}
    });
    chooseRegisterOptiontuButton.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {
        chooseRegisterOption();
        } catch (IOException ex) {Logger.getLogger(A00_loginAndRegisterController.class.getName()).log(Level.SEVERE, null, ex);}}
    });
  }
            
  @FXML private void mute1Pressed(){
    ATMjavafx.Sound.button.stop();ATMjavafx.Sound.button.play();
    mute1.setVisible(false);unmute2.setVisible(true);
    ATMjavafx.Sound.background.stop();
  }
  @FXML private void unmute2Pressed(){
    ATMjavafx.Sound.button.stop();ATMjavafx.Sound.button.play();
    unmute2.setVisible(false);mute3.setVisible(true);
    ATMjavafx.Sound.playBackgoundSound();
  }
  @FXML private void mute3Pressed(){
    ATMjavafx.Sound.button.stop();ATMjavafx.Sound.button.play();
    mute3.setVisible(false);unmute4.setVisible(true);
    ATMjavafx.Sound.background.stop();
  }
  @FXML private void unmute4Pressed(){
    ATMjavafx.Sound.button.stop();ATMjavafx.Sound.button.play();
    unmute4.setVisible(false);mute1.setVisible(true);
    ATMjavafx.Sound.playBackgoundSound();
  }

  @Override public void initialize(URL url, ResourceBundle rb) {
    enterOnFocus();
  }  
  
}
