package client;

import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * This class listens to ingoing and outgoing messages from and to the server.
 * It is also able to open up a chat thread which lets the client use the command
 * window to chat with other clients.
 */
public class ClientThread implements Runnable {
    private final Socket socket;
    private BufferedReader br;
    private final ClientTasks clientTasks;
    private boolean clientThreadRunning;
    private boolean pingThreadRunning = true;
    private OutputStream out;
    private InputStream in;
    private BufferedWriter bw;
    private static final Logger clientThreadLogger = LogManager.getLogger(ClientThread.class);

    protected long pingStartTime;
    protected long roundTripTime;
    private int pingLostCounter = 0;

    /**
     * This is the constructor of the ClientThread class.
     * @param socket
     */
    ClientThread(Socket socket) {
        this.socket = socket;
        this.clientTasks = new ClientTasks();
    }

    /**
     * This starts the connection to the server, by starting the tread.
     */
    public void run() {
        // establishing communication channels with server
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // pinging server
            Runnable pingServer = () -> {
                try {
                    while(pingThreadRunning) {
                        pingStartTime = System.currentTimeMillis();

                        sendCmdToServer(Protocol.PING, "");
                        TimeUnit.MILLISECONDS.sleep(200);
                        roundTripTime = clientTasks.pingBackTime - pingStartTime;
                        if (clientTasks.pingBackTime - pingStartTime  < 0) {
                            pingLostCounter++;
                            if (pingLostCounter == 10) {
//                                System.out.println("Ping NOT received. Could not reach Server.");
//                                System.out.println("RTT: " + roundTripTime + "   Counter: " + pingLostCounter);
                                pingThreadRunning = false;
                                clientThreadRunning = false;
                            }

                        }
                        TimeUnit.SECONDS.sleep(5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    clientThreadLogger.error("Ping Thread could not be initiated!");
                }
            };
            new Thread(pingServer).start();



        } catch (IOException e) {
//          e.printStackTrace();
            clientThreadLogger.error("Connection to server lost");
        }
        try {
            clientThreadRunning = true;
            while (clientThreadRunning) {
                String input = br.readLine();

                // listening to ping from server
                if (input.equals("PONG")) {
                    sendCmdToServer(Protocol.PONG, "");
                } else {
                    Protocol cmd = Protocol.getCommand(input.substring(0, 4));
                    if (cmd != null) {
                        clientTasks.clientTasks(cmd, input.substring(4));
                    }
                }
            }
        } catch (Exception e) {
            clientThreadRunning = false;
            clientThreadLogger.error("Connection to server lost");
            try {
                br.close();
                System.out.println("Server shut down, terminating in 3 seconds.");
                e.printStackTrace();
                TimeUnit.SECONDS.sleep(3);
                System.exit(0);
            } catch (Exception e2) {
                e2.printStackTrace();
                clientThreadLogger.error("Error at shutting down the server!");
            }
        }
    }

    /**
     * This method sends a string message with a protocol command as argument
     * to the server to handle accordingly to the rules of the network protocol.
     * @param cmd The command from the Protocol class.
     * @param msg The string that needs to be sent to the server.
     */
    public synchronized void sendCmdToServer(Protocol cmd, String msg) {
        if(bw == null || msg == null){
            return;
        }
        try {
            bw.write(cmd.getString());
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            clientThreadLogger.error("Error, could not send "+ cmd.getString() + " " + msg);
            clientThreadRunning = false;
            pingThreadRunning = false;
        }
    }

    /**
     * This method return the RTT (Round Trip Time)
     * @return
     */
    public long getRTT() {
        return roundTripTime;
    }

    /**
     * This method closes the thread
     */
    public void closeThread() {
        pingThreadRunning = false;
        clientThreadRunning = false;
    }

}
