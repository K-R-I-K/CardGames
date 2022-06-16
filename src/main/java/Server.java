import lombok.Getter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private final String ip;
    private int port;
    private int errors;
    private Thread thread;
    private Socket socket;
    @Getter
    private DataOutputStream dos;
    @Getter
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private boolean accepted;
    private boolean isHost;
    private boolean isClient;
    private boolean unableToCommunicateWithOpponent;
    @Getter
    private boolean areBothPlayersConnected;
    //private int userId=0;
    private Durak durak;
    public Server(Durak durak){
        areBothPlayersConnected = false;
        this.durak = durak;
        ip = "26.255.53.80";
        port = 22222;
        errors = 0;
        accepted = false;
        unableToCommunicateWithOpponent = false;
        if (!connect()) initializeServer();
        thread = new Thread(this, "Durak");
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            tick();
            if (isHost && !accepted) {
                listenForServerRequest();
            }

        }
    }

    private boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            areBothPlayersConnected = true;
            System.out.println("dos and dis were created");
            //dos.writeInt(++userId);
            //dos.flush();
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
        isClient = false;
    }

    private void tick() {
        if (errors >= 10) unableToCommunicateWithOpponent = true;

        if (areBothPlayersConnected && !unableToCommunicateWithOpponent) {
            try {
                byte[] data = new byte[0];
                if(dis.read(data)>0){
                    dos.write(data);
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
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println("dos and dis were created");

            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
