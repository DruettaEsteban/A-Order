package IO;

import UI.Answers;

public class ResponseStatus {

    private boolean validState;

    private final Answers answer;


    public ResponseStatus(boolean validState, Answers answers){
        this.validState = validState;
        this.answer = answers;
    }



    public boolean isValidState() {
        return validState;
    }

    public Answers getAnswer() {
        return answer;
    }

    public ResponseStatus copyAndInvalidate(){
        ResponseStatus copy = new ResponseStatus(validState, answer);
        this.validState = false;

        return copy;
    }

}