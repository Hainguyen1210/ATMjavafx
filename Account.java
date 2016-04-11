package ATMjavafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
form new account
Load from text file and Create new account object, add them to array list
Save all object from list and save them to text file
 */
public class Account {
  //this array will contain Account objects 
  public static ArrayList<Account> accountList = new ArrayList<>();
  
  static File accountsFile = new File("userData.txt"), 
          statisticFile = new File("statistic.txt");
  public String userName;
  private String userPassword, userRealName, userGender;
  private long userBalance;
  private int userAge;
  private long 
          withdrawalTotal, withdrawalTimes, withdrawalAverage,  withdrawalMax, withdrawalMin,
          depositTotal, depositTimes, depositAverage, depositMax, depositMin ;
  
  public Account(){}
  public Account(String userName,String userPassword,long userBalance, String userRealName,int userAge,String userGender,
          long withdrawalTotal, long withdrawalTimes, long withdrawalAverage, long withdrawalMax, long withdrawalMin,
          long depositTotal, long depositTimes, long depositAverage, long depositMax, long depositMin) 
  {
    this.userName = userName;
    this.userPassword = userPassword;
    this.userBalance = userBalance;
    this.userRealName = userRealName;
    this.userAge = userAge;
    this.userGender = userGender;
    
    this.depositTotal = depositTotal;
    this.depositTimes = depositTimes;
    this.depositAverage = depositAverage;
    this.depositMax = depositMax;
    this.depositMin = depositMin;
    
    this.withdrawalTotal = withdrawalTotal;
    this.withdrawalTimes = withdrawalTimes;
    this.withdrawalAverage = withdrawalAverage;
    this.withdrawalMax = withdrawalMax;
    this.withdrawalMin = withdrawalMin;
  }
  public void changeUserInfo(String newUserName, String newPassword, String newUserRealName,String newGender, int newAge){
    this.userName = newUserName;
    this.userPassword = newPassword;
    this.userRealName = newUserRealName;
    this.userGender = newGender;
    this.userAge = newAge;
  }
  public void deleteThisUser(){accountList.remove(this);}
  
  public void deposit(long moneyToDeposit){
    this.userBalance += moneyToDeposit;
    updateStatisticsDeposit(moneyToDeposit);
  }
  public void withdraw(long moneyToWithdraw) {
    this.userBalance -= moneyToWithdraw;
    updateStatisticsWithdraw(moneyToWithdraw);
  }
  public String getUserPassword(){return this.userPassword;}
  public String getUserRealName(){return this.userRealName;}
  public String getUserGender(){return this.userGender;}
  public int getUserAge(){return this.userAge;}
  public long getUserBalance(){return this.userBalance;}
  

  
  public static void loadUserData(){
    /*
    this method loads the user data from a text file, each line will be recorded as an attributes of an object
    after certain number of line (9 lines), one user is created and added to accountList(ArrayList)
    the loop continues untill it reaches the last blank line
    */
    String currentUserName, currentUserPassword, currentUserRealName, currentUserGender, 
            withdrawString, depositString;
    int currentUserAge;
    long currentUserBalance;
    
    try {
      Scanner input = new Scanner(accountsFile);
      while (input.hasNextLine()) {
        currentUserName = input.next();
        currentUserPassword = input.next();
        currentUserBalance = Long.parseLong(input.next()) ;
        currentUserRealName = input.next().replace("_", " ");
        currentUserAge = Integer.parseInt(input.next());
        currentUserGender = input.next();
        depositString = input.next();
        withdrawString = input.next();
        String userEnd = input.next();
        
        String[] depositStringParts = depositString.split("-");  
        String[] withdrawStringParts = withdrawString.split("-");
        long[][] depositAndWithdraw = new long[2][5];
        
        for (int i = 0; i < 2; i++) {
          for (int j = 0; j < 5; j++) {
            depositAndWithdraw[0][j] = Long.parseLong(depositStringParts[j]);
            depositAndWithdraw[1][j] = Long.parseLong(withdrawStringParts[j]);
          }
        }
        
        // creat users
        if (currentUserName.length() > 1){  // in case it reaches the last blank line
          try {
            accountList.add(new Account(currentUserName, currentUserPassword,
                    currentUserBalance,currentUserRealName, currentUserAge, currentUserGender, 
                    depositAndWithdraw[0][0], depositAndWithdraw[0][1], depositAndWithdraw[0][4], depositAndWithdraw[0][2], depositAndWithdraw[0][3],  
                    depositAndWithdraw[1][0], depositAndWithdraw[1][1], depositAndWithdraw[1][4], depositAndWithdraw[1][2], depositAndWithdraw[1][3]
            ));          
          } catch (Exception e) { System.err.println("Can not create accounts");}
        }
      }
    } catch (FileNotFoundException | NumberFormatException e) {}
  }
  public static void saveUserData(){
    /*
    this method wil get all objects from array and save it to the same text file as it loads 
    by overwriting the current data in this text file.
    it writes these data in the same order as it loads(each account has 9 lines)
    */
    try {
      FileWriter writer = new FileWriter(accountsFile, false);
      for(Account checkingAccount : accountList) {
        writer.write(checkingAccount.userName +System.lineSeparator());
        writer.write(checkingAccount.userPassword + System.lineSeparator());
        writer.write(checkingAccount.userBalance + System.lineSeparator());
        writer.write(checkingAccount.userRealName.replace(" ", "_") + System.lineSeparator());
        writer.write(checkingAccount.userAge + System.lineSeparator());
        writer.write(checkingAccount.userGender + System.lineSeparator());
        
        writer.write(
                checkingAccount.depositTotal+"-"+
                checkingAccount.depositTimes+"-"+
                checkingAccount.depositMax+"-"+
                checkingAccount.depositMin+"-"+
                checkingAccount.depositAverage 
                +System.lineSeparator()
        );
        writer.write(
                checkingAccount.withdrawalTotal+"-"+
                checkingAccount.withdrawalTimes+"-"+
                checkingAccount.withdrawalMax+"-"+
                checkingAccount.withdrawalMin+"-"+
                checkingAccount.withdrawalAverage 
                +System.lineSeparator()
        );
        
        writer.write("------------------" + System.lineSeparator());
      }
        writer.close();
    } catch (Exception e) {
      System.err.println("can not save the file.");
    }
  }
  public static void printCurrentUser(){
    System.out.println("Current accounts:");
    for (Account currentAccount : accountList) {
      System.out.println("  "+currentAccount.userName);
    }
  }
  
  //statistic functions
  public void updateStatisticsDeposit(long amount){
    this.depositTotal += amount;
    this.depositTimes++;
    this.depositAverage = depositTotal / depositTimes;
    if(amount > this.depositMax){depositMax = amount;}
    if(amount < this.depositMin){depositMin = amount;}
  }
  public void updateStatisticsWithdraw(long amount){
    this.withdrawalTotal += amount;
    this.withdrawalTimes++;
    this.withdrawalAverage = withdrawalTotal / withdrawalTimes;
    if(amount > this.withdrawalMax){withdrawalMax = amount;}
    if(amount < this.withdrawalMin){withdrawalMin = amount;}
  }
}
