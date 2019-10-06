package IO;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class AsyncAudioPlayer {
    private final LinkedList<MediaPlayer> trueAudiosList;
    private final LinkedList<MediaPlayer> falseAudiosList;

    public AsyncAudioPlayer(String trueLocation, String falseLocation){
        File trueAudiosDir = new File(trueLocation);
        File falseAudiosDir = new File(falseLocation);
        trueAudiosDir.mkdirs();
        falseAudiosDir.mkdirs();
        File[] trueAudiosArray = trueAudiosDir.listFiles();
        File[] falseAudiosArrray = falseAudiosDir.listFiles();
        this.trueAudiosList = new LinkedList<>();
        this.falseAudiosList = new LinkedList<>();

        Arrays.stream(trueAudiosArray).forEach(correct ->{
            trueAudiosList.add(new MediaPlayer(new Media(correct.toURI().toString())));
        });
        Arrays.stream(falseAudiosArrray).forEach(wrong ->{
            falseAudiosList.add(new MediaPlayer(new Media(wrong.toURI().toString())));
        });

    }


    public void playRandomAudio(boolean correct){

        Thread audioThread = new Thread(() -> {
            MediaPlayer track;
            LinkedList<MediaPlayer> files;

            if (correct){
                files =  trueAudiosList;
            }else {
                files = falseAudiosList;
            }

            if (files.size()>0) {
                track = files.get(new Random().nextInt(files.size()));
                MediaPlayer mediaPlayer = track;
                mediaPlayer.seek(Duration.millis(0));
                mediaPlayer.play();

            }else {
                System.err.println("No such file!");
            }
        });
        audioThread.start();
    }

}
