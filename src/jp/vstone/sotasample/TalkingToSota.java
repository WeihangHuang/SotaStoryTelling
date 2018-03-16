package jp.vstone.sotasample;

import jp.vstone.RobotLib.CRobotUtil;

import java.awt.*;


public class TalkingToSota{

    static final String TAG = "System";

    public static void main(String[] args){
        MotionEngine MoEngine = new MotionEngine();
       SpeechRecogEngine SpEngine = new SpeechRecogEngine();

       CRobotUtil.Log(TAG, "To start talking to SOTA, say Wake Up!");

       if(SpEngine.voiceRecogEngineOnWord("wake up")){
            System.out.println("SOTA: Hello there! Do you have something to show me?");
       }

       System.out.println("SOTA: Oh thats an apple! Do you want to hear more about apples?");

       if(!SpEngine.voiceRecogEngineYesNo()){
           System.out.println("SOTA: Oh, okay then, maybe next time!");
           return;
       }

       //If pass the if statement then the story continues
        System.out.println("Does learning about where apples come from interest you?");
       if(SpEngine.voiceRecogEngineYesNo()){
           System.out.println("Apples come from apple trees!");
       }

       System.out.println("How about a story that talks about a very hungry caterpillar?");

       if(SpEngine.voiceRecogEngineYesNo()){
           System.out.println("SOTA: Great! Sit tight and listen to the story of the very hungry caterpillar!");
       }

       //Starting the different words Recognition
        System.out.println("SOTA: What is this fruit called?");
       while(!SpEngine.voiceRecogEngineOnWord("kiwi")){
           System.out.println("SOTA: Try again!");

       }
        System.out.println("SOTA: Thats right!");
       System.out.println("SOTA: What about this one?");

       while(!SpEngine.voiceRecogEngineOnWord("orange")){
           System.out.println("Try again!");
       }
       System.out.println("SOTA: Correct!");
       System.out.println("SOTA: Last one!");
       while(!SpEngine.voiceRecogEngineOnWord("lemon")){
           System.out.println("SOTA: Ohh! Try again!");
        }
        System.out.println("Hurray! You got the last one right! Well done");
       ///////////////////////////////////////////////////////////////////
       ///////////////////////////////////////////////////////////////////
        //
        //Starting the different words Recognition and Motion Weaving
        //
        //
        System.out.println("SOTA: What is this fruit called?");
        while(!SpEngine.voiceRecogEngineOnWord("kiwi")){
            MoEngine.headShake();
            System.out.println("SOTA: Try again!");
            //Once we exit the loop we can
        }
        System.out.println("SOTA: Thats right!");
        MoEngine.bingo();
        System.out.println("SOTA: What about this one?");

        while(!SpEngine.voiceRecogEngineOnWord("orange")){
            MoEngine.headShake();
            System.out.println("Try again!");

        }
        System.out.println("SOTA: Correct!");
        MoEngine.bingo();
        System.out.println("SOTA: Last one!");
        while(!SpEngine.voiceRecogEngineOnWord("lemon")){
            MoEngine.headShake();
            System.out.println("SOTA: Ohh! Try again!");

        }
        MoEngine.bingo();
        System.out.println("Hurray! You got the last one right! Well done");



//       System.out.println("Now testing Movement engine class");
//       MoEngine.setPose(200, 200, 200 ,200, 200, 200, 200, 200);
//       MoEngine.setColours(Color.ORANGE, Color.YELLOW, 255, Color.GREEN);
//       MoEngine.posePlay();
//
//       MoEngine.setPose(-200, -200, -200, -200, -200, -200, -200, -200);
//       MoEngine.setColours(Color.BLUE, Color.GREEN, 100, Color.ORANGE);
//       MoEngine.posePlay();
//       MoEngine.MotionEngineOff();
    }
}
