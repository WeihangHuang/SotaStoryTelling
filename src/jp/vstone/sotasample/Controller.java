package jp.vstone.sotasample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by apple on 2/19/18.
 */
public class Controller {

    private ServerConnector connector;
    private AudioSpeaker speaker;
    private SOTARobot SOTARobot;
    private SpeechRecogEngine speechRecognizer;

    public Controller(){
        this.connector = new ServerConnector();
        this.speaker = new AudioSpeaker();
        this.SOTARobot = new SOTARobot();
        this.speechRecognizer = new SpeechRecogEngine();
    }

    public static void main(String[] args){
        Controller controller = new Controller();
        controller.run();
    }

    public void run(){
        if (SOTARobot.connect()) {
            speaker.welcomeMessage();

            byte[] image = takePhoto();

            connector.sendImage(image);
            String story = connector.receivedLabel();

            connector.receivedServerSignal(); //indicate server is ready
            tellingFirstStoryPiece(story);

            while (true) {
                if (isTellingNext() && hasNextStoryPiece()) {
                    connector.receivedServerSignal();
                    speaker.tellNextStoryPiece();
                } else if (!hasNextStoryPiece()) {
                    speaker.noMoreStoryMessage();
                    break;
                } else
                    break;
            }

            speaker.goodByeMessage();
            connector.sendTerminationSignal();
            SOTARobot.down();
            System.exit(1);
        }
    }

    private boolean hasNextStoryPiece() {
        String filePath = speaker.getNextFilePath();
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    private boolean isTellingNext() {
        return speechRecognizer.voiceRecogEngineYesNo();
    }

    private void tellingFirstStoryPiece(String story) {
        speaker.tellFirstStoryPiece(story);
        connector.sendAudioFinishedPlayingSignal();
    }

    private byte[] takePhoto(){
        speaker.informTakingPhoto();
        byte[] data =  SOTARobot.takePhoto();
        speaker.informPhotoIsTaken();
        return data;
    }

}
