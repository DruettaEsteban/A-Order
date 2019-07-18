package questions;

import arduino.communication.ArduinoBasicCommunicator;
import com.sun.istack.internal.NotNull;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import ui.JMultilineLabel;
import user.interaction.Status;
import javafx.scene.media.Media;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;

public final class BasicVideoQuestion extends BasicQuestion{
    protected TrueFalseVideoLocation trueFalseVideoLocation;

    public BasicVideoQuestion(ArduinoBasicCommunicator bc, String question, Status anwser,TrueFalseVideoLocation trueFalseVideoLocation) {
        super(bc, question, anwser);
        this.trueFalseVideoLocation = trueFalseVideoLocation;

    }



    @Override
    public void presentQuestion(JMultilineLabel mainLabel) {

        mainLabel.setText(this.getQuestion());
        this.readQuestion();

        boolean evaluation = this.evaluate();
        speaker.say("You say it is " + ((answerStatus.getNumero() == 0) ? "true" : "false")+". And I reply... ");
        File videoLocation = (evaluation) ? trueFalseVideoLocation.getSomeTrueFile() : trueFalseVideoLocation.getSomeFalseFile();
        mainLabel.assureParent();
        JFrame jFrame = mainLabel.returnJFrameContainer();

        final JFXPanel FXpanel = new JFXPanel();

        Media media = new Media(videoLocation.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        StackPane root = new StackPane();
        Scene scene = new Scene(root);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        viewer.setX(((screen.getWidth() - jFrame.getWidth())) / 2);
        viewer.setY((screen.getHeight() - jFrame.getWidth()) / 2);



        root.getChildren().add(viewer);
        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true);

        FXpanel.setScene(scene);

        List<Component> components = mainLabel.clearNearComponents();

        mainLabel.returnJFrameContainer().add(FXpanel);
        player.play();


        try {
            Thread.sleep((long) (player.getTotalDuration().toMillis())); //Wait for the end of the video
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainLabel.removeComponent(FXpanel);
        mainLabel.restoreComponents(components);


    }

    public static class TrueFalseVideoLocation{
        LinkedHashSet<File> trueSet;
        LinkedHashSet<File> falseSet;
        public TrueFalseVideoLocation(LinkedHashSet<File> trueSet, LinkedHashSet<File>falseSet){
            this.trueSet = trueSet;
            this.falseSet = falseSet;
        }

        File getSomeTrueFile(){
            return randomize(trueSet);
        }

        File getSomeFalseFile(){
            return  randomize(falseSet);
        }


        private File randomize(LinkedHashSet<File> set){
            File file = set.stream().skip((int) (set.size() * Math.random())).findFirst().orElseGet( null);
            return file;
        }
    }

}
