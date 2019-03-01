package communication;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class Sound {
    private AudioInputStream audio;
    private Clip clip;


    public Sound(Path audioF) {
        try {
            String fileName = "beep.wav";
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());



            audio= AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void play(){
        if(!clip.isRunning()){
            clip.setFramePosition(0);
            clip.start();
        }


    }

    public void stop(){
        clip.stop();
        clip.setFramePosition(0);
    }
}
