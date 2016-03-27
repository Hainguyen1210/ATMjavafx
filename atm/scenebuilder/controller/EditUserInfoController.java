/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.scenebuilder.controller;

import atm.scenebuilder.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hainguyen
 */
public class EditUserInfoController implements Initializable {
  Account currentAccount = atm.scenebuilder.controller.A01_loginInputUserNameController.currentAccount;
  private static ArrayList<Account> currentAcountsList = atm.scenebuilder.Account.accountList; 
  private boolean isUserNameAvailable, isPasswordValid, isRealNameValid, isGenderValid, isAgeValid;
  private String finalUserName, finalPassword, finalRealName, finalGender;
  private int finalAge;
  @FXML Label userNameLabel, passwordLabel, realNameLabel, genderLabel, AgeLabel;
  @FXML TextField userNameField, password1Field, password2Field, realNameField, genderField, ageField;
  @FXML Button saveUserInfoButton;
  @FXML ImageView male, female;
  
  @FXML private void checkUserName(){
    isUserNameAvailable=true;
    userNameLabel.setTextFill(Color.GREEN);
    String userName = userNameField.getText().replaceAll("\\s+","");
    for(Account checkingAccount : currentAcountsList){
      if(userName.equals(currentAccount.userName)) {break;}//in case user name remain the same
      if (checkingAccount.userName.equals(userName)
              || userName.length()<4) {
        isUserNameAvailable=false;
        userNameLabel.setTextFill(Color.RED);
        break;
      }
    }
    finalUserName=userName;
  }
  @FXML private void checkPasswords(){
    if((password1Field.getText().equals(password2Field.getText()))
            &&password1Field.getText().length()>2){
      passwordLabel.setTextFill(Color.GREEN);
      isPasswordValid=true;
      finalPassword=password1Field.getText();}//get the valid value
    else{
      passwordLabel.setTextFill(Color.RED);
      isPasswordValid=false;
    }
  }
  @FXML private void checkRealName(){
    if(realNameField.getText().length()<3){
      realNameLabel.setTextFill(Color.RED);
      isRealNameValid=false;}// Invalid
    else{
      realNameLabel.setTextFill(Color.GREEN);
      isRealNameValid=true;// Valid
      finalRealName = realNameField.getText().replaceAll(" ", "_");}//get the valid value
  }
  @FXML private void checkGender(){
    // this method accept "male" and "female", any other Input will be treat as "other"
    if(genderField.getText().toLowerCase().equals("male")) {
      genderLabel.setTextFill(Color.GREEN);
      male.setVisible(true);female.setVisible(false);//show Male
      finalGender=genderField.getText().toLowerCase();//get the valid value
    }
    else if(genderField.getText().toLowerCase().equals("female")) {
      genderLabel.setTextFill(Color.GREEN);
      male.setVisible(false);female.setVisible(true);//show female
      finalGender=genderField.getText().toLowerCase();//get the valid value
    }else{
      genderLabel.setTextFill(Color.GREEN);
      male.setVisible(false);female.setVisible(false);//hide all
      genderField.setText("other");finalGender="other";
    }
    isGenderValid=true;
  }
  @FXML private void checkAge(){
    try {
      int age =Integer.parseInt(ageField.getText());
      if (18<=age && age<=120){
        finalAge = age;
        isAgeValid=true;
        AgeLabel.setTextFill(Color.GREEN);}
      else{
        isAgeValid=false;
        AgeLabel.setTextFill(Color.RED);
        }
    } 
    catch (Exception e) {
      isAgeValid=false;
      ageField.clear();
      AgeLabel.setTextFill(Color.RED);}
  }
  
  @FXML private void backToMain() throws IOException{
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/C01_atmMainMenu.fxml"));
    Stage window=(Stage) saveUserInfoButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void saveUserInfo() throws IOException{
    //check all the Field, If valid -> change the account's information -> save data to file
    checkUserName();checkPasswords();checkRealName();checkGender();checkAge();
    if(isUserNameAvailable&&isPasswordValid&&isRealNameValid&&isGenderValid&&isAgeValid) {
      currentAccount.changeUserInfo(finalUserName, finalPassword, finalRealName, finalGender, finalAge);
      Account.saveUserData();
      backToMain();
    }
  }
  
  @FXML private void assignSaveOnENTER(){
    TextField[] textFields = {userNameField, password1Field, password2Field, realNameField, genderField, ageField};
    for(TextField i : textFields){
      i.setOnKeyReleased((KeyEvent keyInput) -> {
        if(keyInput.getCode().equals(KeyCode.ENTER))
        {
          try {saveUserInfo();}
          catch (IOException ex) {Logger.getLogger(EditUserInfoController.class.getName()).log(Level.SEVERE, null, ex);}
        }
      });
    }
  }
  
  @Override public void initialize(URL url, ResourceBundle rb) {
    assignSaveOnENTER();
  }  
}
