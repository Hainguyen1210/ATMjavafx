package ATMjavafx.controller;

import ATMjavafx.Account;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
this controller currently allows users to view their information, deposit money
***this controller will have more functions, such as: edit user's information, withdraw money, 
*/
public class C01_atmMainMenuController implements Initializable {
   // set value
  Account currentAccount = ATMjavafx.controller.A01_loginInputUserNameController.currentAccount;
  private long balanceAmount,  remainingAmount;
  public static long withdrawalAmount;
  DecimalFormat format = new DecimalFormat(); // this object formats money's display
  Button[] buttonList;
  int[] amountOfMoneyList = {1200, 55000, 200200,  555000, 1001200, 2000500, 10001000};
  
  @FXML ImageView trollImg;
  @FXML private TextField 
          editUserNameField, editUserRealNameField, editUserGenderField,editUserAgeField, depositField;
  @FXML private Button   
          quitButton, saveUserInfo, editUserInfo,depositButton, confirmDepositButton,
          // these buttons are the Withdraw buttons
          withdraw1kButton, withdraw50kButton,withdraw200kButton, withdraw500kButton,
          withdraw1000kButton, withdraw2000kButton, withdraw10000kButton, withdrawAllButton;
  @FXML private Label 
          accountNameLabel, accountPasswordLabel, accountBalanceLabel,ownerRealNameLabel, 
          ownerGenderLabel, ownerAgeLabel, checkDepositLabel, remainingAmountLabel, 
          withdrawalAmountLabel, balanceAmountLabel;
  
  public boolean isNumber(String input){
    /*check whether input is a number or not*/
    try {
      Long.parseLong(input);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
  @FXML private void backToLogin()throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/A01_loginInputUserName.fxml"));
    Stage window=(Stage) quitButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void toQuestion()throws IOException{
    System.out.println("to security questions");
    
    Stage mainWindow=(Stage) quitButton.getScene().getWindow(); // this code is to get the main window
    
    Stage popupWindow = new Stage();
    popupWindow.initModality(Modality.APPLICATION_MODAL);
    popupWindow.initStyle(StageStyle.UNDECORATED);
    popupWindow.initOwner(mainWindow);
    
    Parent root = FXMLLoader.load(ATMjavafx.question.QuestionController.class.getResource("question.fxml"));
    popupWindow.setScene(new Scene(root));
    popupWindow.show(); 
  }
  
  @FXML private void editUserInfo()throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    System.out.println("saving user's info");
    Parent root = FXMLLoader.load(ATMjavafx.ATMSceneBuilder.class.getResource("fxml/editUserInfo.fxml"));
    Stage window=(Stage) quitButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void loadUserInfo(){
    // this method load all user's info to the scene by replacing the default text.
      //this first part contains labels in the tab "Account status"
    accountNameLabel.setText(currentAccount.userName);
    accountBalanceLabel.setText("VND " + format.format(currentAccount.getUserBalance()));
    ownerRealNameLabel.setText(currentAccount.getUserRealName().replace("_", " "));
    ownerGenderLabel.setText(currentAccount.getUserGender());
    ownerAgeLabel.setText(Integer.toString(currentAccount.getUserAge()));
      //this part contains labels in the tab "Withdrawal"
    withdrawalAmount=0;
    balanceAmount = remainingAmount=currentAccount.getUserBalance();
    balanceAmountLabel.setText("VND " + format.format(balanceAmount));
    remainingAmountLabel.setText("VND " + format.format(remainingAmount));
    withdrawalAmountLabel.setText("VND " + format.format(withdrawalAmount));
  }

  @FXML private void depositButtonPressed(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    depositButton.setVisible(false);
    confirmDepositButton.setVisible(true);
    depositField.setVisible(true);
    depositField.requestFocus();
  }
  @FXML private void confirmDepositButtonPressed(){
    /* this method checks whether the user's input is valid or not
    if it is valid, these number will be added to the user.balance and then save it to the text file right away
    */
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if (isNumber(depositField.getText())&&depositField.getText().length()>0){
      ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();
      currentAccount.deposit(Long.parseLong(depositField.getText()));
      checkDepositLabel.setVisible(false);
    }else{
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
      checkDepositLabel.setVisible(true);
    }
    depositField.clear();
    depositField.requestFocus();
    loadUserInfo();
    // save to txt
    Account.saveUserData();
  }
  @FXML private void assignKeyboard(){
    //this function allow user to use keyboard instead of mouse to deposit or clear
    depositButton.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){depositButtonPressed();}});
    
    depositField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){confirmDepositButtonPressed();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){depositField.clear();}});
    
  }
  
  @FXML private void assignButtonEvents(){
    /*this function assign withdrawal events on each withdrawal button*/
    
    // assign event to all the withdraw buttons
    for (int i=0; i < buttonList.length; i++) {
      int amountOfMoney = amountOfMoneyList[i];
      buttonList[i].setOnAction((ActionEvent e ) -> {
        ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
        reloadWithdrawalAmount(amountOfMoney);
        
      });
    }
    //especially for withdraw all the money button
    withdrawAllButton.setOnAction((ActionEvent e ) -> {
        ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
        withdrawalAmount = currentAccount.getUserBalance();
        remainingAmount = 0;
        withdrawalAmountLabel.setText("VND " + format.format(withdrawalAmount));
        remainingAmountLabel.setText("VND " + format.format(remainingAmount));
        toggleButtonsVisibility("showWithdrawAll");   //hide other withdrawal button when user want to withdraw all
        trollImg.setVisible(false);
      });
  }
  @FXML private void toggleButtonsVisibility(String showWithdrawAll_showOthers ){
    //this button hide all other withdrawal options when user want to withdrawAll his money
    if ("showWithdrawAll".equals(showWithdrawAll_showOthers )) {
      for(Button i : buttonList) {i.setDisable(true);}
    }else if("showOthers".equals(showWithdrawAll_showOthers )){
    for(Button i : buttonList) {i.setDisable(false);}
    }
  }
  @FXML private void reloadWithdrawalAmount(int amount) {
    if (amount <= remainingAmount){
      withdrawalAmount+=amount;
      remainingAmount = balanceAmount-withdrawalAmount;
      withdrawalAmountLabel.setText("VND " + format.format(withdrawalAmount));
      remainingAmountLabel.setText("VND " + format.format(remainingAmount));
      trollImg.setVisible(false);
    }else{
      trollImg.setVisible(true);
      ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();
    }
    
  }
  @FXML private void confirmWithdrawButtonPressed() throws IOException{
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    if(withdrawalAmount<=balanceAmount && withdrawalAmount > 0){
      toQuestion();
      currentAccount.withdraw(withdrawalAmount);    
      Account.saveUserData();
      loadUserInfo();
      toggleButtonsVisibility("showOthers");  
    }
  }
  @FXML private void cancelWithdrawButtonPressed(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    loadUserInfo();
    toggleButtonsVisibility("showOthers");
    trollImg.setVisible(false);
  }
  
  @Override public void initialize(URL url, ResourceBundle rb) {
    loadUserInfo();
    buttonList = new Button [] {withdraw1kButton, withdraw50kButton, withdraw200kButton, withdraw500kButton, withdraw1000kButton, withdraw2000kButton, withdraw10000kButton};
    assignButtonEvents();
    assignKeyboard();
  }
}

