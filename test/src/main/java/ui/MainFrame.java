package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;

public class MainFrame {
    private static MainFrame ourInstance = new MainFrame();

    public static MainFrame getInstance() {
        return ourInstance;
    }

    private JMultilineLabel mainLabel;

    private MainFrame() {
        JFrame jFrame = new JFrame("UFLI");


        //jFrame.setLocationRelativeTo(null);
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
        mainLabel.setText("Hello there!  How are you doing today?");





        jFrame.pack();





        java.awt.EventQueue.invokeLater(() -> jFrame.setVisible(true));
    }
}
class MyEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new StyledViewFactory();
    }

    static class StyledViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new LabelView(elem);
                    case AbstractDocument.ParagraphElementName:
                        return new ParagraphView(elem);
                    case AbstractDocument.SectionElementName:
                        return new CenteredBoxView(elem, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(elem);
                    case StyleConstants.IconElementName:

                        return new IconView(elem);
                }
            }

            return new LabelView(elem);
        }

    }
}

class CenteredBoxView extends BoxView {
    CenteredBoxView(Element elem, int axis) {

        super(elem, axis);
    }

    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {

        super.layoutMajorAxis(targetSpan, axis, offsets, spans);
        int textBlockHeight = 0;
        int offset = 0;

        for (int span : spans) {

            textBlockHeight = span;
        }
        offset = (targetSpan - textBlockHeight) / 2;
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] += offset;
        }

    }
}