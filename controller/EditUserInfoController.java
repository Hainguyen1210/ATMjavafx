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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 1. check user inputs and edit user's information
 * 2. can use keyboard shortcuts
 *      ENTER / TAB to check 
 */
public class EditUserInfoController implements Initializable {
  @Override public void initialize(URL url, ResourceBundle rb) {
    assignCheckOnENTER();
    assignCheckOnTAB();
  }
  
  Account currentAccount = ATMjavafx.controller.A01_loginInputUserNameController.currentAccount;
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
    for(Account checkingAccount : Account.accountList){
      if(userName.equals(currentAccount.userName)) {break;}//in case user name remain the same
      if (checkingAccount.userName.equals(userName)
              || userName.length()<4
              || userNameField.getText().contains(" ")) {
        isUserNameAvailable=false;
        userNameLabel.setTextFill(Color.RED);
        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play(); 
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
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play(); 
      passwordLabel.setTextFill(Color.RED);
      isPasswordValid=false;
    }
  }
  @FXML private void checkRealName(){
    if(realNameField.getText().length()<3){
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play(); 
      realNameLabel.setTextFill(Color.RED);
      isRealNameValid=false;}// Invalid
    else{
      realNameLabel.setTextFill(Color.GREEN);
      isRealNameValid=true;// Valid
      finalRealName = realNameField.getText().replaceAll(" ", "_");}//get the valid value
  }
  @FXML private void checkGender(){
    // this method accept "male" and "female", any other Input will be treat as "other"
    switch (genderField.getText().toLowerCase()) {
      case "male": case "man": 
        genderLabel.setTextFill(Color.GREEN);
        male.setVisible(true);female.setVisible(false);//show Male
        finalGender=genderField.getText().toLowerCase();//get the valid value
        break;
        
      case "female" :
        genderLabel.setTextFill(Color.GREEN);
        male.setVisible(false);female.setVisible(true);//show female
        finalGender=genderField.getText().toLowerCase();//get the valid value
        break;
  
      default:
        genderLabel.setTextFill(Color.GREEN);
        male.setVisible(false);female.setVisible(false);//hide all
        genderField.setText("other");finalGender="other";
        break;
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
        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
        }
    } 
    catch (Exception e) {
      isAgeValid=false;
      ageField.clear();
      AgeLabel.setTextFill(Color.RED);
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
    } 
  }
  
  @FXML private void backToMain() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/C01_atmMainMenu.fxml"));
    Stage window=(Stage) saveUserInfoButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void saveUserInfo() throws IOException{
    //check all the Field, If valid -> change the account's information -> save data to file
    checkUserName();checkPasswords();checkRealName();checkGender();checkAge();
    if(isUserNameAvailable&&isPasswordValid&&isRealNameValid&&isGenderValid&&isAgeValid) {
      ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();
      currentAccount.changeUserInfo(finalUserName, finalPassword, finalRealName, finalGender, finalAge);
      Account.saveUserData();
      backToMain();
    } else{    ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();    }
  }
  
  @FXML private void assignCheckOnTAB(){
    // check user input on each TAB
  userNameField.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkUserName();}});
  
  password2Field.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkPasswords();}});
  
  realNameField.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkRealName();}});
  
  genderField.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkGender();}});
  
  ageField.setOnKeyPressed((KeyEvent keyInput) -> {
    if (keyInput.getCode().equals(KeyCode.TAB)) {checkAge();}});
  
  }
  @FXML private void assignCheckOnENTER(){
    // check all user's inputs on ENTER
    TextField []textFields = {userNameField, password1Field, password2Field, realNameField, genderField, ageField};
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
}
