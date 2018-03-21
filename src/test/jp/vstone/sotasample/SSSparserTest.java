package jp.vstone.sotasample;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SSSparserTest {
    @Test
    public void parseCorrectly(){
        SSSparser ssSparser = new SSSparser("./Sota_Story_Scripts/tone-s-big-drop.txt");
        assertThat(ssSparser.parseFile().size(), is(2));
    }
}
