package arduino.communication;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public abstract class Communicator {

    protected SerialPort sp;
    Communicator(String port){
        sp = SerialPort.getCommPort(port);
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);


        if(sp.openPort()){
            System.out.println("Port openned");
        }else{
            System.err.println("Could not open port");
        }
    }

    Communicator(SerialPort sp){
        this.sp = sp;
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);


        if(sp.openPort()){
            System.out.println("Port openned");
        }else{
            System.err.println("Could not open port");
        }
    }

    public final SerialPort getSerialPort(){
        return this.sp;
    }

    public final void closeCommunicator(){
        this.sp.closePort();
    }



    public abstract String receive();

    public void send(String message) throws IOException {
        sp.getOutputStream().write(message.getBytes());
        sp.getOutputStream().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (sp.closePort()) {
            System.err.println("Post is closed");
        } else {
            System.err.println("Port not closed");
        }
    }
}
