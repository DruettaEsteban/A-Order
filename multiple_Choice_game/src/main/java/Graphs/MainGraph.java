package Graphs;

import UI.AdaptableWindow;
import UI.Answers;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import stadistics.StatisticQuestion;

public class MainGraph  extends AdaptableWindow {

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private Series<String, Integer> questionData;
    private XYChart.Data<String, Integer> A;
    private XYChart.Data<String, Integer> B;
    private XYChart.Data<String, Integer> C;
    private XYChart.Data<String, Integer> D;
    private BarChart barChart;

    public MainGraph(){
        Stage graphStage = new Stage();

        StackPane mainContainer = new StackPane();
        mainContainer.setAlignment(Pos.CENTER);

        xAxis = new CategoryAxis();
        xAxis.setMaxSize(getPercentageWidth(90), getPercentageHeight(10));
        xAxis.setMinSize(getPercentageWidth(90), getPercentageHeight(10));
        xAxis.setPrefSize(getPercentageWidth(90), getPercentageHeight(10));

        yAxis = new NumberAxis();
        yAxis.setMaxSize(getPercentageWidth(10), getPercentageHeight(90));
        yAxis.setMinSize(getPercentageWidth(10), getPercentageHeight(90));
        yAxis.setPrefSize(getPercentageWidth(10), getPercentageHeight(90));
        yAxis.setTickLabelFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20));



        barChart = new BarChart(xAxis, yAxis);
        barChart.setTitle("HOla como estas?");
        barChart.setStyle("-fx-font-size: 30;");
        barChart.setLegendVisible(false);


        questionData = new Series<>();
        A = new XYChart.Data<>("Soy un ejemplo muy largo", 100);
        B = new XYChart.Data<>("Ejemplo B", 20);
        C = new XYChart.Data<>("Ejemplo C", 30);
        D = new XYChart.Data<>("Ejemplo D", 40);

        Platform.runLater(() -> {
            xAxis.tickLabelFontProperty().setValue(Font.font(30));
            A.getNode().setStyle("-fx-bar-fill: blue");
            B.getNode().setStyle("-fx-bar-fill: black");
            C.getNode().setStyle("-fx-bar-fill: yellow");
            D.getNode().setStyle("-fx-bar-fill: red");
        });

        questionData.getData().addAll(A, B, C, D);
        barChart.getData().add(questionData);
        barChart.setCategoryGap(300);

        mainContainer.getChildren().add(barChart);


        graphStage.setTitle("Game statistics");
        graphStage.setFullScreen(true);
        graphStage.setScene(new Scene(mainContainer));
        graphStage.show();
    }


    public void updateGraph(StatisticQuestion statisticQuestion){
        Platform.runLater(() -> {
            A.setXValue(statisticQuestion.getOptions().get(Answers.A.getAnswer()));
            B.setXValue(statisticQuestion.getOptions().get(Answers.B.getAnswer()));
            C.setXValue(statisticQuestion.getOptions().get(Answers.C.getAnswer()));
            D.setXValue(statisticQuestion.getOptions().get(Answers.D.getAnswer()));
            A.setYValue(statisticQuestion.amountA);
            B.setYValue(statisticQuestion.amountB);
            C.setYValue(statisticQuestion.amountC);
            D.setYValue(statisticQuestion.amountD);
            barChart.setTitle(statisticQuestion.getQuestion());
        });
        
    }

}
