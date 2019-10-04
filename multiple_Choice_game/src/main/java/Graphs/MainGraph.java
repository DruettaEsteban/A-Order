package Graphs;

import UI.AdaptableWindow;
import UI.Answers;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import stadistics.StatisticQuestion;

import java.util.Arrays;
import java.util.LinkedList;

public class MainGraph  extends AdaptableWindow {

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private Series<String, Integer> questionData;
    private XYChart.Data<String, Integer> A;
    private XYChart.Data<String, Integer> B;
    private XYChart.Data<String, Integer> C;
    private XYChart.Data<String, Integer> D;
    private BarChart barChart;
    private boolean ready = false;
    private final int FADE_OUT_MILLIS = 2000;
    private final int FADE_IN_MILLIS = 2000;
    private boolean TWO_SCREENS = false;
    private final int LETTERS_PER_LINE = 25;



    public MainGraph(){
        Platform.runLater(() -> {

            Stage graphStage = new Stage(StageStyle.UNDECORATED);
            TWO_SCREENS = this.moveToSecondScreen(graphStage);


            if(TWO_SCREENS) {


                StackPane mainContainer = new StackPane();
                mainContainer.setAlignment(Pos.CENTER);

                xAxis = new CategoryAxis();
                xAxis.setMaxSize(getPercentageWidth(90), getPercentageHeight(10));
                xAxis.setMinSize(getPercentageWidth(90), getPercentageHeight(10));
                xAxis.setPrefSize(getPercentageWidth(90), getPercentageHeight(10));

                yAxis = new NumberAxis();
                yAxis.setLabel("%");
                yAxis.setMaxSize(getPercentageWidth(6.5), getPercentageHeight(90));
                yAxis.setMinSize(getPercentageWidth(6.5), getPercentageHeight(90));
                yAxis.setPrefSize(getPercentageWidth(6.5), getPercentageHeight(90));
                yAxis.setTickLabelFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

                barChart = new BarChart(xAxis, yAxis);

                StackPane.setMargin(barChart, new Insets(getPercentageHeight(4), getPercentageWidth(2), 0, getPercentageWidth(1)));
                barChart.setStyle("-fx-font-size: 34;");
                barChart.setLegendVisible(false);


                questionData = new Series<>();
                xAxis.tickLabelFontProperty().setValue(Font.font(30));

                barChart.getData().add(questionData);
                barChart.setCategoryGap(300);

                mainContainer.getChildren().add(barChart);


                graphStage.setTitle("Game statistics");
                graphStage.setMaximized(true);
                graphStage.setScene(new Scene(mainContainer));

                graphStage.show();
                ready = true;
            }
        });

    }


    private String spaceShink(String toBeShinked){
        LinkedList<String> words = new LinkedList<>(Arrays.asList(toBeShinked.split("\\s+")));
        StringBuilder result = new StringBuilder();

        StringBuilder buffer = new StringBuilder();
        for (String word:words) {
            buffer.append(word + " ");
            if (buffer.length() > this.LETTERS_PER_LINE){
                result.append(buffer).append("\n");
                buffer = new StringBuilder();
            }
        }
        if (buffer.length() > 0) result.append(buffer);

        return result.toString();
    }


    public void updateGraph(StatisticQuestion statisticQuestion){
        if(TWO_SCREENS) {

            String AText = spaceShink(statisticQuestion.getOptions().get(Answers.A.getAnswer()));
            String BText = spaceShink(statisticQuestion.getOptions().get(Answers.B.getAnswer()));
            String CText = spaceShink(statisticQuestion.getOptions().get(Answers.C.getAnswer()));
            String DText = spaceShink(statisticQuestion.getOptions().get(Answers.D.getAnswer()));


            Platform.runLater(() -> {

                if (ready) {
                    fadeInOutDelayed(1000).setOnFinished(event -> {
                        questionData.getData().clear();

                        PercentualValues percentualValues = new PercentualValues(statisticQuestion.amountA, statisticQuestion.amountB,statisticQuestion.amountC,statisticQuestion.amountD);

                        A = new XYChart.Data<>(AText, percentualValues.getAPercentual());
                        B = new XYChart.Data<>(BText, percentualValues.getBPercentual());
                        C = new XYChart.Data<>(CText, percentualValues.getCPercentual());
                        D = new XYChart.Data<>(DText, percentualValues.getDPercentual());

                        System.out.println(percentualValues.getAPercentual());
                        System.out.println(percentualValues.getBPercentual());
                        System.out.println(percentualValues.getCPercentual());
                        System.out.println(percentualValues.getDPercentual());

                        Platform.runLater(() -> {

                            A.getNode().setStyle("-fx-bar-fill: blue");
                            B.getNode().setStyle("-fx-bar-fill: black");
                            C.getNode().setStyle("-fx-bar-fill: yellow");
                            D.getNode().setStyle("-fx-bar-fill: red");
                        });

                        questionData.getData().addAll(A, B, C, D);
                        barChart.setTitle(statisticQuestion.getQuestion());

                    });
                }
            });
        }
        
    }

    private FadeTransition generateFadeOut(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_OUT_MILLIS), barChart);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        return fadeTransition;
    }

    private FadeTransition generateFadeIn(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_IN_MILLIS), barChart);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        return fadeTransition;
    }

    private FadeTransition fadeInOutDelayed(int delayMillis){
        FadeTransition out = generateFadeOut();
        FadeTransition in = generateFadeIn();
        PauseTransition pause = new PauseTransition(Duration.millis(delayMillis));

        SequentialTransition sequentialTransition = new SequentialTransition(out, pause, in);

        Platform.runLater(sequentialTransition::play);

        return out;
    }

    private boolean moveToSecondScreen(Stage stage){
        ObservableList<Screen> screens = Screen.getScreens();
        if (screens.size() == 1) return false;



        //Rectangle2D mainScreenDimentions  =mainScreen.getVisualBounds();
        Rectangle2D secondScreenDimentions = screens.get(1).getVisualBounds();

        stage.setX(secondScreenDimentions.getMinX());
        stage.setY(secondScreenDimentions.getMinY());
        return true;
    }

}
