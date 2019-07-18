import arduino.communication.ArduinoBasicCommunicator;
import javafx.scene.layout.StackPane;
import questions.BasicQuestion;
import questions.BasicVideoQuestion;
import questions.Question;
import questions.QuestionsQueue;
import ui.MainFrame;
import user.interaction.Status;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main {
    public static boolean RESET = false;

    public static void main(String[] args) {

        ArduinoBasicCommunicator communicator = new ArduinoBasicCommunicator("COM3", 9800);


        /*
        TO DO LIST;

        Multiple Choice
        Add videos - Interactive Sounds -> Question CLASS
        Automatize routine features

        AT THE END
        A whole new thread designed to RESET everything (Add to method evaluateReset() - Question CLASS)
        KISS BRENDA
         */
        LinkedHashSet<File> list = new LinkedHashSet<>();
        list.add(new File("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\A-Order\\GreenDetector\\classes\\artifacts\\GreenDetector_jar\\wqewq\\wqewq.mp4"));
        BasicVideoQuestion.TrueFalseVideoLocation location = new BasicVideoQuestion.TrueFalseVideoLocation(list, list);

        Question videoQuestion = new BasicVideoQuestion(communicator,"This is a testing question", Status.VERDADERO, location);
        Question onePlusOnE = new BasicQuestion(communicator, "One plus one equals 4", Status.FALSO);
        Question myNameIs = new BasicQuestion(communicator, "Your name is Esteban", Status.VERDADERO);

        QuestionsQueue<Question> questionsQueue = new QuestionsQueue<>();
        questionsQueue.addQuestions(videoQuestion, onePlusOnE, myNameIs);
        MainFrame frame = MainFrame.getInstance();

        frame.showQuestions(questionsQueue);

    }


}