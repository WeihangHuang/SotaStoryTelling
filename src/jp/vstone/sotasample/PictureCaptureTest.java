package jp.vstone.sotasample;

import jp.vstone.RobotLib.*;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.CameraCapture;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            camera.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_HD_720, CameraCapture.CAP_FORMAT_MJPG));

            CRobotUtil.Log(TAG, "Take picture after count down.");

//            CRobotUtil.Log(TAG, "5");
//            pose.setLED_Sota(Color.GREEN, Color.GREEN, 255, Color.YELLOW);
//            motion.play(pose, 1000);
//            CRobotUtil.wait(1000);
//
//            CRobotUtil.Log(TAG, "4");
//            pose.setLED_Sota(Color.ORANGE, Color.ORANGE, 255, Color.YELLOW);
//            motion.play(pose, 1000);
//            CRobotUtil.wait(1000);
//
//            CRobotUtil.Log(TAG, "3");
//            pose.setLED_Sota(Color.YELLOW, Color.YELLOW, 255, Color.YELLOW);
//            motion.play(pose, 1000);
//            CRobotUtil.wait(1000);

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

            PictureCaptureTest.send("./TestPhoto.jpg" );

            System.out.println("Reach here. Tag 0");

            pose.setLED_Sota(Color.BLUE, Color.BLUE, 255, Color.GREEN);
            motion.play(pose, 1500);
        }
        motion.ServoOff();
    }

    public static void send(String filePath) {
        int portNumber = 8765;
        String hostName = "192.168.1.86";


        System.out.println("Reach here. TAG 1");

        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            System.out.println("Reach here. TAG 2");
            Path path = Paths.get(filePath);
            System.out.format("toString: %s%n", path.toString());
            System.out.println("Reach here. TAG 3");
            byte[] data = Files.readAllBytes(path);
            System.out.println("Reach here. TAG 4");

            out.writeInt(data.length);
            out.write(data);




        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.out.println(e.toString());
            System.exit(1);
        }
    }

}

