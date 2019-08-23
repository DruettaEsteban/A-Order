package IO;

import UI.Answers;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Scanner;

public final class ArduinoCommunication {

    SerialPort sp;

    public ArduinoCommunication(String port){
        sp = SerialPort.getCommPort(port);
        sp.setComPortParameters(9800, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        while (!sp.openPort()){
            System.out.println("Could not Open Port");
        }
        System.out.println("Port Opened");
    }

    public final void closePort(){
        this.sp.closePort();
    }

    public Answers readPort(){
        Scanner scanner = new Scanner(this.sp.getInputStream());
        int number = 4;
            //System.out.println("here");
        while (scanner.hasNextLine()) {  //COULD BE IMPROVED
            try {
                String result = scanner.nextLine();
                if(result.isEmpty()) return null;
                number = Integer.parseInt(result);
            } catch (Exception e) {
                System.out.println("Unknown error occurred");
                e.printStackTrace();
            }
        }
        switch (number){
            case 0:
                return Answers.A;
            case 1:
                return Answers.B;
            case 2:
                return Answers.C;
            case 3:
                return Answers.D;
            default:
                return null;
        }
    }

    public void clearPort(){
        Scanner scanner = new Scanner(this.sp.getInputStream());
        //System.out.println("here");
        while (scanner.hasNextLine()) {  //COULD BE IMPROVED
            scanner.nextLine();
        }
    }



}
