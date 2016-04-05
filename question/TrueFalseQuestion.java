/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATMjavafx.question;

import ATMjavafx.ATMSceneBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hainguyen
 */
public class TrueFalseQuestion extends Question{
  public static ArrayList<Question> TrueFalseQList = new ArrayList<>();
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
      File data = new File(ATMjavafx.question.Question.class.getResource("TrueFalse.txt").toExternalForm());
      Scanner input = new Scanner(data);
    System.out.println(data);
    System.out.println(question);
      while(input.hasNextLine()) {
        question = input.next();
        isTrueString = input.next();
        
        System.out.println(question);
        System.out.println(isTrueString);
        System.out.println(isTrueBoolean);
        
        if (isTrueString.equals("true")) {    isTrueBoolean = true;   }
        TrueFalseQList.add(new TrueFalseQuestion(question, isTrueBoolean));
      }
    } catch (Exception e) {
    }
  }
}
