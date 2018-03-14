package jp.vstone.sotasample;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.*;
import jp.vstone.RobotLib.*;
import java.io.IOException;
import java.net.URL;

public class TalkingToSota{

    static String resultText;


    static final String TAG = "SpeechRecSample";
    public static void main(String[] args) throws Exception {
        //VSMDと通信ソケット・メモリアクセス用クラス
        CRobotMem mem = new CRobotMem();
        //Sota用モーション制御クラス
        CSotaMotion motion = new CSotaMotion(mem);

       SpeechRecogEngine SpEngine = new SpeechRecogEngine();

       System.out.println("Hello!");

       SpEngine.voiceRecogEngineOn("Apple");

       System.out.println("Do you want to continue");

       if(SpEngine.voiceRecogEngineYesNo()){
           System.out.println("Lets continue with the story then!");
       }else{
           System.out.println("Lets stop the story then!");
       }

       System.out.println("Good bye!");

    }
}
