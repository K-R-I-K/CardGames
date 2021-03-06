import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * Server class for our card game
 */
public class Server implements Runnable{
    private final String ip;
    private final int port;
    private int errors;
    @Getter
    private DataOutputStream dos;
    @Getter
    private DataInputStream dis;
    private ServerSocket serverSocket;
    @Getter
    private boolean accepted;
    @Setter
    private boolean isHost;
    private boolean unableToCommunicateWithOpponent;
    @Setter
    private boolean isClientConnected = false;
    @Getter
    private PrintWriter printWriter=null;
    //private int userId=0;

    /**
     * Constructor for server
     */
    public Server(){
        ip = "26.255.53.80";
        //ip = "26.3.1.128";
        //ip = "26.248.220.2";
        try {
            FileWriter fileWriter = new FileWriter("log_"+User.getUserId()+".txt");
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        port = 22222;
        errors = 0;
        accepted = false;
        unableToCommunicateWithOpponent = false;
        if (!connect()) initializeServer();
        Thread thread = new Thread(this, "Durak");
        thread.start();
    }

    /**
     * Run method for Runnable
     */
    @Override
    public void run() {
        while (true) {
            if (isHost && !accepted) {
                listenForServerRequest();
                continue;
            }//else if(isClientConnected)
            //tick();
        }
    }

    private boolean connect() {
        try {
            Socket socket = new Socket(ip, port);
            dos = new MyObjectOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println("connect() dos and dis were created");
            accepted = true;
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isHost = true;
    }

    private void tick() {
        if (errors >= 10) unableToCommunicateWithOpponent = true;

        if (!unableToCommunicateWithOpponent) {
            try {
                if(dis.available()>0){
                    //int count = server.getDis().available();
                    int count = dis.readInt();
                    byte[] data = new byte[count];
                    dis.read(data, 0, count);
                    Durak buf = SerializationUtils.deserialize(data);
                    data = SerializationUtils.serialize(buf);
                    dos.writeInt(data.length);
                    dos.write(data, 0, data.length);
                    dos.flush();
                }

                //durak = SerializationUtils.deserialize(data);
            } catch (IOException e) {
                e.printStackTrace();
                errors++;
            }
        }
    }

    private void listenForServerRequest() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            dos = new MyObjectOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println("listenForServerRequest(): dos and dis were created");

            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
