package ATMjavafx.coinOrCash;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*
 * FXML Controller class
 Ask user whether withdraw cash or coins
 */
public class CoinOrCashController implements Initializable {
  Stage window;
  @FXML ImageView coin, cash;
  
  @FXML private void chooseCash(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if (Math.random() > 0.2) {    ATMjavafx.Sound.cashOut.stop(); ATMjavafx.Sound.cashOut.play(); }
    else {    ATMjavafx.Sound.coinsOut.stop(); ATMjavafx.Sound.coinsOut.play();}
    
    this.window = (Stage) cash.getScene().getWindow();
    window.close();
  }
  @FXML private void chooseCoin(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if (Math.random() > 0.2) {    ATMjavafx.Sound.coinsOut.stop(); ATMjavafx.Sound.coinsOut.play(); }
    else {    ATMjavafx.Sound.cashOut.stop(); ATMjavafx.Sound.cashOut.play();}
    
    this.window = (Stage) cash.getScene().getWindow();
    window.close();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {}  
  
}
