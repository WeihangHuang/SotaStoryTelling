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
        while (true)
        {


            System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            /*
             * This method will return when the end of speech
             * is reached. Note that the endpointer will determine
             * the end of speech.
             */
            Result result = recognizer.recognize();
            if (result != null)
            {

                System.out.println("Enter your choice"+ "\n");
                resultText = result.getBestFinalResultNoFiller();
                System.out.println("You said: " + resultText +  "\n");
                if (resultText.equalsIgnoreCase("yes")){
                    return true; //If the answer is yes, then return true
                }else if(resultText.equalsIgnoreCase("no")){
                    return false; //If the answer is no then return false
                }else {
                    //If the answer of the user is not yes or no then prompt for an answer again
                    System.out.println("Please respond in yes or no");
                }
            }
            else
            {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }

    public void voiceRecogEngineOn(String switchOffWord){
        /* the microphone will keep recording until the program exits */

        System.out.println("Say: (yes | no)");
        microphone.startRecording();

        while (true)
            {


                System.out.println("Start speaking. Press Ctrl-C to quit.\n");

                /*
                 * This method will return when the end of speech
                 * is reached. Note that the endpointer will determine
                 * the end of speech.
                 */


                Result result = recognizer.recognize();
                if (result != null)
                {

                    System.out.println("Enter your choice"+ "\n");
                    resultText = result.getBestFinalResultNoFiller();
                    System.out.println("You said: " + resultText + "We want " + switchOffWord + "\n");
                    if (switchOffWord.equalsIgnoreCase(resultText)){
                        System.out.println("Word matched");
                        break;
                    }
                }
                else
                {
                    System.out.println("I can't hear what you said.\n");
                }
            }
        }
}
