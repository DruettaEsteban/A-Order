package ui;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speaker {
    private VoiceManager voiceManager;
    private Voice voice;

    public Speaker(){


        System.setProperty("mbrola.base", "C:\\mbrola");

        voiceManager = VoiceManager.getInstance();


        voice = voiceManager.getVoice("mbrola_us1");

        voice.allocate();
        voice.setRate(120);
    }

    public void say (String phrase){
        voice.speak(phrase);

    }

    public void disallocate(){
        voice.deallocate();
    }
}
