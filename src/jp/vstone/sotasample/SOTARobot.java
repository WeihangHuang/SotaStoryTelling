package jp.vstone.sotasample;

import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CRobotUtil;
import jp.vstone.RobotLib.CSotaMotion;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.CameraCapture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by apple on 2/19/18.
 */
public class SOTARobot {
    private CRoboCamera camera;
    private final CSotaMotion motion;
    private final CRobotMem mem;

    static final String TAG = "SOTARobot";

    public SOTARobot(){
        mem = new CRobotMem();
        motion = new CSotaMotion(mem);
        camera = new CRoboCamera("/dev/video0", motion);
        if (mem.Connect()) {
            motion.InitRobot_Sota();
            motion.ServoOn();
            CRobotUtil.Log(TAG, "EVA初号機、発進！");
        }
    }

    public byte[] takePhoto() {
        CRobotUtil.wait(1000);
        camera.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_HD_720, CameraCapture.CAP_FORMAT_MJPG));
        camera.StillPicture("./photo_taken/image");
        CRobotUtil.Log(TAG, "Photo is taken");

        byte[] imageByteArray = transformToByteArray("./photo_taken/image.jpg");

        return imageByteArray;
    }

    private byte[] transformToByteArray(String filepath) {
        byte[] data = null;

        try {
            Path path = Paths.get(filepath);
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            CRobotUtil.Log(TAG, e.getMessage());
        }

        CRobotUtil.Log(TAG, "Converted to byte array");
        return data;
    }

    public void down() {
        motion.ServoOff();
    }

    public boolean connect(){
        return mem.Connect();
    }
}
