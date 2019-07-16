package questions;
import arduino.communication.Communicator;
import ui.JMultilineLabel;
import ui.Speaker;
import user.interaction.Status;

public abstract class Question implements AnwserChecker{

    protected Communicator bc;
    protected String question;
    protected Status answerStatus;
    protected static Speaker speaker;

    public String getQuestion(){
        return this.question;
    }


    public Question(Communicator bc, String question){
        this.question = question;

        this.bc = bc;
        answerStatus = Status.STANDBY;
    }

    public static Speaker getSpeaker(){
        return speaker;
    }

    public static void setSpeaker(Speaker speaker){
        Question.speaker = speaker;
    }

    public static void disallocateSpeaker(){
        speaker.disallocate();
    }

    public final Status getAnswerStatus() {
        return answerStatus;
    }

    protected boolean evaluateReset(){
        if (this.answerStatus.getNumero() == Status.REINICIAR.getNumero()){
            //Reset Thread

            bc.closeCommunicator();
            return true;
        }
        return false;
    }

    public abstract void presentQuestion(JMultilineLabel label);

    public void readQuestion(){
        speaker.say(this.question);
    }

}
