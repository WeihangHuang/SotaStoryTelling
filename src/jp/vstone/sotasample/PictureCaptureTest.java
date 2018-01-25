package jp.vstone.sotasample;

import jp.vstone.RobotLib.*;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.CameraCapture;

import java.awt.*;

/**
 * Created by apple on 1/24/18.
 */
public class PictureCaptureTest  {

    static final String TAG = "PictureCaptureTest";

    public static void main(String args[]){
        CRobotUtil.Log(TAG, "Start" + TAG);

        CRobotPose pose;
        CRobotMem mem = new CRobotMem();

        CSotaMotion motion = new CSotaMotion(mem);

        CRoboCamera camera = new CRoboCamera("/dev/video0", motion);

        if(mem.Connect()){
            motion.InitRobot_Sota();
            CRobotUtil.Log(TAG, "Rev. " + mem.FirmwareRev.get());
            motion.ServoOn();
            CRobotUtil.Log(TAG, "Servo On");

            pose = new CRobotPose();
            pose.SetPose(new Byte[] {1   ,2   ,3   ,4   ,5   ,6   ,7   ,8}	//id
                    ,  new Short[]{0   ,-900,0   ,900 ,0   ,0   ,0   ,0}				//target pos
            );

            pose.setLED_Sota(Color.BLUE, Color.BLUE, 255, Color.GREEN);

            motion.play(pose, 500);
            CRobotUtil.wait(500);

            CPlayWave.PlayWave("./sound/take_a_photo.wav");
            camera.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));

            CRobotUtil.Log(TAG, "Take picture after count down.");

            CRobotUtil.Log(TAG, "5");
            pose.setLED_Sota(Color.GREEN, Color.GREEN, 255, Color.YELLOW);
            motion.play(pose, 1000);
            CRobotUtil.wait(1000);

            CRobotUtil.Log(TAG, "4");
            pose.setLED_Sota(Color.ORANGE, Color.ORANGE, 255, Color.YELLOW);
            motion.play(pose, 1000);
            CRobotUtil.wait(1000);

            CRobotUtil.Log(TAG, "3");
            pose.setLED_Sota(Color.YELLOW, Color.YELLOW, 255, Color.YELLOW);
            motion.play(pose, 1000);
            CRobotUtil.wait(1000);

            CRobotUtil.Log(TAG, "2");
            pose.setLED_Sota(Color.BLUE, Color.GREEN, 255, Color.YELLOW);
            motion.play(pose, 1000);
            CRobotUtil.wait(1000);

            CRobotUtil.Log(TAG, "1");
            pose.setLED_Sota(Color.RED, Color.RED, 255, Color.YELLOW);
            motion.play(pose, 1000);
            CRobotUtil.wait(1000);

            CRobotUtil.Log(TAG, "0");

            camera.StillPicture("./TestPhoto");
            CPlayWave.PlayWave("./sound/nice_photo.wav");

            pose.setLED_Sota(Color.BLUE, Color.BLUE, 255, Color.GREEN);
            motion.play(pose, 1500);
        }
        motion.ServoOff();
    }
}

