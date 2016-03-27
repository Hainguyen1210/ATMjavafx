package ATMjavafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
form new account
Load from text file and Create new account object, add them to array list
Save all object from list and save them to text file
 */
public class Account {
  //this array will contain Account objects 
  public static ArrayList<Account> accountList = new ArrayList<>();
  
  static File inputFile = new File("userData.txt");
  public String userName;
  private String userPassword, userRealName, userGender;
  private long userBalance;
  private int userAge;
  
  public Account(){}
  public Account(String userName,String userPassword,long userBalance,
          String userRealName,int userAge,String userGender) 
  {
    this.userName = userName;
    this.userPassword = userPassword;
    this.userBalance = userBalance;
    this.userRealName = userRealName;
    this.userAge = userAge;
    this.userGender = userGender;
  }
  
  
  public void deposit(long moneyToDeposit){
    this.userBalance += moneyToDeposit;}
  public void withdraw(long moneyToWithdraw) {
    this.userBalance -= moneyToWithdraw;}
  public String getUserPassword(){return this.userPassword;}
  public long getUserBalance(){return this.userBalance;}
  public String getUserRealName(){return this.userRealName;}
  public int getUserAge(){return this.userAge;}
  public String getUserGender(){return this.userGender;}
  
  public void changeUserInfo(String newUserName, String newPassword, String newUserRealName,String newGender, int newAge){
    this.userName = newUserName;
    this.userPassword = newPassword;
    this.userRealName = newUserRealName;
    this.userGender = newGender;
    this.userAge = newAge;
  }
  public static void printCurrentUser(){
    System.out.println("Current accounts:");
    for (Account currentAccount : accountList) {
      System.out.println("  "+currentAccount.userName);
    }
  }
  public static void loadUserData(){
    /*
    this method loads the user data from a text file, each line will be recorded as an attributes of an object
    after certain number of line (7 lines), one user is created and added to accountList(ArrayList)
    the loop continues untill it reaches the last blank line
    */
    String currentUserName, currentUserPassword, currentUserRealName, currentUserGender;
    int currentUserAge;
    long currentUserBalance;
    
    try {
      Scanner input = new Scanner(inputFile);
      while (input.hasNextLine()) {
        currentUserName = input.next();
        currentUserPassword = input.next();
        currentUserBalance = Long.parseLong(input.next()) ;
        currentUserRealName = input.next().replace("_", " ");
        currentUserAge = Integer.parseInt(input.next());
        currentUserGender = input.next();
        String userEnd = input.next();

        // creat users
        if (currentUserName.length() > 1){  // in case it reaches the last blank line
          try {
            accountList.add(new Account(currentUserName, currentUserPassword,
                    currentUserBalance,currentUserRealName, currentUserAge, currentUserGender));          
          } catch (Exception e) { System.err.println("Can not create accounts");}
        }
      }
    } catch (FileNotFoundException | NumberFormatException e) {}
  }
  public static void saveUserData(){
    /*
    this method wil get all objects from array and save it to the same text file as it loads 
    by overwriting the current data in this text file.
    it writes these data in the same order as it loads(each account has 7 lines)
    */
    try {
      FileWriter writer = new FileWriter(inputFile, false);
      for(Account checkingAccount : accountList) {
        writer.write(checkingAccount.userName +System.lineSeparator());
        writer.write(checkingAccount.userPassword + System.lineSeparator());
        writer.write(checkingAccount.userBalance + System.lineSeparator());
        writer.write(checkingAccount.userRealName.replace(" ", "_") + System.lineSeparator());
        writer.write(checkingAccount.userAge + System.lineSeparator());
        writer.write(checkingAccount.userGender + System.lineSeparator());
        writer.write("------------------" + System.lineSeparator());
      }
        writer.close();
    } catch (Exception e) {
      System.err.println("can not save the file.");
    }
  }
}
