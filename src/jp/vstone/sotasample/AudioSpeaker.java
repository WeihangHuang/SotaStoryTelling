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

    public AudioSpeaker(String story, int counter){
        this.story = story;
        setStory_directory(story);
        this.counter = counter;
    }

    public void welcomeMessage() {
        CPlayWave.PlayWave_wait(SYSTEM_MESSAGE_DIRECTORY_PATH + "welcome_message.wav");
        CRobotUtil.Log(TAG, "Play welcome message");
    }

    public void informTakingPhoto() {
        CPlayWave.PlayWave_wait(SYSTEM_MESSAGE_DIRECTORY_PATH + "take_a_photo.wav");
        CRobotUtil.Log(TAG, "Play taking photo message");
    }

    public void tellNextStoryPiece() {
        counter++;
        playStoryPiece();
    }

    public void tellFirstStoryPiece(String label) {
        this.story = label;
        setStory_directory(label);
        playStoryPiece();
    }

    public void noMoreStoryMessage() {
        CPlayWave.PlayWave_wait(SYSTEM_MESSAGE_DIRECTORY_PATH + "no_more_story.wav");
        CRobotUtil.Log(TAG, "Play no more story message");
    }

    public String getNextFilePath() {
        int next = counter + 1;
        String filePath = story_directory + next + ".wav";
        return filePath;
    }

    private void playStoryPiece(){
        String filePath = story_directory + counter + ".wav";
        CPlayWave.PlayWave_wait(filePath);
        CRobotUtil.Log(TAG, "Played story: " + story + ", piece: " + counter);
    }

    private void setStory_directory(String story){
        this.story_directory = "./audio_resources/" + story + "/";
    }

    public void goodByeMessage() {
        CPlayWave.PlayWave_wait(SYSTEM_MESSAGE_DIRECTORY_PATH + "goodbye_message.wav");
        CRobotUtil.Log(TAG, "Play goodbye message");
    }

    public void informPhotoIsTaken() {
        CPlayWave.PlayWave_wait(SYSTEM_MESSAGE_DIRECTORY_PATH + "photo_taken.wav");
        CRobotUtil.Log(TAG, "Play photo taken message");
    }
}
