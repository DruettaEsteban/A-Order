import arduino.communication.ArduinoBasicCommunicator;
import questions.BasicQuestion;
import ui.MainFrame;
import ui.Speaker;
import user.interaction.Status;

import javax.naming.NamingException;
import java.io.IOException;



public class Main {
    public static boolean RESET = false;

    public static void main(String[] args) throws NamingException, IOException {
        MainFrame frame = MainFrame.getInstance();
        ArduinoBasicCommunicator arduinoAdvancedCommunicator = new ArduinoBasicCommunicator("COM3");
        BasicQuestion question = new BasicQuestion(arduinoAdvancedCommunicator, "2 plus 2 equals 4", Status.VERDADERO, new Speaker());


        while (true) {
            Speaker speaker = new Speaker();
            speaker.say(question.evaluate() ? "YOU ARE RIGHT" : "YOU ARE WRONG");
            speaker.disallocate();

        }
    }


}