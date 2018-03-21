package jp.vstone.sotasample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SSSparser {
    private String filepath;

    public SSSparser(String givenfilepath){
        filepath = givenfilepath;
    }
    public ArrayList<StoryPhase> parseFile(){
        System.out.println("Parsing file");
        //instantiate the list of storyphase we will return
        ArrayList<StoryPhase> phases = new ArrayList<>();

        try{
            FileReader input = new FileReader(filepath);
            BufferedReader bufferRead = new BufferedReader(input);
            String myLine = null;
            while ( (myLine = bufferRead.readLine()) != null)
            {
                System.out.println(myLine);
                String[] brokenString = myLine.split(" ");

                //Taking the broken string inside Array1, we construct a storyPhase
                System.out.println("phaseNo: " + brokenString[0] + " mov: " + brokenString[2] + " spk: " + brokenString[4]);
                int phaseNo;
                int movementCmd;
                int speechCmd;

                if(!brokenString[0].equalsIgnoreCase("")){
                    phaseNo = Integer.parseInt(brokenString[0]);
                }else{
                    break;
                }
                if(!brokenString[2].equalsIgnoreCase("")){
                    movementCmd = Integer.parseInt(brokenString[2]);
                }else{
                    break;
                }
                if(!brokenString[4].equalsIgnoreCase("")){
                    speechCmd = Integer.parseInt(brokenString[4]);
                }else{
                    break;
                }
                String answer = brokenString[6];
                //Build the storyPhaseObject and store it into the list
                phases.add(new StoryPhase(phaseNo, movementCmd, speechCmd, answer));
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return phases;
    }
}
