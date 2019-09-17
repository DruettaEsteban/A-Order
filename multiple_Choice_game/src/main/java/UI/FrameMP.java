package UI;

import IO.QuestionsFactory;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import stadistics.StatisticQuestionFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FrameMP extends AdaptableWindowApplication {

    private final double ANSWER_HEIGHT = getPercentageHeight(4);
    private final double ANSWER_WIDTH = getPercentageWidth(45);
    private final double ANSWERS_SEPARATION = getPercentageHeight(5);
    private final double ANSWERS_BOTTOM_PADDING = getPercentageHeight(3);
    private final double RESPONSE_QUESTION_TOP_MARGIN = getPercentageHeight(5);
    private final double QUESTION_HEIGHT = getPercentageHeight(10);
    private final double QUESTION_WIDTH = getPercentageWidth(45);
    private final double QUESTION_CONTAINER_SPACING = getPercentageWidth(0.2);
    private final int FADE_TIME_MILLIS = 1000;
    private final int RESIZE_TIME_MILLIS = 1000;
    private final int FADE_COLOR_TIME_MILLIS = 1000;
    private final int COUNTDOWN_RIGHT_MARGIN = (int) getPercentageWidth(10);
    private final int COUNTDOWN_TOP_MARGIN = (int) getPercentageHeight(10);
    private final int COUNTDOWN_BAR_RIGHT_MARGIN = (int) getPercentageWidth(0);
    private final int COUNTDOWN_BAR_TOP_MARGIN = (int) getPercentageHeight(0);
    private final int COUNTDOWN_TIME_MILLIS = 21000;


    private Label optionA, optionB, optionC, optionD, question;
    private TimeBar countdownBar;
    private LinkedList<Label> options;
    private VBox answersContainer;
    private VBox questionContainer;

    public StatisticQuestionFile getCurrentQuestion() {
        return currentQuestion;
    }

    private StatisticQuestionFile currentQuestion;

    private boolean canEvaluate(){
        return (currentQuestion != null);
    }


    public void fillBar(){
        countdownBar.fillBar();
    }

    public void start(Stage primaryStage) {
        optionA = new Label();
        optionB = new Label();
        optionC = new Label();
        optionD = new Label();

        options = new LinkedList<>();
        Collections.addAll(options, optionA, optionB, optionC, optionD);

        options.forEach(e -> e.setWrapText(true));
        options.forEach(e -> e.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20)));

        answersContainer = new VBox();
        answersContainer.setSpacing(ANSWERS_SEPARATION);
        answersContainer.setAlignment(Pos.BOTTOM_CENTER);


        VBox.setMargin(options.getLast(), new Insets(0, 0, ANSWERS_BOTTOM_PADDING,0));

        options.forEach(option -> option.setStyle("-fx-background-color: white; -fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;"));
        options.forEach(option -> option.setMaxSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setMinSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setPrefSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setAlignment(Pos.CENTER));

        ObservableList<javafx.scene.Node> list = answersContainer.getChildren();
        list.addAll(optionA, optionB,optionC, optionD);

        //QUESTION SETTINGS
        questionContainer = new VBox();
        questionContainer.setSpacing(QUESTION_CONTAINER_SPACING);
        questionContainer.setAlignment(Pos.TOP_CENTER);
        question = new Label();
        VBox.setMargin(question, new Insets(RESPONSE_QUESTION_TOP_MARGIN, 0, 0,0));
        question.setStyle("-fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;");
        question.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,30));
        question.setMaxSize(QUESTION_WIDTH*2, QUESTION_HEIGHT*2);
        question.setMinSize(QUESTION_WIDTH*2, QUESTION_HEIGHT*2);
        question.setPrefSize(QUESTION_WIDTH*2, QUESTION_HEIGHT* 2);
        question.setWrapText(true);
        question.setAlignment(Pos.CENTER);

        questionContainer.getChildren().add(question);
        StackPane mainContainer = new StackPane();
        mainContainer.getChildren().addAll(questionContainer, answersContainer);

        //EXPERIMENTAL
        countdownBar = new TimeBar(COUNTDOWN_TIME_MILLIS);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        StackPane.setMargin(countdownBar, new Insets(COUNTDOWN_BAR_TOP_MARGIN, COUNTDOWN_BAR_RIGHT_MARGIN, 0 , 0));
        mainContainer.getChildren().add(countdownBar);
        countdownBar.setPrefWidth(getPercentageWidth(100));
        countdownBar.setMinWidth(getPercentageWidth(100));
        countdownBar.setMaxWidth(getPercentageWidth(100));
        countdownBar.setPrefHeight(getPercentageHeight(5));
        countdownBar.progressProperty().setValue(0);
        countdownBar.setStyle("-fx-bar-fill: green;");


        Scene scene = new Scene(mainContainer);

        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Testing game");
        primaryStage.setScene(scene);
        primaryStage.show();


        Controller controller = new Controller();
        controller.control(this);

        EventHandler<KeyEvent> eventHandler = event -> {
            if(event.getCode() == KeyCode.F1) {
                System.out.println((!isAddingQuestions) ? "Input mode enabled" : "\nInput mode disabled");
                isAddingQuestions = !isAddingQuestions;
                QuestionsFactory questionsFactory = QuestionsFactory.newInstance();
                Thread thread = new Thread(() -> {
                    while (isAddingQuestions) {
                        questionsFactory.addQuestion(QuestionsFactory.inputQuestion());
                    }
                });
                thread.start();
            }
        };

        scene.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
    }

    public void startCountdown(){
        countdownBar.startCountdown();
    }

    public boolean isTimeOver(){
        return this.countdownBar.remainingTime().equals(Duration.ZERO);
    }

    //public long getMillisRemainingTime(){ return (long) this.countdownLabel.remainingTime().toMillis(); }

    public void stopCounter(){ this.countdownBar.stopCountdown();}

    /*public int getCountdownMaxTime(){
        return this.COUNTDOWN_TIME;
    }*/

    private static boolean isAddingQuestions = false;

    public static void main(String[] args){
        launch();
    }


    protected void resizeTransitions(){
        resizeTransition(Answers.A);
        resizeTransition(Answers.B);
        resizeTransition(Answers.C);
        resizeTransition(Answers.D);
    }

    private void changeQuestion(Question newQuestion){
        fadeOutQuestion();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {
            Platform.runLater(()->{question.setText(newQuestion.getQuestion());});
            fadeInQuestion();

        },FADE_TIME_MILLIS, TimeUnit.MILLISECONDS);
    }

    private void changeOptions(Question newOptions){
        fadeOutOptions();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {
            turnOptionsWhite();
            for (int i = 0; i < newOptions.getOptions().size(); i++) {
                int finalI = i;
                Platform.runLater(()->{
                    options.get(finalI).setText(newOptions.getOptions().get(finalI));
                    System.out.println("option " + options.get(finalI));
                });

            }
            fadeInOptions();

        }, FADE_TIME_MILLIS, TimeUnit.MILLISECONDS);
    }

    public void changeQuestionAndOptions(StatisticQuestionFile newQuestion){
        if(newQuestion != null){
            changeQuestion(newQuestion);
            changeOptions(newQuestion);
            this.currentQuestion = newQuestion;
        }
    }


    private void resizeTransition(Answers a){
        Platform.runLater(()->{
            ScaleTransition transition = new ScaleTransition();
            transition.setDuration(Duration.millis(RESIZE_TIME_MILLIS));
            transition.setNode(options.get(a.getAnswer()));
            transition.setFromX(1);
            transition.setFromY(1);
            transition.setToX(0.1);
            transition.setToY(0.1);
            transition.setCycleCount(2);
            transition.setAutoReverse(true);
            transition.play();

        });

    }

    private void turnOptionsWhite(){
        Platform.runLater(()->{
            options.forEach(option -> option.setStyle("-fx-background-color: white; -fx-background-radius: 50 50 50 50; -fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;"));
        });

    }



    private void turnGreen(Answers answer){

        int duration = FADE_COLOR_TIME_MILLIS;
        Platform.runLater(()->{
            FadeTransition ft = new FadeTransition(Duration.millis(duration), options.get(answer.getAnswer()));
            ft.setFromValue(1);
            ft.setToValue(0.4);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();
        });

        Thread turnGreen = new Thread(() -> {
            try {
                Thread.sleep(duration);
                    Platform.runLater(()->options.get(answer.getAnswer()).setStyle("-fx-background-color: green; -fx-background-radius: 50 50 50 50;"+"-fx-padding: "+ ANSWER_HEIGHT + " " + ANSWER_WIDTH + " " + ANSWER_HEIGHT + " "+ ANSWER_WIDTH+"; -fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;"));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        turnGreen.start();

    }

    private void fadeOutOption(Answers answer){
        Platform.runLater(()->{
            FadeTransition ft = new FadeTransition(Duration.millis(FADE_TIME_MILLIS), options.get(answer.getAnswer()));
            ft.setFromValue(1);
            ft.setToValue(0.1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();
        });
    }

    private void fadeOutOptions(){
        fadeOutOption(Answers.A);
        fadeOutOption(Answers.B);
        fadeOutOption(Answers.C);
        fadeOutOption(Answers.D);
    }


    private void fadeInOption(Answers answer){
        Platform.runLater(()->{
            FadeTransition ft = new FadeTransition(Duration.millis(FADE_TIME_MILLIS), options.get(answer.getAnswer()));
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();
        });
    }

    private void fadeInOptions(){
        fadeInOption(Answers.A);
        fadeInOption(Answers.B);
        fadeInOption(Answers.C);
        fadeInOption(Answers.D);
    }

    private void turnRed(Answers answer){

        int duration = FADE_COLOR_TIME_MILLIS;

        Platform.runLater(()->{

            FadeTransition ft = new FadeTransition(Duration.millis(duration), options.get(answer.getAnswer()));
            ft.setFromValue(1);
            ft.setToValue(0.4);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();
        });

            Thread turnRed = new Thread(() -> {
                try {
                    Thread.sleep(duration);
                    Platform.runLater(()->options.get(answer.getAnswer()).setStyle("-fx-background-color: red; -fx-background-radius: 50 50 50 50;"+"; -fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            turnRed.start();

    }

    private void fadeOutQuestion(){

        Color fromColor = Color.BLACK;
        Color toColor = Color.TRANSPARENT;
        Platform.runLater(()->{
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(question.textFillProperty(), fromColor)),
                    new KeyFrame(Duration.millis(FADE_TIME_MILLIS), new KeyValue(question.textFillProperty(), toColor))
            );
            timeline.play();
        });

    }


    private void fadeInQuestion(){

        Color fromColor = Color.TRANSPARENT;
        Color toColor = Color.BLACK;
        Platform.runLater(()->{
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(question.textFillProperty(), fromColor)),
                    new KeyFrame(Duration.millis(FADE_TIME_MILLIS), new KeyValue(question.textFillProperty(), toColor))
            );
            timeline.play();
        });

    }

    public boolean isCorrect(Answers userResponse){
        return (userResponse == currentQuestion.getAnswer());
    }


    public void evaluateAndDisplay(Answers userResponse, Runnable resultAction){

        if(canEvaluate()){
            Answers correct = currentQuestion.getAnswer();
            String answerColor;
            switch (userResponse.getAnswer()){
                case 0:
                    answerColor = "-fx-border-color: blue;";
                    break;
                case 1:
                    answerColor = "-fx-border-color: black;";
                    break;
                case 2:
                    answerColor = "-fx-border-color: yellow;";
                    break;
                case 3:
                default:
                    answerColor = "-fx-border-color: red;";
                    break;
            }


            Platform.runLater(()->options.get(userResponse.getAnswer()).setStyle(answerColor+"-fx-border-width: 8; -fx-border-style: solid inside; -fx-border-radius: 50;-fx-background-color: white; -fx-background-radius: 50 50 50 50;"));

            Answers[] representationArray= new Answers[]{Answers.A,Answers.B, Answers.C, Answers.D};
            final LinkedList<Answers> completelyIncorrect = new LinkedList<>(Arrays.asList(representationArray));
            final LinkedList<Answers> lastRepresentation = new LinkedList<>(Arrays.asList(representationArray));

            completelyIncorrect.remove(correct);
            completelyIncorrect.remove(userResponse);
            completelyIncorrect.forEach(lastRepresentation::remove);


            final SimpleTreatment simpleTreatment = new SimpleTreatment();

            final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(3);

            Answers firstDeleted = completelyIncorrect.get(generateRandom(0, 1));
            completelyIncorrect.remove(firstDeleted);


            executorService.schedule(() -> Platform.runLater(()-> simpleTreatment.treatWrong(firstDeleted)), 2, TimeUnit.SECONDS);


            Answers secondDeleted = completelyIncorrect.pop();
            executorService.schedule(() -> Platform.runLater(()->simpleTreatment.treatWrong(secondDeleted)), 5, TimeUnit.SECONDS);


            Answers wrong;
            if(completelyIncorrect.size() > 0){
                wrong = completelyIncorrect.pop();
                lastRepresentation.add(wrong);
            }else {
                wrong = (lastRepresentation.get(0).equals(correct)) ? lastRepresentation.get(1) : lastRepresentation.get(0);
            }


            executorService.schedule(() -> {
                simpleTreatment.treatWrong(wrong);
                simpleTreatment.treatRight(correct);
                executorService.schedule(resultAction, (long) (this.RESIZE_TIME_MILLIS*1.2),TimeUnit.MILLISECONDS);
            }, 10, TimeUnit.SECONDS);


        }
    }

    class SimpleTreatment{
        void treatWrong(Answers answer){
            resizeTransition(answer);
            turnRed(answer);
        }

        void treatRight(Answers answer){
            resizeTransition(answer);
            turnGreen(answer);
        }
    }

    private int generateRandom(int a, int b){
        int result = -1;
        while (result != a && result !=b){
            result = new Random().nextInt(4);
        }
        return result;
    }

    //ITALIANISIMO
    //Sonidos italianos


}
