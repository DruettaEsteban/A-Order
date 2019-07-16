package ui;

import javax.swing.*;


public class JMultilineLabel extends JTextPane{
    private static final long serialVersionUID = 1L;
    public JMultilineLabel(){
        super();
        setEditable(false);
        setCursor(null);
        setOpaque(false);
        setFocusable(false);
        setFont(UIManager.getFont("Label.font"));



    }
}