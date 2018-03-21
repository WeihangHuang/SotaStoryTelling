package jp.vstone.sotasample;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

/**
 * Created by apple on 2/19/18.
 */
public class AudioSpeakerTest {
    @Test
    public void canSetCorrectFilePath(){
        AudioSpeaker speaker = new AudioSpeaker();
        speaker.tellFirstStoryPiece("story_one");
        assertEquals(speaker.getNextFilePath(),"./audio_resources/story_one/1.wav");
    }

    @Test
    public void counterCanIncrement(){
        AudioSpeaker speaker = new AudioSpeaker("story_one", 2);
        assertEquals(speaker.getNextFilePath(), "./audio_resources/story_one/3.wav");
        speaker.tellNextStoryPiece();
        speaker.tellNextStoryPiece();
        assertEquals(speaker.getNextFilePath(), "./audio_resources/story_one/5.wav");
    }
}