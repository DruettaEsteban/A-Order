package UI;

import IO.QuestionsFactory;
import javafx.application.Platform;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller {
    public Controller(){
        super();
    }

    public void control(FrameMP frameMP){

        QuestionsFactory questionsFactory = QuestionsFactory.newInstance();

        LinkedList<String> options = new LinkedList<>();
        String A = "SOY LA RESPUESTA A";
        String B = "SOY LA RESPUESTA B";
        String C = "SOY LA RESPUESTA C";
        String D = "SOY LA RESPUESTA D Y CORRECTA";

        Collections.addAll(options, A,B,C,D);

        Question question = new Question(options, Answers.D, "Soy la pregunta");

        frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion());

        //frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion());


        //frameMP.evaluateAndDisplay(Answers.C);

        //frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion());




    }

}
