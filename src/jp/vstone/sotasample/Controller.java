package jp.vstone.sotasample;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 2/19/18.
 */
public class Controller {

    private ServerConnector connector;
    private AudioSpeaker speaker;
    private Camera camera;
    private SpeechRecognizer recognizer;

    public Controller(){
        this.connector = new ServerConnector();
        this.speaker = new AudioSpeaker();
        this.camera = new Camera();
        this.recognizer = new SpeechRecognizer();
    }

    public static void main(String[] args){

    }

    public void run(){
        speaker.welcomeMessage();
        speaker.informTakingPhoto();

        waitForSecond(1);

        byte[] image = camera.takePhoto();

        connector.sendImage(image);
        String story = connector.receivedLabel();

        connector.receivedServerSignal(); //indicate server is ready
        TellingStory(story);

        while(true){
            if (isTellingNext() && hasNextStoryPiece()) {
                connector.receivedServerSignal();
                speaker.tellNextStoryPiece();
            }
            else if (!hasNextStoryPiece()){
                speaker.noMoreStoryMessage();
                break;
            }
            else
                break;
        }

        connector.sendTerminationSignal();
        System.exit(1);
    }

    private boolean hasNextStoryPiece() {
        String filePath = speaker.getNextFilePath();
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    private boolean isTellingNext() {
        return recognizer.recognizeBoolean();
    }

    private void TellingStory(String story) {
        speaker.tellFirstStoryPiece(story);
        connector.sendAudioFinishedPlayingSignal();
    }

    private void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
