package arduino.communication;


public final class ArduinoAdvancedCommunicator extends ArduinoBasicCommunicator{


    public ArduinoAdvancedCommunicator(String port) {
        super(port);
    }

    @Override
    public String receive() {
        throw new UnsupportedOperationException();
    }


}
