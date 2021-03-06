package jp.vstone.sotasample;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import java.io.IOException;
import java.net.URL;

public class SpeechRecogEngine {
    private String resultText;
    private Recognizer recognizer;
    private Microphone microphone;

    public SpeechRecogEngine() {
        try {

            URL url = TalkingToSota.class.getResource("helloworld.config.xml");

            System.out.println("Loading...");

            ConfigurationManager cm = new ConfigurationManager(url);

            this.recognizer = (Recognizer) cm.lookup("recognizer");
            this.microphone = (Microphone) cm.lookup("microphone");


            /* allocate the resource necessary for the recognizer */
            recognizer.allocate();

        } catch (IOException e) {
            System.err.println("Problem when loading Speech Recognition " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring HelloWorld: " + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Problem creating HelloWorld: " + e);
            e.printStackTrace();
        }
    }

    public boolean voiceRecogEngineYesNo(){

        microphone.startRecording();
        while (microphone.isRecording())
        {
            Result result = recognizer.recognize();
            if (result != null)
            {
                resultText = result.getBestFinalResultNoFiller();
                System.out.println("You said: " + resultText +  "\n");
                if (resultText.equalsIgnoreCase("yes")){
                    microphone.clear();
                    return true; //If the answer is yes, then return true
                }else if(resultText.equalsIgnoreCase("no")){
                    microphone.clear();
                    return false; //If the answer is no then return false
                }else {
                    //If the answer of the user is not yes or no then prompt for an answer again
                    System.out.println("Im listening for yes or no!");
                }
            }
            else
            {
                System.out.println("I can't hear what you said.\n");
                System.out.println("Yes / no");
            }
        }
        return false;
    }

    public boolean voiceRecogEngineOnWord(String switchOffWord){
        microphone.startRecording();

        while (microphone.isRecording())
            {
                Result result = recognizer.recognize();
                if (result != null){
                    resultText = result.getBestFinalResultNoFiller();
                    System.out.println("You said: " + resultText + "We're looking for: " + switchOffWord);
                    if (switchOffWord.equalsIgnoreCase(resultText)){
                        //System.out.println("Word matched");
                        microphone.clear();
                        return true;
                    }else{
                        //Even if the answer is wrong we want it to continue
                        System.out.println("Try again!");
                        microphone.clear();
                        //return false;
                    }
                }
                else
                {
                    System.out.println("I can't hear what you said.\n");
                    System.out.println("SwitchoffWord");
                }
            }
            return false;
        }
}
