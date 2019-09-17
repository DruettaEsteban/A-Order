package UI;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public abstract class AdaptableWindowApplication extends Application {
    private static double getWidth(){
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        return rectangle2D.getWidth();
    }

    private static double getHeight(){
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        return rectangle2D.getHeight();
    }

    protected static double getPercentageWidth(double percentage){
        return ((getWidth() / 100f) * percentage);
    }

    protected static double getPercentageHeight(double percentage){
        return ((getHeight() / 100f) * percentage);
    }

}
