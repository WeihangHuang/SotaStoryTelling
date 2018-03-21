package jp.vstone.sotasample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by apple on 2/19/18.
 */
public class Controller {

    private ServerConnector connector;
    private AudioSpeaker speaker;
    private SOTARobot SOTARobot;
    private SpeechRecogEngine speechRecognizer;
    private MotionEngine motion;

    public Controller(String hostName){
        this.connector = new ServerConnector(hostName);
        this.speaker = new AudioSpeaker();
        this.SOTARobot = new SOTARobot();
        this.speechRecognizer = new SpeechRecogEngine();
        this.motion = new MotionEngine();
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

            //connector.receivedServerSignal(); //indicate server is ready

            SSSparser sssParser = new SSSparser("./Sota_Story_Scripts/" + story + ".txt");

            ArrayList<StoryPhase> phases = sssParser.parseFile();

            for (StoryPhase phase : phases){
                //Run each of the commands with movement first, speech, then answer
                //Run the motion first
                System.out.println("MOV: " + phase.getMovementCmd());
                System.out.println("MOV: " + phase.getSpeechCmd());
                System.out.println("MOV: " + phase.getAnswer());
                if(phase.getMovementCmd() != -1 ) {
                    System.out.println("playing motion");
                    motion.play(phase.getMovementCmd());

                }
                connector.receivedServerSignal();
                connector.startPlayingAudioSignal();
                if(phase.getSpeechCmd() != -1 ) {
                    System.out.println("Playing audio");
                    speaker.tellStoryPhase(story, phase.getSpeechCmd());
                }
                if(!phase.getAnswer().equalsIgnoreCase("-1")){
                    System.out.println("Looking for answer");
                    speechRecognizer.voiceRecogEngineOnWord(phase.getAnswer());
                }
                connector.sendAudioFinishedPlayingSignal();
            }

            //tellingFirstStoryPiece(story);

//            int storyPhase = 0;

//            while (hasNextStoryPiece()) {
//                connector.receivedServerSignal();
//                if(storyPhase != 4){
//                    connector.startPlayingAudioSignal();
//                    speaker.tellNextStoryPiece();
//                    connector.sendAudioFinishedPlayingSignal();
//                    storyPhase++;
//                }else {
//                    if (isTellingNext()) {
//                        connector.startPlayingAudioSignal();
//                        speaker.tellNextStoryPiece();
//                        connector.sendAudioFinishedPlayingSignal();
//                        storyPhase = 0;
//                    } else {
//                        break;
//                    }
//                }
//            }

            speaker.noMoreStoryMessage();

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

//    private void runStoryPhases(ArrayList<StoryPhase> phases){
//        for (StoryPhase phase : phases){
//            //Check whether server side is ready
//            connector.receivedServerSignal();
//
//            //Run each of the commands with movement first, speech, then answer
//            //Run the motion first
//            if(phase.getMovementCmd() != -1 ) {
//                motion.play(phase.getMovementCmd());
//            }
//            connector.sendAudioFinishedPlayingSignal();
//            if(phase.getSpeechCmd() != -1 ) {
//                speaker.tellStoryPhase(phase.getSpeechCmd());
//            }
//            if(phase.getAnswer() != "-1"){
//                speechRecognizer.voiceRecogEngineOnWord(phase.getAnswer());
//            }
//            connector.startPlayingAudioSignal();
//        }
//    }

}
