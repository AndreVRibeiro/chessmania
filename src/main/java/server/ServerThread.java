package server;

import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * This class opens up a thread to listen to ingoing and outgoing messages from and to the client.
 * Each client gets his own thread assigned.
 */
public class ServerThread  implements Runnable {
    private static int connectionCounter = 0;

    private final Socket socket;
    private BufferedReader br;
    protected BufferedWriter bw;
    private ServerTasks serverTasks;
    private ClientID clientID;
    private boolean serverThreadRunning;
    private boolean pingThreadRunning;
    private InputStream in;
    private OutputStream out;
    protected long pongStartTime;
    private long roundTripTime;

    private int pongCounter = 0;
    private static final Logger serverThreadLogger = LogManager.getLogger(ServerThread.class);

    /**
     * This is the constructor of the ServerThread class.
     * @param socket THe socket.
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
        ++connectionCounter;
    }

    /**
     * This method starts the ServerThread.
     */
    public void run() {
        // establishing communication channels with client
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // pinging client
            Runnable pingClient = () -> {
                try {
                    pingThreadRunning = true;
                    while(pingThreadRunning) {
                        pongStartTime = System.currentTimeMillis();
                        sendCmdToClient(Protocol.PONG, "");
                        TimeUnit.MILLISECONDS.sleep(200);
                        roundTripTime = serverTasks.pongBackTime - pongStartTime;
                        if (roundTripTime < 0) {
                            pongCounter++;
//                            System.out.println("Pong NOT received. Could not reach client " + clientID.getUsername());
//                            System.out.println("RTT: " + roundTripTime + "   Counter: " + pongCounter);
                            if (pongCounter == 10) {
                                pingThreadRunning = false;
                                serverThreadRunning = false;
                            }
                        }
                        TimeUnit.SECONDS.sleep(5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    serverThreadLogger.error("Pong Thread could not be initiated!");
                }
            };
            new Thread(pingClient).start();

        } catch (IOException e) {
            e.printStackTrace();
            serverThreadLogger.error("Connection to client lost");
        }
        serverThreadRunning = true;
        while (serverThreadRunning) try {
            String input = br.readLine();

            // listening to ping from client
            if (input.equals("PING")) {
                sendCmdToClient(Protocol.PING, "");
            } else {
                Protocol cmd = Protocol.getCommand(input.substring(0, 4));
                if (cmd != null) {
                    serverTasks.serverTasks(cmd, input.substring(4));
                }
            }
        } catch (IOException e) {
            serverThreadRunning = false;
            serverThreadLogger.error("Player disconnect: " + clientID.getUsername() + ", " +
                    "Connection: " + connectionCounter--);
            serverTasks.logout();
        }
    }

    /**
     * This method closes the Server Thread.
     */
    public void closeThread() {
        pingThreadRunning = false;
        serverThreadRunning = false;
    }

    /**
     * This function is used to create a delay in the Thread.
     * @param ms Milliseconds.
     */
    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    /**
     * returns the amount of current connections as integer
     */
    public int getConnectionCounter() {
        return connectionCounter;
    }

    /**
     * This is used to set the ServerTasks.
     * @param serverTasks serverTasks
     */
    public void setServerTasks(ServerTasks serverTasks) {
        this.serverTasks = serverTasks;
    }

    /**
     * This sets the clientID.
     * @param clientID clientID.
     */
    public void setClientID(ClientID clientID) {
        this.clientID = clientID;
    }

    /**
     * this method sends a string message with a protocol command as argument to the
     * the server to handle accordingly to the rules of the network protocol.
     * @param cmd The command from the Protocol class.
     * @param msg The string that needs to be sent to the server.
     */
    public synchronized void sendCmdToClient(Protocol cmd, String msg) {
        if(msg == null || bw == null || !serverThreadRunning){
            return;
        }
        try {
            bw.write(cmd.getString());
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            serverThreadLogger.error("Error at sending command: "+e.toString());
        }
    }

}
