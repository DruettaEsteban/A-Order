package UI;

import IO.ArduinoCommunication;
import IO.AsyncAudioPlayer;
import IO.QuestionsFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



class Controller {
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

    private static void letTheGameBegin(FrameMP frameMP, QuestionsFactory questionsFactory, ArduinoCommunication communication){

        frameMP.changeQuestionAndOptions(questionsFactory.getRandomQuestion());

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        executor.schedule(frameMP::fillBar, 1000, TimeUnit.MILLISECONDS);
        executor.schedule(() -> {


            Answers answers = null;
            frameMP.startCountdown();
            boolean timeOver = false;
            while (answers == null){
                answers = communication.readPort();
                if (frameMP.isTimeOver()){
                    timeOver = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            if(!timeOver) {
                System.out.println(answers);
                frameMP.stopCounter();
                boolean isCorrect = frameMP.isCorrect(answers);
                frameMP.evaluateAndDisplay(answers, ()-> asyncAudioPlayer.playRandomAudio(isCorrect));
                executor.schedule(() -> {
                    Controller.letTheGameBegin(frameMP, questionsFactory, communication);
                    communication.clearPort();

                },20000, TimeUnit.MILLISECONDS);
            } else {
                Controller.letTheGameBegin(frameMP, questionsFactory, communication);
            }



        },2500, TimeUnit.MILLISECONDS);


    }

}
