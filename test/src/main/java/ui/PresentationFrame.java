package ui;

import javax.swing.*;
import java.awt.*;

public class PresentationFrame {

    JFrame jFrame;

    PresentationFrame (int sleep){
        jFrame = new JFrame("Presentation");
        jFrame.setUndecorated(true);

        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("C:\\Users\\Usuario\\Desktop\\maxresdefault.jpg");
        JLabel imageLabel = new JLabel(image);
        jFrame.add(imageLabel);



        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        java.awt.EventQueue.invokeLater(() -> jFrame.setVisible(true));
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void killFrame(){
        jFrame.dispose();
    }


}
