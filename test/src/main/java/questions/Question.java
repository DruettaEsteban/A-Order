package questions;
import arduino.communication.Communicator;
import ui.Speaker;
import user.interaction.Status;

public abstract class Question implements AnwserChecker{

    protected Communicator bc;
    protected String question;
    protected Status answerStatus;
    protected Speaker speaker;

    public Question(Communicator bc, String question, Speaker speaker){
        this.question = question;

        this.speaker = speaker;
        this.bc = bc;
        answerStatus = Status.STANDBY;
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

    public void readQuestion(){
        speaker.say(this.question);
    }

}
