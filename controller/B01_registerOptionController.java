package ATMjavafx.controller;

import ATMjavafx.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

/*
this controller allows user to create new account with userName and passwords
program also check the validity of user's input
*/
public class B01_registerOptionController implements Initializable {
  private static ArrayList<Account> currentAcountsList = ATMjavafx.Account.accountList; 
  private static boolean isUserNameAvailable, isPasswordValid;
  private static String finalUserName, finalPassword;
  @FXML public PasswordField userPassword1Field, userPassword2Field;
  @FXML public TextField UserNameField;
  @FXML public Label userNameAvailableLable, userNameUnavailableLable, validLabel, invalidLabel;
  @FXML public Button clearUserNameButton, clearUserPassword1Button, clearUserPassword2Button,
          backButton, checkInputButton, registerButton;
  
  @FXML private void backToPreviousScene()throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A00_loginAndRegister.fxml"));
    Stage window;
    window=(Stage) backButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  
  @FXML private void toggleRegisterButton(){
    registerButton.setVisible(false);checkInputButton.setVisible(true);}
  @FXML private void clearUserName(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    UserNameField.clear(); toggleRegisterButton();UserNameField.requestFocus();}
  @FXML private void clearUserPassword1(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    userPassword1Field.clear(); toggleRegisterButton();userPassword1Field.requestFocus();}
  @FXML private void clearUserPassword2(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    userPassword2Field.clear(); toggleRegisterButton();userPassword2Field.requestFocus();}
  @FXML private void clearOrCheckTextField(){
    // this method allows user to user keyboard shortcuts to check (clear) textField instead of using mouse
    UserNameField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){checkUserName();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserName();}});
    userPassword1Field.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){checkPasswords();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword1();}});
    userPassword2Field.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){checkPasswords();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserPassword2();}});
  }
  
  @FXML private void checkUserName(){
    isUserNameAvailable=true;
    userNameAvailableLable.setVisible(true);userNameUnavailableLable.setVisible(false);
    String currentUserName = UserNameField.getText();
    
    for(Account checkingAccount : currentAcountsList){
      if (checkingAccount.userName.equals(currentUserName)
              || currentUserName.length()<4
              || currentUserName.contains(" ")) {
        isUserNameAvailable=false;
        userNameUnavailableLable.setVisible(true);userNameAvailableLable.setVisible(false);
        break;
      }
      if (isUserNameAvailable==true) {
        ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();  
      } else {
        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
      }
    }
    
    finalUserName=UserNameField.getText();
  }
  @FXML private void checkPasswords(){
    if(((!userPassword1Field.getText().equals(userPassword2Field.getText())) 
            || userPassword1Field.getText().length() <= 2) 
            || userPassword1Field.getText().contains(" ")){
      validLabel.setVisible(false);invalidLabel.setVisible(true);
      isPasswordValid=false;
    }else{
      validLabel.setVisible(true);invalidLabel.setVisible(false);
      isPasswordValid=true;
      finalPassword=userPassword1Field.getText();
    }
  }
  

  @FXML private void checkButtonPressed(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    checkUserName();
    checkPasswords();
    if(isPasswordValid&&isUserNameAvailable){
      registerButton.setVisible(true);checkInputButton.setVisible(false);// VALID
      ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();
    }else{
      registerButton.setVisible(false);checkInputButton.setVisible(true);// INVALID
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
    }
  }
  @FXML private void registerButtonPressed() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    creatAccount();
    Account.saveUserData();
    backToPreviousScene();
  }
  @FXML private void creatAccount(){
    try {
      if(isPasswordValid&&isUserNameAvailable){
        currentAcountsList.add(new Account(finalUserName, finalPassword, 0, "unknown", 18, "unknown"));
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
