package arduino.communication;


public final class ArduinoAdvancedCommunicator extends Communicator{


    public ArduinoAdvancedCommunicator(String port, int baudRate) {
        super(port, baudRate);
    }

    @Override
    public String receive() {
        throw new UnsupportedOperationException();
    }


}
