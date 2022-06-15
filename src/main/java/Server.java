import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private String ip;
    private int port;
    private int errors;
    private Thread thread;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private boolean accepted;
    private boolean isHost;
    private boolean isClient;
    private boolean unableToCommunicateWithOpponent;

    private boolean yourTurn = false;
    private boolean won = false;
    private boolean enemyWon = false;

    public Server(){
        ip = "";
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
        yourTurn = true;
        isHost = true;
        isClient = false;
    }

    private void tick() {
        if (errors >= 10) unableToCommunicateWithOpponent = true;

        if (!yourTurn && !unableToCommunicateWithOpponent) {
            try {
                byte[] data = new byte[0];
                dis.read(data);
                Durak game = SerializationUtils.deserialize(data);
                if (isClient) spaces[space] = "X";
                else spaces[space] = "O";
                checkForEnemyWin();
                yourTurn = true;
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
            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ttt(){
        if (accepted) {
            if (yourTurn && !unableToCommunicateWithOpponent && !won && !enemyWon) {
                int x = e.getX() / lengthOfSpace;
                int y = e.getY() / lengthOfSpace;
                y *= 3;
                int position = x + y;

                if (spaces[position] == null) {
                    if (!circle) spaces[position] = "X";
                    else spaces[position] = "O";
                    yourTurn = false;
                    repaint();
                    Toolkit.getDefaultToolkit().sync();

                    try {
                        dos.writeInt(position);
                        dos.flush();
                    } catch (IOException e1) {
                        errors++;
                        e1.printStackTrace();
                    }

                    System.out.println("DATA WAS SENT");
                    checkForWin();
                    checkForTie();

                }
    }
}
