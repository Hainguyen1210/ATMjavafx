package ATMjavafx.controller;

import ATMjavafx.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * 1. check user inputs and create new account if they are VALID
 * 2. support keyboard shortcuts
 *      TAB or ENTER to check
 *      ESCAPE to clear
*/
public class B01_registerOptionController implements Initializable {
  private static boolean isUserNameAvailable, isPasswordValid;
  private static String finalUserName, finalPassword;
  @FXML public PasswordField userPassword1Field, userPassword2Field;
  @FXML public TextField UserNameField;
  @FXML public Label userNameAvailableLable, userNameUnavailableLable, validLabel, invalidLabel;
  @FXML public Button clearUserNameButton, clearUserPassword1Button, clearUserPassword2Button,
          backButton, registerButton;
  
  @FXML private void backToPreviousScene()throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A00_loginAndRegister.fxml"));
    Stage window;
    window=(Stage) backButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  
  @FXML private void clearUserName(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    UserNameField.clear(); registerButton.setVisible(false);UserNameField.requestFocus();}
  @FXML private void clearUserPassword1(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    userPassword1Field.clear(); registerButton.setVisible(false); userPassword1Field.requestFocus();}
  @FXML private void clearUserPassword2(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    userPassword2Field.clear(); registerButton.setVisible(false); userPassword2Field.requestFocus();}
  @FXML private void clearOrCheckTextField(){
    // this method allows user to user keyboard shortcuts to check (clear) textField instead of using mouse
    UserNameField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode() == KeyCode.ENTER 
      || keyInput.getCode() == KeyCode.BACK_SPACE
      || keyInput.getCode() == KeyCode.SPACE){checkUserName();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserName();}});
    
    userPassword1Field.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode() == KeyCode.ENTER
      || keyInput.getCode() == KeyCode.BACK_SPACE
      || keyInput.getCode() == KeyCode.SPACE){checkPasswords();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword1();}});
    
    userPassword2Field.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode() == KeyCode.ENTER
      || keyInput.getCode() == KeyCode.BACK_SPACE
      || keyInput.getCode() == KeyCode.SPACE){checkPasswords();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword2();}});
  }
  
  @FXML private void checkUserName(){
    isUserNameAvailable=true;
    userNameAvailableLable.setVisible(true);userNameUnavailableLable.setVisible(false);
    String currentUserName = UserNameField.getText();
    
    for(Account checkingAccount : Account.accountList){
      if (checkingAccount.userName.equals(currentUserName)
              || currentUserName.length()<4
              || currentUserName.contains(" ")) {
        isUserNameAvailable=false;
        userNameUnavailableLable.setVisible(true);userNameAvailableLable.setVisible(false);
        break;
      }
    }
      if (isUserNameAvailable==true) {
        ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();  
      } else {
        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
      }
    
    finalUserName=UserNameField.getText();
    enableRegister();
  }
  @FXML private void checkPasswords(){
    if(((!userPassword1Field.getText().equals(userPassword2Field.getText())) 
            || userPassword1Field.getText().length() <= 2) 
            || userPassword1Field.getText().contains(" ")){
      validLabel.setVisible(false);invalidLabel.setVisible(true);
      isPasswordValid=false;
        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
    }else{
      validLabel.setVisible(true);invalidLabel.setVisible(false);
      isPasswordValid=true;
        ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();  
        
      finalPassword=userPassword1Field.getText();
      enableRegister();
    }
  }
  

  @FXML private void enableRegister(){
    // this method check both input and show the Register button
    if(isPasswordValid&&isUserNameAvailable){
      registerButton.setVisible(true);// VALID
    }else{
      registerButton.setVisible(false);// INVALID
    }
  }
  @FXML private void registerButtonPressed() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    creatAccount();
    Account.saveUserData();
    backToPreviousScene();
  }
  @FXML private void creatAccount(){
    checkUserName();checkPasswords(); // check the last time
    try {
      if(isPasswordValid&&isUserNameAvailable){
        Account.accountList.add(new Account(
                finalUserName, finalPassword, 0, "unknown", 18, "unknown", 
                0, 0, 0, 0, 99999999,
                0, 0, 0, 0, 99999999));
        System.out.println("new object created.");
      }
    } catch (Exception e) {
      System.out.println("Unable to create new object");
    }
  }
  
  @FXML private void checkOnTAB(){
    UserNameField.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkUserName();}});
  
    userPassword2Field.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkPasswords();}});
  
  }
  @Override public void initialize(URL url, ResourceBundle rb) {
    Account.printCurrentUser();
    clearOrCheckTextField();
    checkOnTAB();
  }  
}
