package jp.vstone.sotasample;

import jp.vstone.RobotLib.CRobotUtil;

public class YesTest {

    public static void main(String[] args) {
        SpeechRecogEngine SpEngine = new SpeechRecogEngine();
        CRobotUtil.Log("System", "To start talking to SOTA, say Wake Up!");
        while(true){
            SpEngine.voiceRecogEngineYesNo();
        }
    }
}
