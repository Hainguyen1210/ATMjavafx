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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
* 1. This controller allow user to input user name, it will check whether that user name is available or not
*   if NOT, Register options will appear
* 2. can use Keyboard shortcuts
*     ENTER to check user name
*     ESCAPE to clear the Field
*/
public class A01_loginInputUserNameController implements Initializable {
  @Override public void initialize(URL url, ResourceBundle rb) {
    //HIDE NODES
    validateUserNameLabel.setVisible(false);
    toRegister.setVisible(false);
    
    System.out.println("checking user name...");
    Account.printCurrentUser();
    clearOrCheckTextField();
  }  
  
  public static Account currentAccount;
  private static boolean loginStatus;
  
  @FXML private Label validateUserNameLabel;
  @FXML private TextField userNameField;
  @FXML private Button checkUserNameButton, backButton, clearUserNameButton, toRegister;
  
  @FXML private void goToNextPage()throws IOException {
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A02_loginInputPassword.fxml"));
    Stage window=(Stage) checkUserNameButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void backToPreviousScene()throws IOException{
    
    ATMjavafx.Sound.button.stop();    ATMjavafx.Sound.button.play();
    
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A00_loginAndRegister.fxml"));
    Stage window;
    window=(Stage) backButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void toRegister()throws IOException{
    
    ATMjavafx.Sound.button.stop();    ATMjavafx.Sound.button.play();
    
    System.out.println("Going to the bank ");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/B01_registerOption.fxml"));
    Stage window;
    window=(Stage) toRegister.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  
  @FXML private void clearOrCheckTextField(){
    userNameField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {checkUserName();
        } catch (IOException ex) {Logger.getLogger(A01_loginInputUserNameController.class.getName()).log(Level.SEVERE, null, ex);}
    }
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){
        //if the TextField is empty, back to previous Scene instead
        if(userNameField.getLength()<1) {   try {backToPreviousScene();} catch (IOException ex) {Logger.getLogger(A01_loginInputUserNameController.class.getName()).log(Level.SEVERE, null, ex);  }}
        else{clearUserName();}
      }
    });
  }
  @FXML private void clearUserName(){
    ATMjavafx.Sound.button.stop();    ATMjavafx.Sound.button.play();
    userNameField.clear();userNameField.requestFocus();
  }
  @FXML private void checkUserName()throws IOException{
    ATMjavafx.Sound.button.stop();    ATMjavafx.Sound.button.play();
    
// loop through user names to check
    for (Account currentCheckingAcount : Account.accountList) {
      
      loginStatus = (userNameField.getText().equals(currentCheckingAcount.userName));
      if(loginStatus){currentAccount = currentCheckingAcount; break;}  
    }
    // set actions after checking
    if (loginStatus == true){
      ATMjavafx.Sound.correct.stop();    ATMjavafx.Sound.correct.play();
      
      System.out.println("User name found. " + currentAccount.userName);//-------------------USER NAME FOUND  
        goToNextPage();
    }else{
        ATMjavafx.Sound.error.stop();    ATMjavafx.Sound.error.play();

        System.out.println("User name not found.");//-------------------USER NAME NOT FOUND
        // set Nodes visible
        validateUserNameLabel.setVisible(true);
        toRegister.setVisible(true);
    }
  }

}
