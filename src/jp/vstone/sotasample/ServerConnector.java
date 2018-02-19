package jp.vstone.sotasample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by apple on 2/19/18.
 */
public class ServerConnector {

    private int portNumber;
    private String hostName;

    private Socket clientSocket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;


    public ServerConnector(){
        try {
            clientSocket = new Socket(hostName, portNumber);
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendImage(byte[] image)  {
        try {
            out.writeInt(1);
            out.writeInt(image.length);
            out.write(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAudioFinishedPlayingSignal() {
        try {
            out.writeInt(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTerminationSignal(){
        try {
            out.writeInt(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receivedLabel(){
        String label = null;

        try {
            label = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return label;
    }

    public boolean receivedServerSignal() {
        boolean signal = false;

        try {
            signal = in.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return signal;
    }
}
