package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class MainFrame {
    private static MainFrame ourInstance = new MainFrame();

    public static MainFrame getInstance() {
        return ourInstance;
    }

    private JMultilineLabel mainLabel;

    private MainFrame() {
        JFrame jFrame = new JFrame("UFLI");
        jFrame.getContentPane().add(Box.createVerticalGlue());


        //jFrame.setLocationRelativeTo(null);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //jFrame.setUndecorated(true); NEEDs EXIT BUTTON
        mainLabel = new JMultilineLabel();
        jFrame.getContentPane().add(mainLabel);

        StyledDocument document = mainLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        document.setParagraphAttributes(0, document.getLength(), center, false);



        mainLabel.setFont(new Font("", Font.PLAIN, 150));
        mainLabel.setText("Hello there!  How are you doing today?");
        jFrame.getContentPane().add(Box.createVerticalGlue());





        jFrame.pack();
        mainLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));





        java.awt.EventQueue.invokeLater(() -> jFrame.setVisible(true));
    }
}
