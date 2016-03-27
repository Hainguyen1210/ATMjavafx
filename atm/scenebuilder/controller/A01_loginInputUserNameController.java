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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
This controller allow user to input user name, it will check whether that user name is available or not 
 */
public class A01_loginInputUserNameController implements Initializable {
  @Override public void initialize(URL url, ResourceBundle rb) {
    //HIDE NODES
    validateUserNameLabel.setVisible(false);
    toRegister.setVisible(false);
    
    System.out.println("checking user name...");
    printCurrentUsers();
    clearOrCheckTextField();
  }  
  
  // get array list of Accounts from the Account class
  private static ArrayList<Account> currentAcountsList = atm.scenebuilder.Account.accountList; 
  public static Account currentAccount;
  private static boolean loginStatus;
  
  @FXML private Label validateUserNameLabel;
  @FXML private TextField userNameField;
  @FXML private Button checkUserNameButton, backButton, clearUserNameButton, toRegister;
  
  @FXML private void goToNextPage()throws IOException {
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/A02_loginInputPassword.fxml"));
    Stage window=(Stage) checkUserNameButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void printCurrentUsers(){
    System.out.println("Current accounts:");
    for (Account currentAccount : currentAcountsList) {
      System.out.println("  "+currentAccount.userName);
    }
  }
  @FXML private void clearOrCheckTextField(){
    userNameField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){try {checkUserName();
        } catch (IOException ex) {Logger.getLogger(A01_loginInputUserNameController.class.getName()).log(Level.SEVERE, null, ex);}
    }
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){clearUserName();}
    });
  }
  @FXML private void clearUserName(){userNameField.clear();userNameField.requestFocus();}
  @FXML private void checkUserName()throws IOException{
    // loop through user names to check
    for (Account currentCheckingAcount : currentAcountsList) {
      loginStatus = (userNameField.getText().equals(currentCheckingAcount.userName));
      if(loginStatus){currentAccount = currentCheckingAcount; break;}  
    }
    // set actions after checking
    if (loginStatus == true){
        System.out.println("User name found.");//-------------------USER NAME FOUND
        goToNextPage();
    }else{
        System.out.println("User name not found.");//-------------------USER NAME NOT FOUND
        // set Nodes visible
        validateUserNameLabel.setVisible(true);
        toRegister.setVisible(true);
    }
  }
  @FXML private void backToPreviousScene()throws IOException{
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/A00_loginAndRegister.fxml"));
    Stage window;
    window=(Stage) backButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void toRegister()throws IOException{
    System.out.println("Going to the bank ");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/B01_registerOption.fxml"));
    Stage window;
    window=(Stage) toRegister.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  
}
