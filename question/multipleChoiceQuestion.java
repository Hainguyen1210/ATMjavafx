package ATMjavafx.question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Hainguyen
 */
public class multipleChoiceQuestion extends Question{
  public static ArrayList<multipleChoiceQuestion> multipleQList = new ArrayList<>();
  public String A, B, C, D;
  public int answer;
  public multipleChoiceQuestion(String title, String A, String B, String C, String D, int answer) {
    super(title);
    this.A  = A;
    this.B  = B;
    this.C  = C;
    this.D  = D;
    this.answer = answer;
  }
  public static void loadData(){
    String title, A, B, C, D;
    int answer;
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(Question.class.getResourceAsStream("multipleChoice.txt")));
      while (true) {        
        title = input.readLine(); if (title == null) { break; }
        A = input.readLine();
        B = input.readLine();
        C = input.readLine();
        D = input.readLine();
        answer = Integer.parseInt(input.readLine());
        
        multipleQList.add( new multipleChoiceQuestion(title, A, B, C, D, answer));
      }
    } catch (IOException | NumberFormatException e) {
      System.err.println("can not load the file multipleChoice.txt successfully.");
    }
  }
}
