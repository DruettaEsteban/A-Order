package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JMultilineLabel extends JTextPane{
    private JFrame parentFrame;

    private static final long serialVersionUID = 1L;
    JMultilineLabel(){
        super();
        setEditable(false);
        setCursor(null);
        setOpaque(false);
        setFocusable(false);
        setFont(UIManager.getFont("Label.font"));
    }

    public void assureParent(){
        this.parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    public JFrame returnJFrameContainer(){
        return this.parentFrame;
    }


    private Container returnJPanelContainer(){
        return parentFrame.getContentPane();
    }

    private List<Component> returnRelatedComponents(final Container bigContainer){
        Component[] component = bigContainer.getComponents();
        List<Component> components = new ArrayList<>();

        for(Component c : component){
            if(c instanceof Container && !(c instanceof JTextPane)){
                components.addAll(returnRelatedComponents((Container) c));
            }else {
                components.add(c);
            }
        }
        return components;
    }

     private List<Component> returnNearComponents(){
        return returnRelatedComponents(returnJPanelContainer());
    }


    public List<Component> clearNearComponents(){
        List<Component> components = this.returnNearComponents();
        components.forEach(e -> this.parentFrame.remove(e));
        this.revalidate();
        this.repaint();
        return components;
    }

    public void restoreComponents(List<Component> c){
        c.forEach(p -> this.parentFrame.add(p));
        this.revalidate();
        this.repaint();

    }

    public void addComponent(Component component){
        this.parentFrame.add(component);
        this.revalidate();
    }

    public void removeComponent(Component component){
        this.parentFrame.remove(component);
        this.revalidate();
        this.repaint();
    }

}