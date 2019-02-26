/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.greendetector;

import communication.Mail;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javafx.scene.paint.Color;


/**
 *
 * @author Usuario
 */

public class Main {
    
    public static int SENSIBILITY;
    public static int DELAY;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    
    public static void main(String[] args) throws InterruptedException {

        SENSIBILITY = Integer.parseInt(args[0]);
        DELAY = Integer.parseInt(args[1]);
        Grabber grabber = new Grabber(0);
        PaintedSquare square = new PaintedSquare();
        square.start();
        
        
        Mail mail = new Mail("COM3", 9600) {
            @Override
            public void sendMessage() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String readMessage() {
                return this.communication.serialRead();
            }

            @Override
            public void sendMessage(Boolean response) {
                try{
                    this.communication.serialWrite( (response) ? "true" : "false");
                }catch (Exception e){
                    System.out.println("Null pointer");
                }

            }
        };
        
        while(true){
            boolean isGreen = checkImage(grabber);
            System.out.printf("%s (%d) \n", isGreen,lastCounter);
            mail.sendMessage(isGreen);
        }
        
    }
    private static int lastCounter = 0;
    private static boolean checkImage(Grabber grabber) throws InterruptedException  {
        Thread.sleep(DELAY);
        

        BufferedImage toCheck = grabber.getImage();
        BufferedImage toShowFunctionality = new BufferedImage(toCheck.getColorModel(),toCheck.copyData(null), toCheck.isAlphaPremultiplied(), null);
        int coincidenceCounter = 0;

        for (int i = 0; i < toCheck.getHeight(); i++) {

            for (int j = 0; j < toCheck.getWidth(); j++) {
                PaintedSquare.updateImage(toShowFunctionality);
                if(Grabber.isGreen(toCheck.getRGB(j, i))){
                    toShowFunctionality.setRGB(j, i, 16711680);
                    coincidenceCounter++;

                }

            }
        }
        lastCounter = coincidenceCounter;
        return coincidenceCounter > SENSIBILITY;

    }
}
