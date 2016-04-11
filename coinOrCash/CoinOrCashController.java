/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATMjavafx.coinOrCash;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hainguyen
 */
public class CoinOrCashController implements Initializable {

  /**
   * Initializes the controller class.
   */
  Stage window;
  @FXML ImageView coin, cash;
  
  @FXML private void chooseCash(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if (Math.random() < 0.2) {    ATMjavafx.Sound.cashOut.stop(); ATMjavafx.Sound.cashOut.play(); }
    else {    ATMjavafx.Sound.coinsOut.stop(); ATMjavafx.Sound.coinsOut.play();}
    
    this.window = (Stage) cash.getScene().getWindow();
    window.close();
  }
  @FXML private void chooseCoin(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if (Math.random() < 0.5) {    ATMjavafx.Sound.coinsOut.stop(); ATMjavafx.Sound.coinsOut.play(); }
    else {    ATMjavafx.Sound.cashOut.stop(); ATMjavafx.Sound.cashOut.play();}
    
    this.window = (Stage) cash.getScene().getWindow();
    window.close();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
}
