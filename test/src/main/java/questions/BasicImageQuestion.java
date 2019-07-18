package questions;

import arduino.communication.ArduinoBasicCommunicator;
import user.interaction.Status;

public class BasicImageQuestion extends BasicQuestion{
    public BasicImageQuestion(ArduinoBasicCommunicator bc, String question, Status anwser) {
        super(bc, question, anwser);
    }
}
