package ATMjavafx.controller;

import ATMjavafx.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
this class checks user's password and navigate him to the main ATM menu
*/
public class A02_loginInputPasswordController implements Initializable {
  
  // set value
  Account currentAccount = ATMjavafx.controller.A01_loginInputUserNameController.currentAccount;
  boolean hasInstructionPlayed = false, hasPasswordFieldCleared = false;
  int timeRemainingInt = 3;
  
  @FXML private Label validateUserPassword, timeRemaining, waitForSoundLabel;
  
  @FXML private PasswordField userPasswordField;
  @FXML private Button checkUserPasswordButton, backButton, clearUserPasswordButton;
  static FadeTransition fade;
  @FXML private void clearUserPassword(){userPasswordField.clear();userPasswordField.requestFocus();}
  @FXML private void checkUserPassword() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    
    // User have to wait for the sound to reInput the password
    if (  !ATMjavafx.Sound.bigError.isPlaying() &&  
          !ATMjavafx.Sound.kidsLaughter.isPlaying() &&
          !ATMjavafx.Sound.boo.isPlaying() )
    {
      //check the password
      if (currentAccount.getUserPassword().equals(userPasswordField.getText())){    //-------------------CORRECT PASSWORD
        ATMjavafx.Sound.bigError.stop();ATMjavafx.Sound.kidsLaughter.stop(); ATMjavafx.Sound.boo.stop(); 
        ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();

          System.out.println("Correct password.");
          goToMainMenu();
      }else{ //-------------------------------------------------------------------------------------------------------------INCORRECT PASSWORD
        switch(timeRemainingInt) {
          case 3: 
            if(!ATMjavafx.Sound.bigError.isPlaying()){ATMjavafx.Sound.bigError.stop(); ATMjavafx.Sound.bigError.play();};
            break;
          case 2: 
            if(!ATMjavafx.Sound.kidsLaughter.isPlaying()){ATMjavafx.Sound.kidsLaughter.stop(); ATMjavafx.Sound.kidsLaughter.play();};
            break;
          case 1: 
            this.timeRemaining.setText("Your account is being locked.");
            if(!ATMjavafx.Sound.boo.isPlaying()){ATMjavafx.Sound.boo.stop(); ATMjavafx.Sound.boo.play();};
            break;
                      
        }
        timeRemainingInt -= 1;
        

          System.out.println("Incorrect password." + timeRemainingInt);
          // set Nodes visible
          validateUserPassword.setVisible(true);
          loadTimeRemaining(timeRemainingInt);
          
          // delete User
          if (timeRemainingInt == 0){  //-------------------Nested loop here
            try {
              System.out.println("deleting User");
              while (ATMjavafx.Sound.boo.isPlaying()) {}  //wait for the sound to finish
              ATMjavafx.Sound.boo.stop();
              currentAccount.deleteThisUser();
              Account.saveUserData();

              ATMjavafx.Sound.gameOver.stop(); ATMjavafx.Sound.gameOver.play();
              backToPreviousScene();              
            } catch (Exception e) {
            System.out.println("failed to delete user");
            }
          }
      }
    } else{
      System.out.println("wait");     
      fade.play();
    }
  }
  @FXML private void loadTimeRemaining(int timeRemaining) {
      this.timeRemaining.setVisible(true);
    switch (timeRemainingInt) {
      case 1:
        this.timeRemaining.setText("Only one time remaining.");
        break;
      case 0:
        this.timeRemaining.setText("Your account is being locked.");
        break;
      default:
        this.timeRemaining.setText(timeRemaining + " times remaining");
        break;
    }
  }
  @FXML private void clearOrCheckTextField(){
    userPasswordField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {checkUserPassword();}
      catch (IOException ex) {Logger.getLogger(A02_loginInputPasswordController.class.getName()).log(Level.SEVERE, null, ex);}}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword();}
    });
  }
  @FXML private void disableTyping() throws InterruptedException{
    //this function disable user from typing while rthe instruction playing
    while(hasInstructionPlayed == false) {
      userPasswordField.setVisible(false);
      ATMjavafx.Sound.pleaseEnterYourPassword.stop(); ATMjavafx.Sound.pleaseEnterYourPassword.play();
      while(ATMjavafx.Sound.pleaseEnterYourPassword.isPlaying()) {      Thread.sleep(2000); userPasswordField.clear();  } // make sure the instruction played before user enter password
      
      hasInstructionPlayed = true;
      userPasswordField.setVisible(true);  
    }
      Thread.sleep(1000);
      userPasswordField.clear();
  }    // -------------------------------NESTED LOOP HERE
  @FXML private void goToMainMenu()throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    
    System.out.println("Go to the Main Menu");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/C01_atmMainMenu.fxml"));
    Stage window;
    window=(Stage) checkUserPasswordButton.getScene().getWindow();
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void backToPreviousScene()throws IOException{
    ATMjavafx.Sound.bigError.stop(); 
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A01_loginInputUserName.fxml"));
    Stage window=(Stage) backButton.getScene().getWindow();
    window.setScene(new Scene(root));
    window.show();
  }
  @Override public void initialize(URL url, ResourceBundle rb) {
    validateUserPassword.setVisible(false);
    System.out.println("Current account's password: " + currentAccount.getUserPassword());
    clearOrCheckTextField();
    
    //setup transition
    fade = new FadeTransition(Duration.millis(2000), waitForSoundLabel);
    fade.setAutoReverse(true);fade.setCycleCount(1);fade.setFromValue(1.0);fade.setToValue(0.0);
    fade.play();
  }  
  
}
