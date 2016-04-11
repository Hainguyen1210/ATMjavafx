/* 
Title: A  becoming-Crazy ATM
Description: This is a simple but not ordinary ATM, it will not usually meet user's needs correctly :)
                    During the program's process, I also print a lot to the console to check the program working
                    (in the console, there are program's status, user names and passwords, ...)
Author: Hai Nguyen
Version: v4
Last updated: 09:30 AM,  April 6, 2016
List of features:
	- Current features:
		○ Loads user's data from text file and assign these data to new account objects
		○ Login with password-checking
		○ Create new accounts and save it to the same text file
		○ Display user's information
		○ Deposit money
		○ Withdraw money
        ○ Switching between scenes
		○ Edit user's info
        ○ Sound effect
        ○ Delete account after entering wrong password 3 times 
	- Upcoming features:
		○ Security questions with crazy answers
		○ Unusual behaviors detection
		○ Lucky games offer
        ○ Charity offer
*/

package ATMjavafx;

import ATMjavafx.question.TrueFalseQuestion;
import ATMjavafx.question.multipleChoiceQuestion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ATMSceneBuilder extends Application {
  
  public static void main(String[] args) {launch(args);}
  @Override public void start(Stage window) throws Exception { 
    
    //load user data
    System.out.print("Loading user data ");
    try {Account.loadUserData();}catch (Exception e) {}
    System.out.println("finished.");
    
    //go to the Login and Register options
    window.setTitle("Hainguyen's ATM");
    
    ATMjavafx.Sound.playBackgoundSound();
    
    Parent root = FXMLLoader.load(getClass().getResource("fxml/A00_loginAndRegister.fxml"));
    Scene scene1 = new Scene(root);
    scene1.getStylesheets().add // CSS
      (ATMSceneBuilder.class.getResource("MainStyle.css").toExternalForm());

    window.setScene(scene1);
    window.show();
  }
}
