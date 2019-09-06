package UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CountdownLabel extends Label {
    private int countdownCurrentTime;
    private final int countdownTime;
    private Timeline timeline;

    CountdownLabel(int countdownTime){
        super();
        this.countdownCurrentTime = countdownTime;
        this.countdownTime = countdownTime;
        Platform.runLater(()->setText(String.valueOf(countdownTime)));
    }

    void startCountdown(){
        Platform.runLater(()->{
            this.timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.seconds(1), event -> {
                        countdownCurrentTime--;
                        setText(String.valueOf(countdownCurrentTime));
                        if(countdownCurrentTime == 0) timeline.stop();
                    })
            );
            timeline.playFromStart();
        });
    }

    void stopTimer(){
        Platform.runLater(()-> this.timeline.stop());

    }

    void resetCounter(){
        countdownCurrentTime = countdownTime;
    }

    Duration remainingTime(){
        return Duration.seconds(countdownCurrentTime);
    }

}
