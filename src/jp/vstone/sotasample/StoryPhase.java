package jp.vstone.sotasample;

public class StoryPhase {
    private int phase;
    private int movement;
    private int speech;
    private String answer;

    public StoryPhase(int phase, int movement, int speech, String answer){
        this.phase = phase;
        this.movement = movement;
        this.speech = speech;
        this.answer = answer;
    }

    public int getMovementCmd() {
        return movement;
    }

    public int getSpeechCmd(){
        return speech;
    }

    public String getAnswer(){
        return answer;
    }
}
