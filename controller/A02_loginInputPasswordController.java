package ATMjavafx.controller;

import ATMjavafx.Account;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/*
this class checks user's password and navigate him to the main ATM menu
*/
public class A02_loginInputPasswordController implements Initializable {
  
  // set value
  Account currentAccount = ATMjavafx.controller.A01_loginInputUserNameController.currentAccount;
  boolean hasInstructionPlayed = false, hasPasswordFieldCleared = false;
  int timeRemainingInt = 5;
  
  @FXML private Label validateUserPassword, timeRemaining;
  @FXML private PasswordField userPasswordField;
  @FXML private Button checkUserPasswordButton, backButton, clearUserPasswordButton;
  
  @FXML private void clearUserPassword(){userPasswordField.clear();userPasswordField.requestFocus();}
  @FXML private void checkUserPassword() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    
    //check the password
    if (currentAccount.getUserPassword().equals(userPasswordField.getText())){
      ATMjavafx.Sound.bigError.stop(); 
      ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();
    
        System.out.println("Correct password.");//-------------------CORRECT PASSWORD
        goToMainMenu();
    }else{
      timeRemainingInt -= 1;
      if(!ATMjavafx.Sound.bigError.isPlaying()){ATMjavafx.Sound.bigError.stop(); ATMjavafx.Sound.bigError.play();}
    
        System.out.println("Incorrect password.");//-------------------INCORRECT PASSWORD
        // set Nodes visible
        validateUserPassword.setVisible(true);
        loadTimeRemaining(timeRemainingInt);
    }
  }
  @FXML private void loadTimeRemaining(int timeRemaining) {
      this.timeRemaining.setVisible(true);
    if (timeRemaining == 1) {
      this.timeRemaining.setText("Only one time remaining.");}
    else{
      this.timeRemaining.setText(timeRemaining + " times remaining");}
  }
  @FXML private void clearOrCheckTextField(){
    userPasswordField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {checkUserPassword();}
      catch (IOException ex) {Logger.getLogger(A02_loginInputPasswordController.class.getName()).log(Level.SEVERE, null, ex);}}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword();}
    });
  }
  @FXML private void disableTyping(){
    // -----------------------------------------------NESTED LOOP HERE
    while(hasInstructionPlayed == false) {
      userPasswordField.clear();
      userPasswordField.setVisible(false);
      ATMjavafx.Sound.pleaseEnterYourPassword.stop(); ATMjavafx.Sound.pleaseEnterYourPassword.play();
      while(ATMjavafx.Sound.pleaseEnterYourPassword.isPlaying()) {      userPasswordField.clear();  } // make sure the instruction played before user enter password
      hasInstructionPlayed = true;
      userPasswordField.setVisible(true);  
    }
  }
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
  }  
  
}
