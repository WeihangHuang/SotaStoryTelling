package jp.vstone.sotasample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by apple on 2/19/18.
 */
public class Controller {

    private ServerConnector connector;
    private AudioSpeaker speaker;
    private SOTARobot SOTARobot;
    private SpeechRecogEngine speechRecognizer;

    public Controller(String hostName){
        this.connector = new ServerConnector(hostName);
        this.speaker = new AudioSpeaker();
        this.SOTARobot = new SOTARobot();
        this.speechRecognizer = new SpeechRecogEngine();
    }

    public static void main(String[] args){
        String hostName;

        Scanner reader = new Scanner(System.in);
        System.out.print("Input IP Address: ");
        hostName = reader.nextLine();
        reader.close();

        Controller controller = new Controller(hostName);
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
                    connector.sendAudioFinishedPlayingSignal();
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
