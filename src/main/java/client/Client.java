package client;

import gamelogic.Game;
import gui.ClientGUI;
import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientID;
import server.Lobby;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * This class starts each client, establishes connection with the server and opens
 * up a thread which listens to ingoing and outgoing commands. It also opens up a
 * chat thread for communicating with other clients via command line.
 */
public class Client {
    private static final Logger clientLogger = LogManager.getLogger(Client.class);

    public static ClientThread clientThread;
    public static String username;

    public static List<Lobby> clientLobbyList = new ArrayList<>();
    public static List<Game> currentGames = new ArrayList<>();
    public static List<Game> pastGames = new ArrayList<>();
    public static List<Lobby> openLobbies = new ArrayList<>();
    public static List<ClientID> playerList = new ArrayList<>();
    public static List<String> messages = new ArrayList<>();

    /**
     * This is the constructor of the client class
     * @param serverIP The IP Address
     * @param port The port
     */
    public Client(String serverIP, int port) {
        try {
            Socket socket = new Socket(serverIP, port);
            clientThread = new ClientThread(socket);
            Thread thread = new Thread(clientThread);
            thread.start();

        } catch (Exception e) {
            clientLogger.error("Socket failed to connect. Can't seem to reach the server."); //LOGGER
//            e.printStackTrace();
        }

//        startChat();
//        MainChat.main(new String[0]);
        ClientGUI.main(new String[0]);
    }

    /**
     * This is also a constructor of the client class
     * @param serverIP The IP Address
     * @param port The port
     * @param name The name of the client
     */
    public Client(String serverIP, int port, String name) {
        username = name;

        try {
            Socket socket = new Socket(serverIP, port);
            clientThread = new ClientThread(socket);
            Thread thread = new Thread(clientThread);
            thread.start();
            clientThread.sendCmdToServer(Protocol.SETNAME, System.getProperty("user.name"));

        } catch (Exception e) {
            clientLogger.error("Socket failed to connect. Can't seem to reach the server.");
            e.printStackTrace();
        }

//        startChat();  // console chat, disabled
//        MainChat.main(new String[0]);
        ClientGUI.main(new String[0]);
    }


    /**
     * This method starts a chat thread which enables the client to communicate
     * via command line.
     */
    @Deprecated
    private void startChat() {
        Thread chatThread = new Thread(() -> {
            try {
                clientThread.sendCmdToServer(Protocol.LOGIN, username);
                while (/*chatThreadRunning*/true) {
                    BufferedReader brChat = new BufferedReader(new InputStreamReader(System.in));
                    String msg = brChat.readLine();
                    if (msg != null) {
                        if (isNotCommand(msg)) {
                            clientThread.sendCmdToServer(Protocol.GLOBALCHAT, msg);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                clientLogger.error("The chat couldn't be started!");
            }
        });
        chatThread.start();
    }


    /**
     * This method checks whether the command line input ist meant to execute
     * a certain task. In that case it lets the client thread send the according
     * network protocol command to the server.
     * @param msg message
     */
    public static boolean isNotCommand(String msg) {
        if (msg.startsWith("SETNAME") || msg.startsWith("setname")) {
            clientThread.sendCmdToServer(Protocol.SETNAME, msg.substring(7));
        } else if (msg.startsWith("LOGOUT") || msg.startsWith("logout")) {
            clientThread.sendCmdToServer(Protocol.LOGOUT, msg.substring(6));
        } else if (msg.startsWith("SHOWNAME") || msg.startsWith("showname")) {
            clientThread.sendCmdToServer(Protocol.SHOWNAME, msg.substring(8));
        } else if (msg.startsWith("SHOWPLAYERS") || msg.startsWith("showplayers")) {
            clientThread.sendCmdToServer(Protocol.SHOWPLAYERS, msg.substring(11));
        } else if (msg.startsWith("@LOBBY") || msg.startsWith("@lobby")) {
            clientThread.sendCmdToServer(Protocol.LOBBYCHAT, msg.substring(6));
        } else if (msg.toUpperCase().startsWith("WHISPER")) {
            clientThread.sendCmdToServer(Protocol.WHISPERCHAT, msg.substring(7));
        } else if (msg.startsWith("@")) {
            clientThread.sendCmdToServer(Protocol.WHISPERCHAT, msg.substring(1));
        } else if (msg.startsWith("COMMANDS") || msg.startsWith("commands")) {
            System.out.println("Commands for chat:");
            System.out.println("======================================================");
            System.out.println("SETNAME allows you to change your name");
            System.out.println("SHOWNAME displays your current name");
            System.out.println("WHISPER <playername> <message> sends a private message");
            System.out.println("@<playername> <message> works as well for whisper chat");
            System.out.println("@lobby <message> to write in lobby chat");
            System.out.println();
            System.out.println("SHOWNAME displays your current name");
            System.out.println("SHOWPLAYERS for all players online currently");
            System.out.println("SHOWLOBBY to display your current lobby");
            System.out.println("SHOWLOBBIES to display all lobbies and its players");
            System.out.println("SHOWPLAYERS displays all currently connected clients");
            System.out.println("RTT to check your round-trip time");
            System.out.println();
            System.out.println("SHOWGAMES to display currently running games");
            System.out.println("PASTGAMES to display finished games");
            System.out.println("OPENGAMES to display open lobbies, ready to join");
            System.out.println();
            System.out.println("MOVE <startSquare/endSquare> to make your move in game");
            System.out.println();
            System.out.println("NEWLOBBY to create a new lobby");
            System.out.println("JOINLOBBY <lobbyID> to join a specific lobby");
            System.out.println("QUITLOBBY to leave current lobby");
            System.out.println();
            System.out.println("LOGOUT or QUIT to end the chat");
            System.out.println("======================================================");
        } else if (msg.startsWith("RTT") || msg.startsWith("rtt")) {
            System.out.println("Your last round-trip time was " + clientThread.roundTripTime + "ms");
        } else if (msg.startsWith("NEWLOBBY") || msg.startsWith("newlobby")) {
            clientThread.sendCmdToServer(Protocol.CREATELOBBY, msg.substring(8));
        } else if (msg.startsWith("SHOWLOBBIES") || msg.startsWith("showlobbies")) {
            clientThread.sendCmdToServer(Protocol.SHOWLOBBIES, msg.substring(11));
        } else if (msg.startsWith("SHOWLOBBY") || msg.startsWith("showlobby")) {
            clientThread.sendCmdToServer(Protocol.SHOWLOBBY, msg.substring(9));
        } else if (msg.startsWith("JOINLOBBY") || msg.startsWith("joinlobby")) {
            clientThread.sendCmdToServer(Protocol.JOINLOBBY, msg.substring(9));
        } else if (msg.startsWith("QUITLOBBY") || msg.startsWith("quitlobby")) {
            clientThread.sendCmdToServer(Protocol.QUITLOBBY, msg.substring(9));
        } else if (msg.startsWith("LOBBY") || msg.startsWith("lobby")) {
            clientThread.sendCmdToServer(Protocol.LOBBYCHAT, msg.substring(5));
        } else if (msg.startsWith("NEWGAME") || msg.startsWith("newgame")) {
            clientThread.sendCmdToServer(Protocol.NEWGAME, msg.substring(7));
        } else if (msg.startsWith("QUIT") || msg.startsWith("quit")
                || msg.startsWith("EXIT") || msg.startsWith("exit")) {
            clientThread.sendCmdToServer(Protocol.LOGOUT, msg.substring(4));
        } else if (msg.startsWith("MOVE") || msg.startsWith("move")) {
            clientThread.sendCmdToServer(Protocol.MOVE, msg.substring(4));
        } else if (msg.startsWith("SHOWGAMES") || msg.startsWith("showgames")) {
            clientThread.sendCmdToServer(Protocol.SHOWGAMES, msg.substring(9));
        } else if (msg.startsWith("PASTGAMES") || msg.startsWith("pastgames")) {
            clientThread.sendCmdToServer(Protocol.PASTGAMES, msg.substring(9));
        } else if (msg.startsWith("OPENGAMES") || msg.startsWith("opengames")) {
            clientThread.sendCmdToServer(Protocol.OPENGAMES, msg.substring(9));
        } else {
            return true;
        }
        return false;
    }

    /**
     * This function is used to set the delay.
     * @param ms The time the delay should last.
     */
    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    /**
     * This is the main method of the client class.
     * @param args The string argument
     */
    public static void main(String[] args){

        //new Client(Constants.SERVERIP, Constants.PORT);

    }

    /**
     * Small method to get the index of a game stored in the client's gamesList.
     */
    public static int getIndex(Game game) {
        if (game == null) { return -1;}
        for (Game i : currentGames) {
            if (i.getGameID() == game.getGameID()) {
                return currentGames.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * Small method to get the index of a lobby stored in the client's lobbylist.
     */
    public static int getIndex(int lobbyID) {
        for (Lobby i : clientLobbyList) {
            if (i.getLobbyID() == lobbyID) {
                return clientLobbyList.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * This function sets the name of the client
     * @param name The name
     */
    public static void setUsername(String name) {
        username = name;
    }

    /**
     * This method is used to get the username
     * @return The username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * This method is used to get the RTT (Round Trip Time)
     * @return The RTT
     */
    public static long getRTT() {
        return clientThread.getRTT();
    }
}
