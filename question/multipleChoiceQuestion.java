/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATMjavafx.question;

import java.util.ArrayList;

/**
 *
 * @author Hainguyen
 */
public class multipleChoiceQuestion extends Question{
  public static ArrayList<Question> multipleQList = new ArrayList<>();
  
  public multipleChoiceQuestion(String title) {
    super(title);
  }
  public void loadData(){
    
  }
}
