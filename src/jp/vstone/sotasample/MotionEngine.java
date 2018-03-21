package jp.vstone.sotasample;

import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CRobotPose;
import jp.vstone.RobotLib.CRobotUtil;
import jp.vstone.RobotLib.CSotaMotion;

import java.awt.*;

public class MotionEngine {
    static final String TAG = "MotionEngine";
    private CRobotPose pose;
    private CRobotMem mem;
    private CSotaMotion motion;
    private Color colourArray[];
    private Short poseArray[];
    private int mouthIntensity;

    public MotionEngine(){
        this.colourArray = new Color[3];
        this. poseArray = new Short[8];
        mem = new CRobotMem();
        motion = new CSotaMotion(mem);
        if(mem.Connect()) {
            //Sota仕様にVSMDを初期化
            motion.InitRobot_Sota();

            CRobotUtil.Log(TAG, "Rev. " + mem.FirmwareRev.get());

            //サーボモータを現在位置でトルクOnにする
            CRobotUtil.Log(TAG, "Servo On");
            motion.ServoOn();
        }
    }
    //(min, max) (-900, 900);
    //1 = base (+left rotation | -right rotation)
    //2 = left shoulder (+up | -down)
    //3 = left elbow (+down | -up)
    //4 = right shoulder (+down | -up)
    //5 = right elbow (+up | -down)
    //6 = neck (+left rotation | -right rotation)
    //7 = neck (+look down | -look up)
    //8 = neck (tilt left | tilt right)
    public void posePlay(){
        pose = new CRobotPose();
        pose.SetPose(new Byte[] {1   ,2   ,3   ,4   ,5   ,6   ,7   ,8}	//id
                ,  this.poseArray);
        pose.setLED_Sota(this.colourArray[0], this.colourArray[1], mouthIntensity, this.colourArray[2]);
        motion.play(pose,1000);
        motion.waitEndinterpAll();
    }

    public void setColours(Color  eye_Left, Color eye_Right, int mouth, Color btn){
        this.colourArray[0] = eye_Left;
        this.colourArray[1] = eye_Right;
        this.colourArray[2] = btn;
        this.mouthIntensity = mouth;
    }

    public void setPose(int base, int leftShoulder, int leftElbow, int rightShoulder, int rightElbow, int neckRotate, int neckUpDown, int neckTilt){
        this.poseArray[0] = (short) base;
        this.poseArray[1] = (short) leftShoulder;
        this.poseArray[2] = (short) leftElbow;
        this.poseArray[3] = (short) rightShoulder;
        this.poseArray[4] = (short) rightElbow;
        this.poseArray[5] = (short) neckRotate;
        this.poseArray[6] = (short) neckUpDown;
        this.poseArray[7] = (short) neckTilt;
    }


    public void MotionEngineOff(){
        CRobotUtil.Log(TAG, "Servo Off");
        motion.ServoOff();
    }

    public void headShake(){
        this.setColours(Color.RED, Color.RED, 255, Color.ORANGE);
        this.setPose(0,-450,-200,450,200,900, 500,0);
        this.posePlay();
        this.setColours(Color.DARK_GRAY, Color.DARK_GRAY, 255, Color.ORANGE);
        this.setPose(0,-450,-200,450,200,-900, 500,0);
        this.posePlay();
        this.setColours(Color.RED, Color.RED, 255, Color.ORANGE);
        this.setPose(0,-450,-200,450,200,0, 500,0);
        this.posePlay();
    }

    public void bingo(){
        this.setColours(Color.GREEN, Color.GREEN, 255, Color.GREEN);
        this.setPose(0,900,-200,-900,200,0, 900,0);
        this.posePlay();
        this.setColours(Color.DARK_GRAY, Color.DARK_GRAY, 255, Color.DARK_GRAY);
        this.setPose(0,900,-200,-900,200,0, -900,0);
        this.posePlay();
        this.setColours(Color.GREEN, Color.GREEN, 255, Color.GREEN);
        this.setPose(0,-450,-200,450,200,0, 0,0);
        this.posePlay();

    }

    public void play(int movementSelect){
        switch(movementSelect){
            case 0:
                bingo();
                break;
            case 1:
                headShake();
                break;
            default:
                break;
        }
    }
}
