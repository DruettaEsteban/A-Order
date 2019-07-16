package questions;

import user.interaction.Status;

interface AnwserChecker {

    default Status statusCheck(char answer){
        switch (answer){
            case '0':
                return Status.VERDADERO;
            case '1':
                return Status.FALSO;
            case '2':
                return Status.REINICIAR;
            default:
                return Status.ERROR;
        }
    }

    default Status statusCheck(String answer){
        switch (answer.toCharArray()[0]){
            case '0':
                return Status.VERDADERO;
            case '1':
                return Status.FALSO;
            case '2':
                return Status.REINICIAR;
            default:
                return Status.ERROR;
        }
    }

    public boolean evaluate();

}
