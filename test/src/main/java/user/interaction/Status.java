package user.interaction;

public enum Status {

    VERDADERO(0), FALSO(1), REINICIAR(2), ERROR(-1), STANDBY(3);

     Status(final int numero){
        this.numero = numero;
    }

    private int numero;

    public int getNumero(){
        return numero;
    }

}
