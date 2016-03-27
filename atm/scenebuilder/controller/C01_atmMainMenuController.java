package atm.scenebuilder.controller;

import atm.scenebuilder.Account;
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
import javafx.stage.Stage;

/*
this controller currently allows users to view their information, deposit money
***this controller will have more functions, such as: edit user's information, withdraw money, 
*/
public class C01_atmMainMenuController implements Initializable {
   // set value
  Account currentAccount = atm.scenebuilder.controller.A01_loginInputUserNameController.currentAccount;
  long balanceAmount, withdrawalAmount,  remainingAmount;
  DecimalFormat format = new DecimalFormat(); // this object formats money's display
  Button[] buttonList;
  int[] amountOfMoneyList = {1000, 50000,200000,  500000, 1000000, 2000000, 10000000};
  
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
  
  @FXML private void backToLogin()throws IOException{
    System.out.println("back to Login & Register");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/A01_loginInputUserName.fxml"));
    Stage window=(Stage) quitButton.getScene().getWindow(); // this code is to get the main window
    window.setScene(new Scene(root));
    window.show();
  }
  @FXML private void editUserInfo()throws IOException{
    System.out.println("saving user's info");
    Parent root = FXMLLoader.load(atm.scenebuilder.ATMSceneBuilder.class.getResource("fxml/editUserInfo.fxml"));
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
    depositButton.setVisible(false);
    confirmDepositButton.setVisible(true);
    depositField.setVisible(true);
    depositField.requestFocus();
  }
  @FXML private void confirmDepositButtonPressed(){
    /* this method checks whether the user's input is valid or not
    if it is valid, these number will be added to the user.balance and then save it to the text file right away
    */
    if (isNumber(depositField.getText())&&depositField.getText().length()>0){
      currentAccount.deposit(Long.parseLong(depositField.getText()));
      checkDepositLabel.setVisible(false);
    }else{
      checkDepositLabel.setVisible(true);
    }
    depositField.clear();
    depositField.requestFocus();
    loadUserInfo();
    // save to txt
    Account.saveUserData();
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
    }
    
  }
  @FXML private void assignButtonEvents(){
    // assign event to all the withdraw buttons
    for (int i=0; i < buttonList.length; i++) {
      int amountOfMoney = amountOfMoneyList[i];
      buttonList[i].setOnAction((ActionEvent e ) -> {
        reloadWithdrawalAmount(amountOfMoney);
        
      });
    }
    //especially for withdraw all the money button
    withdrawAllButton.setOnAction((ActionEvent e ) -> {
        withdrawalAmount = currentAccount.getUserBalance();
        remainingAmount = 0;
        withdrawalAmountLabel.setText("VND " + format.format(withdrawalAmount));
        remainingAmountLabel.setText("VND " + format.format(remainingAmount));
        toggleButtonsVisibility("showWithdrawAll");   //hide other withdrawal button when user want to withdraw all
        trollImg.setVisible(false);
      });
  }
  @FXML private void confirmWithdrawButtonPressed(){
    if(withdrawalAmount<=balanceAmount){
      currentAccount.withdraw(withdrawalAmount);
      loadUserInfo();
      Account.saveUserData();
      toggleButtonsVisibility("showOthers");
    }
    
  }
  @FXML private void cancelWithdrawButtonPressed(){
    loadUserInfo();
    toggleButtonsVisibility("showOthers");
    trollImg.setVisible(false);
  }
  @FXML private void confirmOrCancelDeposit(){
    depositField.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){confirmDepositButtonPressed();}
      else if(keyInput.getCode().equals(KeyCode.ESCAPE)){depositField.clear();}});
    
    depositButton.setOnKeyReleased((KeyEvent keyInput) -> {
      if(keyInput.getCode().equals(KeyCode.ENTER)){depositButtonPressed();}});
  }
  @FXML private void toggleButtonsVisibility(String showWithdrawAll_showOthers ){
    //this button hide all other withdrawal options when user want to withdrawAll his money
    if ("showWithdrawAll".equals(showWithdrawAll_showOthers )) {
      for(Button i : buttonList) {i.setDisable(true);}
    }else if("showOthers".equals(showWithdrawAll_showOthers )){
    for(Button i : buttonList) {i.setDisable(false);}
    }
  }
  
  public boolean isNumber(String input){
    /*check whether input is a number or not*/
    try {
      Long.parseLong(input);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
  @Override public void initialize(URL url, ResourceBundle rb) {
    loadUserInfo();
    buttonList = new Button [] {withdraw1kButton, withdraw50kButton, withdraw200kButton, withdraw500kButton, withdraw1000kButton, withdraw2000kButton, withdraw10000kButton};
    assignButtonEvents();
    confirmOrCancelDeposit();
  }
  
 
}

