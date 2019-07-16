package ui;

import questions.Question;
import questions.QuestionsQueue;
import user.interaction.MyEditorKit;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Iterator;

public class MainFrame {
    private static MainFrame ourInstance = new MainFrame();

    public static MainFrame getInstance() {
        return ourInstance;
    }

    private JMultilineLabel mainLabel;

    private MainFrame() {

        PresentationFrame presentationFrame = new PresentationFrame(5000);
        presentationFrame.killFrame();


        JFrame jFrame = new JFrame("UFLI");
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //jFrame.setUndecorated(true); NEEDs EXIT BUTTON
        mainLabel = new JMultilineLabel();
        mainLabel.setEditorKit(new MyEditorKit());
        jFrame.getContentPane().add(mainLabel);

        StyledDocument document = mainLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        document.setParagraphAttributes(0, document.getLength(), center, false);

        mainLabel.setFont(new Font("", Font.PLAIN, 150));



        jFrame.pack();

        java.awt.EventQueue.invokeLater(() -> jFrame.setVisible(true));

    }

    private void setBlackColor(){
        mainLabel.setForeground(Color.BLACK);
    }


    public void showQuestions(QuestionsQueue queue){
        Question.setSpeaker(new Speaker());
        Iterator iterator = queue.iterator();
        while (iterator.hasNext()){
            setBlackColor();
            ((Question) iterator.next()).presentQuestion(mainLabel);

        }

        Question.disallocateSpeaker();
    }
}


