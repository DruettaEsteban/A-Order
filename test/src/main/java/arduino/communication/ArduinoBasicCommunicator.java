package arduino.communication;
import java.util.Scanner;

public class ArduinoBasicCommunicator extends Communicator {


    public ArduinoBasicCommunicator(String port, int baudRate) {
        super(port, baudRate);
    }

    public String receive() {
        Scanner scanner = new Scanner(super.sp.getInputStream());
        String number = null;
        while (scanner.hasNextLine()){
            try {
                number = scanner.nextLine();
            } catch (Exception ignored){}
        }
        return number;
    }

}