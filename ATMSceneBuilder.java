/* 
Title: A  becoming-Crazy ATM
Description: 
  This is a simple but not ordinary ATM, it will not usually meet user's needs correctly :)
  During the program's process, I also print a lot to the console to check the program working
  (in the console, there are program's status, user names and passwords, ...)
Author: Hai Nguyen
Version: v6
Last updated: 12:37 PM,  April 12, 2016
List of features:
		○ Manage bank accounts by read & write text file (user's infomation and statistics)
		○ Create new accounts and save it to the same text file
    ○ Delete account after entering wrong password 3 times 
		○ Display & Edit user's information
		○ Deposit & Withdraw money
    ○ Crazy Sound effect
    ○ "Security" questions
    ○ Coin or Cash withdraw
    ○ Open Browser with url
*/

package ATMjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

  /*
  this class will 
  1. load user data from text file, create account objects and put them to an ArrayList
  2. Enable an annoying background sound
  3. Load the first Scene
  */
public class ATMSceneBuilder extends Application {
  public static void main(String[] args) {launch(args);}
  @Override public void start(Stage window) throws Exception { 
    
    //load user data
    System.out.print("Loading user data ");
    try {Account.loadUserData();}catch (Exception e) {}
    System.out.println("finished.");
    
    //Enable background sound
    ATMjavafx.Sound.playBackgoundSound();
    
    //go to the Login and Register options
    window.setTitle("Hainguyen's ATM");
    Parent root = FXMLLoader.load(getClass().getResource("fxml/A00_loginAndRegister.fxml"));
    Scene scene1 = new Scene(root);
    scene1.getStylesheets().add // CSS
      (ATMSceneBuilder.class.getResource("MainStyle.css").toExternalForm());

    window.setScene(scene1);
    window.show();
  }
}
