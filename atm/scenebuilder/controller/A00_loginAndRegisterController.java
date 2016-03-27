/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.scenebuilder.controller;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hainguyen
 */
public class A00_loginAndRegisterController implements Initializable {  
  @FXML private Button chooseLoginOptionButton, chooseRegisterOptiontuButton;
  @FXML private void chooseLoginOption()throws IOException{
    System.out.println("choose login option");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/A01_loginInputUserName.fxml"));
    Stage window=(Stage) chooseLoginOptionButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void chooseRegisterOption()throws IOException{
    System.out.println("choose register option");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/B01_registerOption.fxml"));
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
            

  @Override public void initialize(URL url, ResourceBundle rb) {
    enterOnFocus();
  }  
  
}
