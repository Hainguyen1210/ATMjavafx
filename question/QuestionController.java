package ATMjavafx.question;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * randomly choose type of question
 * randomly generate question
 */
public class QuestionController implements Initializable {
  boolean isTrueFalseQuestion = false, userChoiceTF;
  private int userChoiceMC;
  
  TrueFalseQuestion currentTFQuestion;
  multipleChoiceQuestion currentMCQuestion;
  Button buttonList [];
  @FXML Stage window;
  @FXML Label questionTiltleLabel;
  @FXML Button 
          triggerQuestionButton,
          trueButton, falseButton,
          A, B, C, D;
  
  @FXML private void toCoinOrCash() {
    // switch to another scene to ask whether user like to receive cash or coin
        System.out.println("to Coin or Cash");
    
    try {
      Parent root = FXMLLoader.load(ATMjavafx.coinOrCash.CoinOrCashController.class.getResource("coinOrCash.fxml"));
      window.setScene(new Scene(root));
    } catch (Exception e) {
      System.err.println("can not load the coinOrCash.fxml");
    }
    window.show(); 
  }
  
  @FXML private void triggerQuestion(){
    ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
    this.window = (Stage) questionTiltleLabel.getScene().getWindow();
    
    triggerQuestionButton.setVisible(false);
    if(Math.random() < 0.5) {loadTrueFalseQuestion(); isTrueFalseQuestion = true;}
    else{loadMultipleChoiceQuestion();}
  }
  @FXML private void toggleButtons(String questionType){
    //this function toggle the buttons depends on type of question. whether TrueFalse or MultipleChoice
    boolean type = "TF".equals(questionType);
    A.setVisible(!type);
    B.setVisible(!type);
    C.setVisible(!type);
    D.setVisible(!type);
    trueButton.setVisible(type);
    falseButton.setVisible(type);
  }
  @FXML private void loadTrueFalseQuestion(){
    toggleButtons("TF");
    
    int index = new Random().nextInt(TrueFalseQuestion.TrueFalseQList.size());
    currentTFQuestion = TrueFalseQuestion.TrueFalseQList.get(index);
    String questionsTitle = currentTFQuestion.questionTitle;
    Boolean answer = TrueFalseQuestion.TrueFalseQList.get(index).isTrue;
    questionTiltleLabel.setText(questionsTitle);
    
  }
  @FXML private void loadMultipleChoiceQuestion(){
    toggleButtons("notTF");
    //load title
    int index = new Random().nextInt(multipleChoiceQuestion.multipleQList.size());
    currentMCQuestion = multipleChoiceQuestion.multipleQList.get(index);
    String questionsTitle = currentMCQuestion.questionTitle;
    questionTiltleLabel.setText(questionsTitle);
    //load options
    A.setText("A. " + currentMCQuestion.A);
    B.setText("B. " + currentMCQuestion.B);
    C.setText("C. " + currentMCQuestion.C);
    D.setText("D. " + currentMCQuestion.D);
  }
  @FXML private void checkAnswer(){
    // check whether user choice is true then move to the next scene, if not , another question will be loaded.
    if (isTrueFalseQuestion) {
      if(userChoiceTF == currentTFQuestion.isTrue) {     ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();   toCoinOrCash();     } 
      else {        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();   triggerQuestion();      }
    } else {
      if(userChoiceMC == currentMCQuestion.answer) { ATMjavafx.Sound.correct.stop(); ATMjavafx.Sound.correct.play();   toCoinOrCash();      } 
      else {        ATMjavafx.Sound.error.stop(); ATMjavafx.Sound.error.play();   triggerQuestion();      }
    }
  }
  @FXML private void assignButton(){
//    for (Button buttonList1 : buttonList) {
//      buttonList1.setOnAction((ActionEvent) -> {
//        ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();
//        checkAnswer();
//      });
//    }
    
      A.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceMC = 0;checkAnswer();});
      B.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceMC = 1;checkAnswer();});
      C.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceMC = 2;checkAnswer();});
      D.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceMC = 3;checkAnswer();});
      D.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceMC = 3;checkAnswer();});
      trueButton.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceTF = true;checkAnswer();});
      falseButton.setOnAction((ActionEvent) -> {ATMjavafx.Sound.button.stop(); ATMjavafx.Sound.button.play();userChoiceTF = false;checkAnswer();});
      
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {    
    //load questions
    System.out.print("Loading questions ");
    TrueFalseQuestion.loadData();
    multipleChoiceQuestion.loadData();
    System.out.println("finished");
    
    buttonList = new Button [] {A, B, C, D, trueButton, falseButton};
    assignButton();
  }  
  
}
