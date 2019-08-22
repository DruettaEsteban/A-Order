package UI;

import IO.ArduinoCommunication;
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
        ArduinoCommunication communication = new ArduinoCommunication("COM3");
        letTheGameBegin(frameMP, questionsFactory, communication);



        //RESOLVER PROBLEMA DE RESPUESTA CORRECTA
    }

    public static void letTheGameBegin(FrameMP frameMP, QuestionsFactory questionsFactory, ArduinoCommunication communication){
        Thread gameCycle = new Thread(() -> {

            frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion());

            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

            executor.schedule(() -> {
                Platform.runLater(()->{

                    Answers answers = null;
                    while (answers == null){
                        answers = communication.readPort();
                        //System.out.println("here");
                    }
                    System.out.println(answers);
                    frameMP.evaluateAndDisplay(answers);
                    executor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Controller.letTheGameBegin(frameMP, questionsFactory, communication);
                        }
                    },20000, TimeUnit.MILLISECONDS);

                });
            },5000, TimeUnit.MILLISECONDS);

        });
        gameCycle.start();
    }

}
