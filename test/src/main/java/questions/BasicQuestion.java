package questions;

import arduino.communication.ArduinoBasicCommunicator;
import ui.Speaker;
import user.interaction.Status;

public class BasicQuestion extends Question{

    protected Status answer;
    public BasicQuestion(ArduinoBasicCommunicator bc, String question, Status anwser, Speaker speaker) {
        super(bc, question, speaker);
        this.answer = anwser;
    }


    @Override
    public boolean evaluate() {
        String r = null;

        while (true){
            if ((r = bc.receive()) != null && !r.equals("")) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.answerStatus = statusCheck(r);
        super.evaluateReset(); //Interrupt Thread

        return super.answerStatus.equals(answer);
    }


}
