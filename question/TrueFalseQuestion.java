package ATMjavafx.question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * load TrueFalse questions from text file, create new object and put them to an ArrayList
 */
public class TrueFalseQuestion extends Question{
  public static ArrayList<TrueFalseQuestion> TrueFalseQList = new ArrayList<>();
  public boolean isTrue; 
  
  public TrueFalseQuestion(String title, boolean isTrue) {
    super(title);
    this.isTrue = isTrue;
  }
  public static void loadData(){
    String question = "";
    String isTrueString;
    boolean isTrueBoolean = false;
    
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(Question.class.getResourceAsStream("TrueFalse.txt")));
      while(true) {
        question = input.readLine();  if (question == null) {break;}
        isTrueString = input.readLine();
        isTrueBoolean = isTrueString.equals("true");
        
        TrueFalseQList.add(new TrueFalseQuestion(question, isTrueBoolean));
      }
    } catch (Exception e) {
      System.err.println("cannot load the file  TrueFalse.txt successfully.");
    }
  }
}
