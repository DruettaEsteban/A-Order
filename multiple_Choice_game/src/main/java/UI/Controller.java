package UI;

import IO.ArduinoCommunication;
import IO.AsyncAudioPlayer;
import IO.QuestionsFactory;
import javafx.application.Platform;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class Controller {
    private final String rootDirectory = "C:\\Users\\Usuario\\Desktop\\game";
    private final String XML_FILE_NAME = "\\test.xml";
    private final String TRUE_DIR_NAME = "\\trueAudios";
    private final String FALSE_DIR_NAME = "\\falseAudios";

    private static AsyncAudioPlayer asyncAudioPlayer;


    public Controller(){
        super();
    }

    public void control(FrameMP frameMP){
        QuestionsFactory questionsFactory = QuestionsFactory.newInstance(rootDirectory + XML_FILE_NAME);
        ArduinoCommunication communication = new ArduinoCommunication("COM3");
        asyncAudioPlayer = new AsyncAudioPlayer(rootDirectory + TRUE_DIR_NAME, rootDirectory + FALSE_DIR_NAME);
        letTheGameBegin(frameMP, questionsFactory, communication);

    }

    public static void letTheGameBegin(FrameMP frameMP, QuestionsFactory questionsFactory, ArduinoCommunication communication){

        Thread gameCycle = new Thread(() -> {

            Platform.runLater(() -> frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion()));

            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

            executor.schedule(() -> Platform.runLater(()->{

                Answers answers = null;
                while (answers == null){
                    answers = communication.readPort();

                }
                System.out.println(answers);
                boolean isCorrect = frameMP.isCorrect(answers);
                frameMP.evaluateAndDisplay(answers, ()-> asyncAudioPlayer.playRandomAudio(isCorrect));
                executor.schedule(() -> {
                    Controller.letTheGameBegin(frameMP, questionsFactory, communication);
                    communication.clearPort();
                },20000, TimeUnit.MILLISECONDS);

            }),5000, TimeUnit.MILLISECONDS);

        });
        gameCycle.start();
    }

}
