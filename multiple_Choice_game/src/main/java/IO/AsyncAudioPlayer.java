package IO;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

public class AsyncAudioPlayer {
    private final File trueAudiosDir;
    private final File falseAudiosDir;

    public AsyncAudioPlayer(String trueLocation, String falseLocation){
        this.trueAudiosDir = new File(trueLocation);
        this.falseAudiosDir = new File(falseLocation);
        trueAudiosDir.mkdirs();
        falseAudiosDir.mkdirs();
    }

    public static void prebuildFileStructure(String trueLocation, String falseLocation){
        new File(trueLocation).mkdirs();
        new File(falseLocation).mkdirs();
    }

    public void playRandomAudio(boolean correct){

        Thread audioThread = new Thread(() -> {
            File track;
            File[] files;

            if (correct){
                files = trueAudiosDir.listFiles();
            }else {
                files = falseAudiosDir.listFiles();
            }

            if (files.length>0) {
                track = files[new Random().nextInt(files.length)];
                Media hit = new Media(track.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(hit);
                mediaPlayer.play();

            }else {
                System.err.println("No such file!");
            }
        });
        audioThread.start();
    }

}
