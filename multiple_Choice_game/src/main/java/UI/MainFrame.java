package UI;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;




public class MainFrame extends Application {


    public void start(Stage primaryStage) {
        Group root = new Group(); //Group of nodes

        Scene scene = new Scene(root); //Appends group of nodes to itself
        scene.setFill(Color.WHITE); //Sets color

        Text question = new Text(); //Creates text
        question.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20)); //Set font type
        question.setX(250); //Set X position (relative to scene)
        question.setY(250); //Set Y position (relative to Scene)
        question.setText("This is a question"); //Set text
        question.setFill(Color.GREEN); //Text Color
        question.setStrokeWidth(2); //StrokeWidth
        question.setStroke(Color.BLACK); //Text Borders
        question.setStrikethrough(true); //Strikes through it
        question.setUnderline(true); //Underlines the text
        root.getChildren().add(question); //Appends node to Group

        //EFFECT
        Glow glow = new Glow(); //Glow effect
        glow.setLevel(0.9);
        question.setEffect(glow);

        //TRANSFORMATION
        Scale scale = new Scale();
        scale.setX(4);
        scale.setY(4);
        scale.setPivotX(250);
        scale.setPivotY(250);
        question.getTransforms().add(scale);

        //ANIMATION (RESCALING)
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(2000));
        scaleTransition.setNode(question);
        scaleTransition.setByX(1.5);
        scaleTransition.setByY(1.5);
        scaleTransition.setCycleCount(2); //Times it it executed
        scaleTransition.setAutoReverse(true); //Invert process?
        scaleTransition.play();

        //ANIMATION (TRANSLATION)
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setNode(question);
        translateTransition.setByX(1000);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        //HANDLE CLICK EVENT
        EventHandler<MouseEvent> eventHandler = event -> System.out.println("MOUSE CLICKED");
        //ADDING EVENT
        question.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        //REMOVE EVENT
        question.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);





        primaryStage.setTitle("Multiple Choice Game"); //Stage name
        primaryStage.setMaximized(true); //Maximizes stage
        primaryStage.setScene(scene); //Adds scene to stage





        primaryStage.show(); //Displays the content of the stage
    }

    public static void main(String[] args){
        launch();

    }


}
