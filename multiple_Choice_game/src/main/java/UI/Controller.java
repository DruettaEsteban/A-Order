package UI;

import Graphs.MainGraph;
import IO.ArduinoCommunication;
import IO.AsyncAudioPlayer;
import IO.QuestionsFactory;
import stadistics.QuestionRectifiedRandomizer;
import stadistics.StatisticQuestion;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class Controller {
    private static final String rootDirectory = "C:\\Users\\Usuario\\Desktop\\game";
    private static final String XML_FILE_NAME = "\\test.xml";
    private static final String TRUE_DIR_NAME = "\\trueAudios";
    private static final String FALSE_DIR_NAME = "\\falseAudios";
    public static final String STATISTICS_FILE_DIR = rootDirectory + "\\statistics";
    public static final int UPDATE_GRAPH_TIME_MILLIS = 15000;
    private static AsyncAudioPlayer asyncAudioPlayer;
    private static final boolean PLAY_GRAPHS = true;
    private static volatile QuestionRectifiedRandomizer<StatisticQuestion> rectifiedRandomizer;



    public Controller(){
        super();
    }

    public void control(FrameMP frameMP){

        QuestionsFactory questionsFactory = QuestionsFactory.newInstance(rootDirectory + XML_FILE_NAME);
        ArduinoCommunication communication = new ArduinoCommunication("COM3");
        asyncAudioPlayer = new AsyncAudioPlayer(rootDirectory + TRUE_DIR_NAME, rootDirectory + FALSE_DIR_NAME);


        if (PLAY_GRAPHS) {
            rectifiedRandomizer = questionsFactory.turnToStatisticCollection();
            MainGraph mainGraph = new MainGraph();
            displayStatistics(mainGraph);

            Thread graphThread = new Thread(() -> {

                while (true){
                    displayStatistics(mainGraph);
                }

            });
            graphThread.start();
        }



        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> letTheGameBegin(frameMP, questionsFactory, communication), 30, TimeUnit.SECONDS);



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

                Answers finalAnswers = answers;

                frameMP.getCurrentQuestion().updateProperty(finalAnswers);
                rectifiedRandomizer.updateQuestion(frameMP.getCurrentQuestion());



                executor.schedule(() -> {
                    Controller.letTheGameBegin(frameMP, questionsFactory, communication);
                    communication.clearPort();

                },20000, TimeUnit.MILLISECONDS);
            } else {
                Controller.letTheGameBegin(frameMP, questionsFactory, communication);
            }



        },2500, TimeUnit.MILLISECONDS);
    }



    private static void displayStatistics(MainGraph mainGraph) {

            try {
                mainGraph.updateGraph((StatisticQuestion) rectifiedRandomizer.getRandomQuestion());
                Thread.sleep(UPDATE_GRAPH_TIME_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


    }

}