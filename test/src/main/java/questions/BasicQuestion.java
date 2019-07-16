package questions;

import arduino.communication.ArduinoBasicCommunicator;
import ui.JMultilineLabel;
import ui.Speaker;
import user.interaction.Status;

import java.awt.*;

public class BasicQuestion extends Question{

    protected Status answer;
    public BasicQuestion(ArduinoBasicCommunicator bc, String question, Status anwser) {
        super(bc, question);
        this.answer = anwser;
    }


    @Override
    public boolean evaluate() {
        String r;

        while ((r = bc.receive()) == null || r.equals("")) {
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


    @Override
    public void presentQuestion(JMultilineLabel mainLabel) {

        mainLabel.setText(this.getQuestion());
        this.readQuestion();


        String toBeSaid;
        boolean evaluation = this.evaluate();
        speaker.say("You say it is " + ((answerStatus.getNumero() == 0) ? "true" : "false")+". And I say... ");

        if(evaluation) {
            toBeSaid = "Well Done!";
            mainLabel.setForeground(Color.GREEN);
        } else {
            toBeSaid = "Good luck next time";
            mainLabel.setForeground(Color.RED);
        }
        mainLabel.setText(toBeSaid);
        Question.getSpeaker().say(toBeSaid);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
