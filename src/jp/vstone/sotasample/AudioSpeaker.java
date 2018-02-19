package jp.vstone.sotasample;

import jp.vstone.RobotLib.CPlayWave;
import jp.vstone.RobotLib.CRobotUtil;

/**
 * Created by apple on 2/19/18.
 */
public class AudioSpeaker {

    private String story;
    private String story_directory;
    private int counter;

    static final String TAG = "AudioSpeaker";
    static final String SYSTEM_MESSAGE_DIRECTORY_PATH = "./audio_resources/system_message/";


    public AudioSpeaker(){
        this.story = null;
        this.counter = 0;
    }

    public void welcomeMessage() {
        CPlayWave.PlayWave(SYSTEM_MESSAGE_DIRECTORY_PATH + "welcome_message.wav");
        CRobotUtil.Log(TAG, "Play welcome message");
    }

    public void informTakingPhoto() {
        CPlayWave.PlayWave(SYSTEM_MESSAGE_DIRECTORY_PATH + "take_a_photo.wav");
        CRobotUtil.Log(TAG, "Play taking photo message");
    }

    public void tellNextStoryPiece() {
        counter++;
        playStoryPiece();
    }

    public void tellFirstStoryPiece(String label) {
        this.story = label;
        story_directory = "./audio_resources/" + story + "/";
        playStoryPiece();
    }

    public void noMoreStoryMessage() {
        CPlayWave.PlayWave(SYSTEM_MESSAGE_DIRECTORY_PATH + "no_more_story.wav");
        CRobotUtil.Log(TAG, "Play no more story message");
    }

    public String getNextFilePath() {
        int next = counter + 1;
        String filePath = story_directory + next + ".wav";
        return filePath;
    }

    private void playStoryPiece(){
        String filePath = story_directory + counter + ".wav";
        CPlayWave.PlayWave(filePath);
        CRobotUtil.Log(TAG, "Played story: " + story + ", piece: " + counter);
    }
}